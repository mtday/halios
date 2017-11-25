package halios.model;

import static java.util.stream.Collectors.toList;

import halios.model.move.Move;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.annotation.Nonnull;

public class Player {
    private final int id;
    @Nonnull
    private final Map<Integer, Ship> ships = new TreeMap<>();

    public Player(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public Map<Integer, Ship> getShips() {
        return ships;
    }

    @Nonnull
    public Ship getShip(final int shipId) {
        return ships.get(shipId);
    }

    @Nonnull
    public List<Move> turn(final int turn, @Nonnull final Game game) {
        return game.getPlayer().getShips().values().stream()
                .map(Ship::getStrategy)
                .filter(Objects::nonNull)
                .map(strategy -> strategy.run(game))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    @Nonnull
    protected ToStringBuilder toString(@Nonnull final ToStringBuilder str) {
        str.append("id", getId());
        str.append("ships", getShips());
        return str;
    }

    @Override
    @Nonnull
    public String toString() {
        return toString(new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)).build();
    }
}
