package halios.model.move;

import static halios.model.move.MoveType.DOCK;

import halios.model.Planet;
import halios.model.Ship;

import javax.annotation.Nonnull;

public class DockMove extends Move {
    @Nonnull
    private final Planet planet;

    public DockMove(@Nonnull final Ship ship, @Nonnull final Planet planet) {
        super(DOCK, ship);
        this.planet = planet;
    }

    @Nonnull
    public Planet getPlanet() {
        return planet;
    }

    @Override
    @Nonnull
    public String serialize() {
        return String.format("d %d %d ", getShip().getId(), getPlanet().getId());
    }
}
