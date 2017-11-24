package mtday.halios.starterkit;

import mtday.halios.model.Entity;
import mtday.halios.model.Position;

public class Collision {
    /**
     * Test whether a given line segment intersects a circular area.
     *
     * @param start  the start of the segment
     * @param end    the end of the segment
     * @param entity the entity to test against
     * @param fudge  an additional safety zone to leave when looking for collisions, probably set it to ship radius
     * @return true if the segment intersects, false otherwise
     */
    public static boolean segmentCircleIntersect(
            final Position start,
            final Position end,
            final Entity entity,
            final double fudge) {
        // Parameterize the segment as start + t * (end - start),
        // and substitute into the equation of a circle
        // Solve for t
        final double radius = entity.getRadius();
        final double startX = start.getX();
        final double startY = start.getY();
        final double endX = end.getX();
        final double endY = end.getY();
        final double centerX = entity.getX();
        final double centerY = entity.getY();
        final double dx = endX - startX;
        final double dy = endY - startY;

        final double a = (dx * dx) + (dy * dy);
        final double b = -2 * ((startX * startX) - (startX * endX)
                            - (startX * centerX) + (endX * centerX)
                            + (startY * startY) - (startY * endY)
                            - (startY * centerY) + (endY * centerY));

        if (a == 0.0) {
            // Start and end are the same point
            return start.distance(entity) <= radius + fudge;
        }

        // Time along segment when closest to the circle (vertex of the quadratic)
        final double t = Math.min(-b / (2 * a), 1.0);
        if (t < 0) {
            return false;
        }

        final double closestX = startX + dx * t;
        final double closestY = startY + dy * t;
        final double closestDistance = new Position(closestX, closestY).distance(entity);

        return closestDistance <= radius + fudge;
    }
}
