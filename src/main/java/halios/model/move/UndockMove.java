package halios.model.move;

import static halios.model.move.MoveType.UNDOCK;

import halios.model.Ship;

import javax.annotation.Nonnull;

public class UndockMove extends Move {
    public UndockMove(@Nonnull final Ship ship) {
        super(UNDOCK, ship);
    }

    @Override
    @Nonnull
    public String serialize() {
        return String.format("u %d ", getShip().getId());
    }
}
