package mtday.halios.model;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public final class Planet extends Entity {
    private static final long serialVersionUID = 1;

    private final int remainingProduction;
    private final int currentProduction;
    private final int dockingSpots;

    @Nonnull
    private final List<Integer> dockedShips;

    private Planet(
            final int owner,
            final int id,
            final double x,
            final double y,
            final int health,
            final double radius,
            final int dockingSpots,
            final int currentProduction,
            final int remainingProduction,
            @Nonnull final List<Integer> dockedShips) {
        super(owner, id, x, y, health, radius);

        this.dockingSpots = dockingSpots;
        this.currentProduction = currentProduction;
        this.remainingProduction = remainingProduction;
        this.dockedShips = dockedShips;
    }

    public static Planet from(@Nonnull final List<String> parts) {
        final int id = parseInt(parts.remove(0));
        final double x = parseDouble(parts.remove(0));
        final double y = parseDouble(parts.remove(0));
        final int health = parseInt(parts.remove(0));
        final double radius = parseDouble(parts.remove(0));
        final int dockingSpots = parseInt(parts.remove(0));
        final int currentProduction = parseInt(parts.remove(0));
        final int remainingProduction = parseInt(parts.remove(0));
        final int hasOwner = parseInt(parts.remove(0));
        final int ownerCandidate = parseInt(parts.remove(0));
        final int owner = (hasOwner == 1) ? ownerCandidate : -1;
        final int dockedShipCount = parseInt(parts.remove(0));
        final List<Integer> dockedShips = new ArrayList<>(dockedShipCount);
        for (int i = 0; i < dockedShipCount; ++i) {
            dockedShips.add(parseInt(parts.remove(0)));
        }

        return new Planet(owner, id, x, y, health, radius, dockingSpots, currentProduction, remainingProduction,
                dockedShips);
    }

    public int getRemainingProduction() {
        return remainingProduction;
    }

    public int getCurrentProduction() {
        return currentProduction;
    }

    public int getDockingSpots() {
        return dockingSpots;
    }

    @Nonnull
    public List<Integer> getDockedShips() {
        return dockedShips;
    }

    public boolean isFull() {
        return dockedShips.size() == dockingSpots;
    }

    public boolean isOwned() {
        return getOwner() != -1;
    }

    @Override
    @Nonnull
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", getId())
                .append("owner", getOwner())
                .append("health", getHealth())
                .append("radius", getRadius())
                .append("x", getX())
                .append("y", getY())
                .append("remainingProduction", getRemainingProduction())
                .append("currentProduction", getCurrentProduction())
                .append("dockingSpots", getDockingSpots())
                .append("dockedShips", getDockedShips())
                .build();
    }
}
