package halios.model;

import halios.config.Config;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.awt.geom.Point2D;

import javax.annotation.Nonnull;

public class Position extends Point2D.Double {
    private static final long serialVersionUID = 1;

    private static final double TWO_PI = 2 * Math.PI;

    public Position() {
        super();
    }

    public Position(final double x, final double y) {
        super(x, y);
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public static int toDegrees(final double radians) {
        // Make sure return value is in [0, 360) as required by game engine.
        final long degrees = Math.round(Math.toDegrees(radians));
        return (int) (((degrees % 360L) + 360L) % 360L);
    }

    public int orientTowardsInDeg(final Position target) {
        return toDegrees(orientTowardsInRad(target));
    }

    public double orientTowardsInRad(final Position target) {
        final double dx = target.getX() - getX();
        final double dy = target.getY() - getY();
        return Math.atan2(dy, dx) + TWO_PI;
    }

    @Nonnull
    public Position getClosestPoint(final Entity target) {
        final double radius = target.getRadius() + Config.MIN_DISTANCE.getDouble();
        final double radians = target.orientTowardsInRad(this);
        final double x = target.getX() + radius * Math.cos(radians);
        final double y = target.getY() + radius * Math.sin(radians);
        return new Position(x, y);
    }

    @Nonnull
    protected ToStringBuilder toString(@Nonnull final ToStringBuilder str) {
        str.append("x", getX());
        str.append("y", getY());
        return str;
    }

    @Override
    @Nonnull
    public String toString() {
        return toString(new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)).build();
    }
}
