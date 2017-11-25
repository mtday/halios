package halios.model.move;

import static halios.model.move.MoveType.NOOP;

import halios.model.Ship;

import javax.annotation.Nonnull;

public class NoopMove extends Move {
    public NoopMove(@Nonnull final Ship ship) {
        super(NOOP, ship);
    }

    @Override
    @Nonnull
    public String serialize() {
        return "";
    }
}
