package mtday.halios.model.move;

import mtday.halios.model.Ship;

import javax.annotation.Nonnull;

public class ThrustMove extends Move {
    private final int angleDeg;
    private final int thrust;

    public ThrustMove(@Nonnull final Ship ship, final int angleDeg, final int thrust) {
        super(MoveType.THRUST, ship);
        this.thrust = thrust;
        this.angleDeg = angleDeg;
    }

    public int getAngle() {
        return angleDeg;
    }

    public int getThrust() {
        return thrust;
    }

    @Override
    @Nonnull
    public String serialize() {
        return String.format("t %d %d %d", getShip().getId(), getThrust(), getAngle());
    }
}
