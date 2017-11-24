package mtday.halios.model.move;

import mtday.halios.model.Ship;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

public abstract class Move {
    @Nonnull
    private final MoveType type;
    @Nonnull
    private final Ship ship;

    public Move(@Nonnull final MoveType type, @Nonnull final Ship ship) {
        this.type = type;
        this.ship = ship;
    }

    @Nonnull
    public MoveType getType() {
        return type;
    }

    @Nonnull
    public Ship getShip() {
        return ship;
    }

    @Override
    @Nonnull
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("type", getType())
                .append("ship", getShip())
                .build();
    }

    @Nonnull
    public abstract String serialize();
}
