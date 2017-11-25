package halios.model;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;

import halios.config.Config;
import halios.starterkit.Collision;
import halios.strategy.DockStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Nonnull;

public final class Game {
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private final int width;
    private final int height;
    private final int playerId;
    @Nonnull
    private final Map<Integer, Player> players;
    @Nonnull
    private final Map<Integer, Planet> planets;
    @Nonnull
    private final Map<Integer, Ship> ships;

    private Game(final int width, final int height, final int playerId) {
        this.width = width;
        this.height = height;
        this.playerId = playerId;

        players = new TreeMap<>();
        planets = new TreeMap<>();
        ships = new TreeMap<>();
    }

    public static Game from(final int playerId, @Nonnull final String sizeInfo) {
        final String[] parts = sizeInfo.split(" ");
        final int width = parseInt(parts[0]);
        final int height = parseInt(parts[1]);
        return new Game(width, height, playerId);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Nonnull
    public Map<Integer, Player> getPlayers() {
        return players;
    }

    @Nonnull
    public Player getPlayer() {
        return getPlayers().get(getPlayerId());
    }

    @Nonnull
    public Planet getPlanet(final int planetId) {
        return planets.get(planetId);
    }

    @Nonnull
    public Map<Integer, Planet> getPlanets() {
        return planets;
    }

    @Nonnull
    public Map<Integer, Ship> getShips() {
        return ships;
    }

    @Nonnull
    public Map<Integer, Ship> getShipsForPlayer(final int playerId) {
        return players.get(playerId).getShips();
    }

    @Nonnull
    public Ship getShip(final int shipId) {
        return ships.get(shipId);
    }

    @Nonnull
    public ArrayList<Entity> objectsBetween(@Nonnull final Position start, @Nonnull final Position target) {
        final ArrayList<Entity> entitiesFound = new ArrayList<>();
        addEntitiesBetween(entitiesFound, start, target, planets.values());
        addEntitiesBetween(entitiesFound, start, target, ships.values());
        return entitiesFound;
    }

    private static void addEntitiesBetween(
            final List<Entity> entitiesFound,
            final Position start,
            final Position target,
            final Collection<? extends Entity> entitiesToCheck) {
        for (final Entity entity : entitiesToCheck) {
            if (entity.equals(start) || entity.equals(target)) {
                continue;
            }
            if (Collision.segmentCircleIntersect(start, target, entity, Config.FUDGE_FACTOR.getDouble())) {
                entitiesFound.add(entity);
            }
        }
    }

    @Nonnull
    public Map<Double, Entity> nearbyEntitiesByDistance(@Nonnull final Entity entity) {
        final Map<Double, Entity> entityByDistance = new TreeMap<>();

        for (final Planet planet : planets.values()) {
            if (planet.equals(entity)) {
                continue;
            }
            entityByDistance.put(entity.distance(planet), planet);
        }

        for (final Ship ship : ships.values()) {
            if (ship.equals(entity)) {
                continue;
            }
            entityByDistance.put(entity.distance(ship), ship);
        }

        return entityByDistance;
    }

    public void update(@Nonnull final String input) {
        LOGGER.info("Updating game");
        LOGGER.info("My Player ID: {}", getPlayerId());
        final List<String> parts = new LinkedList<>(asList(input.split(" ")));
        final int numberOfPlayers = parseInt(parts.remove(0));
        LOGGER.info("  Number of Players: {}", numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; ++i) {
            final int playerId = parseInt(parts.remove(0));
            LOGGER.info("  Player ID: {}", playerId);
            final Player currentPlayer = players.computeIfAbsent(playerId, (ignored) -> new Player(playerId));
            players.put(playerId, currentPlayer);

            final int numberOfShips = parseInt(parts.remove(0));
            LOGGER.info("    Number of Ships: {}", numberOfShips);
            for (int s = 0; s < numberOfShips; ++s) {
                final int id = parseInt(parts.remove(0));
                final Ship ship = ships.computeIfAbsent(id, (ignored) -> new Ship(id, playerId));
                ships.put(ship.getId(), ship);
                currentPlayer.getShips().put(ship.getId(), ship);

                ship.setX(parseDouble(parts.remove(0)));
                ship.setY(parseDouble(parts.remove(0)));
                ship.setHealth(parseInt(parts.remove(0)));

                // Ignoring velocity(x,y) which is always (0,0) in current version.
                parts.remove(0);
                parts.remove(0);

                ship.setDockingStatus(DockingStatus.values()[parseInt(parts.remove(0))]);
                ship.setDockedPlanet(parseInt(parts.remove(0)));
                ship.setDockingProgress(parseInt(parts.remove(0)));
                ship.setWeaponCooldown(parseInt(parts.remove(0)));
                LOGGER.info("    Ship: {}", ship);

                if (ship.getStrategy() == null) {
                    LOGGER.info("      Assigning dock strategy");
                    // TODO: How to assign strategies?
                    ship.setStrategy(new DockStrategy(ship));
                }
            }
        }

        final int numberOfPlanets = parseInt(parts.remove(0));
        LOGGER.info("  Number of Planets: {}", numberOfPlanets);
        for (int i = 0; i < numberOfPlanets; ++i) {
            final int id = parseInt(parts.remove(0));
            final Planet planet = planets.computeIfAbsent(id, (ignored) -> new Planet(id));
            planets.put(planet.getId(), planet);

            planet.setX(parseDouble(parts.remove(0)));
            planet.setY(parseDouble(parts.remove(0)));
            planet.setHealth(parseInt(parts.remove(0)));
            planet.setRadius(parseDouble(parts.remove(0)));
            planet.setDockingSpots(parseInt(parts.remove(0)));
            planet.setCurrentProduction(parseInt(parts.remove(0)));
            planet.setRemainingProduction(parseInt(parts.remove(0)));
            final int hasOwner = parseInt(parts.remove(0));
            final int ownerCandidate = parseInt(parts.remove(0));
            planet.setOwner((hasOwner == 1) ? ownerCandidate : -1);
            final int dockedShipCount = parseInt(parts.remove(0));
            final List<Integer> dockedShips = new ArrayList<>(dockedShipCount);
            for (int d = 0; d < dockedShipCount; ++d) {
                dockedShips.add(parseInt(parts.remove(0)));
            }
            planet.setDockedShips(dockedShips);
            LOGGER.info("  Planet: {}", planet);
        }
    }
}
