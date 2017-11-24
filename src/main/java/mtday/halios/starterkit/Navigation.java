package mtday.halios.starterkit;

import mtday.halios.config.Config;
import mtday.halios.model.Entity;
import mtday.halios.model.Position;
import mtday.halios.model.Ship;
import mtday.halios.model.move.ThrustMove;

import javax.annotation.Nonnull;

public class Navigation {

    public static ThrustMove navigateShipToDock(
            @Nonnull final GameMap gameMap,
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
            @Nonnull final GameMap gameMap,
            @Nonnull final Ship ship,
            @Nonnull final Position targetPos,
            final int maxThrust,
            final boolean avoidObstacles,
            final int maxCorrections,
            final double angularStepRad) {
        if (maxCorrections <= 0) {
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
