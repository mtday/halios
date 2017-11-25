package halios.model;

import halios.config.Config;
import halios.strategy.Strategy;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Ship extends Entity {
    private static final long serialVersionUID = 1;

    private static final double SHIP_RADIUS = Config.SHIP_RADIUS.getDouble();
    private static final double DOCK_RADIUS = Config.DOCK_RADIUS.getDouble();

    @Nonnull
    private DockingStatus dockingStatus = DockingStatus.UNDOCKED;
    private int dockedPlanet;
    private int dockingProgress;
    private int weaponCooldown;

    @Nullable
    private Strategy strategy;

    public Ship(final int id, final int owner) {
        setId(id);
        setOwner(owner);
        setRadius(SHIP_RADIUS);
    }

    @Nonnull
    public DockingStatus getDockingStatus() {
        return dockingStatus;
    }

    public void setDockingStatus(@Nonnull final DockingStatus dockingStatus) {
        this.dockingStatus = dockingStatus;
    }

    public int getDockedPlanet() {
        return dockedPlanet;
    }

    public void setDockedPlanet(final int dockedPlanet) {
        this.dockedPlanet = dockedPlanet;
    }

    public int getDockingProgress() {
        return dockingProgress;
    }

    public void setDockingProgress(final int dockingProgress) {
        this.dockingProgress = dockingProgress;
    }

    public int getWeaponCooldown() {
        return weaponCooldown;
    }

    public void setWeaponCooldown(final int weaponCooldown) {
        this.weaponCooldown = weaponCooldown;
    }

    @Nullable
    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(@Nonnull final Strategy strategy) {
        this.strategy = strategy;
    }

    public boolean canDock(@Nonnull final Planet planet) {
        return distance(planet) <= SHIP_RADIUS + DOCK_RADIUS + planet.getRadius();
    }

    @Override
    @Nonnull
    public ToStringBuilder toString(@Nonnull final ToStringBuilder str) {
        return super.toString(str)
                .append("dockingStatus", getDockingStatus())
                .append("dockedPlanet", getDockedPlanet())
                .append("dockingProgress", getDockingProgress())
                .append("weaponCooldown", getWeaponCooldown());
    }

    @Override
    @Nonnull
    public String toString() {
        return toString(new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)).build();
    }
}
