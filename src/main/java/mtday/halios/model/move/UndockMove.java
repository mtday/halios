package mtday.halios.model.move;

import mtday.halios.model.Ship;

import javax.annotation.Nonnull;

public class UndockMove extends Move {
    public UndockMove(@Nonnull final Ship ship) {
        super(MoveType.UNDOCK, ship);
    }

    @Override
    @Nonnull
    public String serialize() {
        return String.format("u %d", getShip().getId());
    }
}
