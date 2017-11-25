package halios.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

public final class Planet extends Entity {
    private static final long serialVersionUID = 1;

    private int remainingProduction;
    private int currentProduction;
    private int dockingSpots;

    @Nonnull
    private List<Integer> dockedShips = new LinkedList<>();

    public Planet(final int id) {
        setId(id);
    }

    public int getRemainingProduction() {
        return remainingProduction;
    }

    public void setRemainingProduction(final int remainingProduction) {
        this.remainingProduction = remainingProduction;
    }

    public int getCurrentProduction() {
        return currentProduction;
    }

    public void setCurrentProduction(final int currentProduction) {
        this.currentProduction = currentProduction;
    }

    public int getDockingSpots() {
        return dockingSpots;
    }

    public void setDockingSpots(final int dockingSpots) {
        this.dockingSpots = dockingSpots;
    }

    @Nonnull
    public List<Integer> getDockedShips() {
        return dockedShips;
    }

    public void setDockedShips(@Nonnull final List<Integer> dockedShips) {
        this.dockedShips = dockedShips;
    }

    public boolean isFull() {
        return dockedShips.size() == dockingSpots;
    }

    public boolean isOwned() {
        return getOwner() != -1;
    }

    @Override
    @Nonnull
    public ToStringBuilder toString(@Nonnull final ToStringBuilder str) {
        return super.toString(str)
                .append("remainingProduction", getRemainingProduction())
                .append("currentProduction", getCurrentProduction())
                .append("dockingSpots", getDockingSpots())
                .append("dockedShips", getDockedShips());
    }

    @Override
    @Nonnull
    public String toString() {
        return toString(new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)).build();
    }
}
