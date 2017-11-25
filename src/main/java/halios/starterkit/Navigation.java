package halios.starterkit;

import halios.config.Config;
import halios.model.Entity;
import halios.model.Game;
import halios.model.Position;
import halios.model.Ship;
import halios.model.move.ThrustMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class Navigation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Navigation.class);

    public static ThrustMove navigateShipToDock(
            @Nonnull final Game gameMap,
            @Nonnull final Ship ship,
            @Nonnull final Entity dockTarget,
            final int maxThrust) {
        final int maxCorrections = Config.NAVIGATION_CORRECTIONS.getInt();
        final boolean avoidObstacles = true;
        final double angularStepRad = Math.PI / 180.0;
        final Position targetPos = ship.getClosestPoint(dockTarget);

        return navigateShipTowardsTarget(
                gameMap, ship, targetPos, maxThrust, avoidObstacles, maxCorrections, angularStepRad);
    }

    public static ThrustMove navigateShipTowardsTarget(
            @Nonnull final Game gameMap,
            @Nonnull final Ship ship,
            @Nonnull final Position targetPos,
            final int maxThrust,
            final boolean avoidObstacles,
            final int maxCorrections,
            final double angularStepRad) {
        if (maxCorrections <= 0) {
            LOGGER.error("Hit max corrections!");
            return null;
        }

        final double distance = ship.distance(targetPos);
        final double angleRad = ship.orientTowardsInRad(targetPos);

        if (avoidObstacles && !gameMap.objectsBetween(ship, targetPos).isEmpty()) {
            final double newTargetDx = Math.cos(angleRad + angularStepRad) * distance;
            final double newTargetDy = Math.sin(angleRad + angularStepRad) * distance;
            final Position newTarget = new Position(ship.getX() + newTargetDx, ship.getY() + newTargetDy);

            return navigateShipTowardsTarget(
                    gameMap, ship, newTarget, maxThrust, true, (maxCorrections - 1), angularStepRad);
        }

        final int thrust;
        if (distance < maxThrust) {
            // Do not round up, since overshooting might cause collision.
            thrust = (int) distance;
        } else {
            thrust = maxThrust;
        }

        final int angleDeg = Position.toDegrees(angleRad);

        return new ThrustMove(ship, angleDeg, thrust);
    }
}
