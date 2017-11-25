package halios.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

public class Entity extends Position {
    private static final long serialVersionUID = 1;

    private int owner;
    private int id;
    private int health;
    private double radius;

    public int getOwner() {
        return owner;
    }

    public void setOwner(final int owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    @Override
    @Nonnull
    protected ToStringBuilder toString(@Nonnull final ToStringBuilder str) {
        str.append("id", getId());
        str.append("owner", getOwner());
        str.append("health", getHealth());
        str.append("radius", getRadius());
        return super.toString(str);
    }

    @Override
    @Nonnull
    public String toString() {
        return toString(new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)).build();
    }
}
