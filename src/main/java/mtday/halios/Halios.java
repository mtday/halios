package mtday.halios;

import mtday.halios.config.Config;
import mtday.halios.io.Comms;
import mtday.halios.model.DockingStatus;
import mtday.halios.model.Planet;
import mtday.halios.model.Ship;
import mtday.halios.model.move.DockMove;
import mtday.halios.model.move.Move;
import mtday.halios.model.move.ThrustMove;
import mtday.halios.starterkit.GameMap;
import mtday.halios.starterkit.Navigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import javax.annotation.Nonnull;

public class Halios {
    private final Comms comms;
    private final GameMap gameMap;
    private final Logger log;

    public Halios() {
        comms = new Comms();
        final int playerId = comms.readPlayerId();

        System.setProperty("log.file", String.format("%d_%s.log", playerId, Halios.class.getName()));

        log = LoggerFactory.getLogger(Halios.class);

        log.info("Reading game for player: {}", playerId);
        gameMap = comms.readGame(playerId);
    }

    public void run() {
        log.info("Bot name is: {}", Halios.class.getSimpleName());
        comms.sendBotName(Halios.class.getSimpleName());

        final ArrayList<Move> moveList = new ArrayList<>();
        while (true) {
            moveList.clear();
            log.info("Updating game");
            comms.updateGame(gameMap);

            for (final Ship ship : gameMap.getPlayer().getShips().values()) {
                log.info("  Processing ship: {}", ship.getId());
                if (ship.getDockingStatus() != DockingStatus.UNDOCKED) {
                    continue;
                }

                for (final Planet planet : gameMap.getPlanets().values()) {
                    if (planet.isOwned()) {
                        continue;
                    }

                    if (ship.canDock(planet)) {
                        moveList.add(new DockMove(ship, planet));
                        break;
                    }

                    final ThrustMove newThrustMove =
                            Navigation.navigateShipToDock(gameMap, ship, planet, Config.SPEED_MAX.getInt() / 2);
                    if (newThrustMove != null) {
                        moveList.add(newThrustMove);
                    }

                    break;
                }
            }
            comms.send(moveList);
        }
    }

    public static void main(@Nonnull final String[] args) {
        new Halios().run();
    }
}
