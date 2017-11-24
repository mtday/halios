package mtday.halios.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

public class Entity extends Position {
    private static final long serialVersionUID = 1;

    private final int owner;
    private final int id;
    private final int health;
    private final double radius;

    public Entity(
            final int owner,
            final int id,
            final double x,
            final double y,
            final int health,
            final double radius) {
        super(x, y);
        this.owner = owner;
        this.id = id;
        this.health = health;
        this.radius = radius;
    }

    public int getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public double getRadius() {
        return radius;
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
                .build();
    }
}
