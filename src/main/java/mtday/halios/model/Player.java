package mtday.halios.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Player {
    private final int id;
    private final Map<Integer, Ship> ships;

    public Player(final int id, @Nonnull final Map<Integer, Ship> ships) {
        this.id = id;
        this.ships = ships;
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public Map<Integer, Ship> getShips() {
        return ships;
    }

    @Nullable
    public Ship getShip(final int entityId) {
        return ships.get(entityId);
    }

    @Override
    @Nonnull
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", getId())
                .append("ships", getShips())
                .build();
    }
}
