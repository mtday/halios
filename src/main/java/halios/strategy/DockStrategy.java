package halios.strategy;

import halios.config.Config;
import halios.model.DockingStatus;
import halios.model.Game;
import halios.model.Planet;
import halios.model.Ship;
import halios.model.move.DockMove;
import halios.model.move.Move;
import halios.model.move.NoopMove;
import halios.starterkit.Navigation;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DockStrategy implements Strategy {
    @Nonnull
    private final Ship ship;

    @Nullable
    private Planet target;

    public DockStrategy(@Nonnull final Ship ship) {
        this.ship = ship;
    }

    @Nonnull
    @Override
    public Move run(@Nonnull final Game game) {
        if (ship.getDockingStatus() == DockingStatus.DOCKED) {
            return new NoopMove(ship);
        }

        if (target == null) {
            target = game.getPlanets().values().stream()
                    .filter(planet -> !(planet.isOwned() && planet.getOwner() == game.getPlayerId() && planet.isFull()))
                    .sorted(Comparator.comparingDouble(planet -> planet.distance(ship)))
                    .findFirst()
                    .orElse(null);

            if (target == null) {
                // TODO: There are no available planets, switch strategies.
                return new NoopMove(ship);
            }
        }

        if (ship.canDock(target)) {
            return new DockMove(ship, target);
        }

        return Navigation.navigateShipToDock(game, ship, target, Config.SPEED_MAX.getInt());
    }
}
