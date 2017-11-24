package mtday.halios.model;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import mtday.halios.config.Config;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

import javax.annotation.Nonnull;

public final class Ship extends Entity {
    private static final long serialVersionUID = 1;

    private static final double SHIP_RADIUS = Config.SHIP_RADIUS.getDouble();
    private static final double DOCK_RADIUS = Config.DOCK_RADIUS.getDouble();

    private final DockingStatus dockingStatus;
    private final int dockedPlanet;
    private final int dockingProgress;
    private final int weaponCooldown;

    private Ship(
            final int owner,
            final int id,
            final double x,
            final double y,
            final int health,
            final DockingStatus dockingStatus,
            final int dockedPlanet,
            final int dockingProgress,
            final int weaponCooldown) {
        super(owner, id, x, y, health, SHIP_RADIUS);

        this.dockingStatus = dockingStatus;
        this.dockedPlanet = dockedPlanet;
        this.dockingProgress = dockingProgress;
        this.weaponCooldown = weaponCooldown;
    }

    public static Ship from(final int owner, @Nonnull final List<String> parts) {
        final int id = parseInt(parts.remove(0));
        final double x = parseDouble(parts.remove(0));
        final double y = parseDouble(parts.remove(0));
        final int health = parseInt(parts.remove(0));

        // Ignoring velocity(x,y) which is always (0,0) in current version.
        parts.remove(0);
        parts.remove(0);

        final DockingStatus dockingStatus = DockingStatus.values()[parseInt(parts.remove(0))];
        final int dockedPlanet = parseInt(parts.remove(0));
        final int dockingProgress = parseInt(parts.remove(0));
        final int weaponCooldown = parseInt(parts.remove(0));

        return new Ship(owner, id, x, y, health, dockingStatus, dockedPlanet, dockingProgress, weaponCooldown);
    }

    public int getWeaponCooldown() {
        return weaponCooldown;
    }

    public DockingStatus getDockingStatus() {
        return dockingStatus;
    }

    public int getDockingProgress() {
        return dockingProgress;
    }

    public int getDockedPlanet() {
        return dockedPlanet;
    }

    public boolean canDock(final Planet planet) {
        return distance(planet) <= SHIP_RADIUS + DOCK_RADIUS + planet.getRadius();
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
                .append("dockingStatus", getDockingStatus())
                .append("dockedPlanet", getDockedPlanet())
                .append("dockingProgress", getDockingProgress())
                .append("weaponCooldown", getWeaponCooldown())
                .build();
    }
}
