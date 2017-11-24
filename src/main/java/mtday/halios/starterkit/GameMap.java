package mtday.halios.starterkit;

import mtday.halios.config.Config;
import mtday.halios.model.Entity;
import mtday.halios.model.Planet;
import mtday.halios.model.Player;
import mtday.halios.model.Position;
import mtday.halios.model.Ship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class GameMap {
    private final int width;
    private final int height;
    private final int playerId;
    @Nonnull
    private final List<Player> players;
    @Nonnull
    private final Map<Integer, Planet> planets;
    @Nonnull
    private final List<Ship> ships;

    private GameMap(final int width, final int height, final int playerId) {
        this.width = width;
        this.height = height;
        this.playerId = playerId;

        players = new ArrayList<>(Config.PLAYERS_MAX.getInt());
        planets = new TreeMap<>();
        ships = new LinkedList<>();
    }

    public static GameMap from(final int playerId, @Nonnull final List<String> parts) {
        final int width = Integer.parseInt(parts.remove(0));
        final int height = Integer.parseInt(parts.remove(0));
        return new GameMap(width, height, playerId);
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
    public List<Player> getPlayers() {
        return players;
    }

    @Nonnull
    public Player getPlayer() {
        return getPlayers().get(getPlayerId());
    }

    @Nullable
    public Ship getShip(final int playerId, final int shipId) {
        return Optional.ofNullable(players.get(playerId)).map(player -> player.getShip(shipId)).orElse(null);
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
    public List<Ship> getShips() {
        return ships;
    }

    @Nonnull
    public ArrayList<Entity> objectsBetween(@Nonnull final Position start, @Nonnull final Position target) {
        final ArrayList<Entity> entitiesFound = new ArrayList<>();
        addEntitiesBetween(entitiesFound, start, target, planets.values());
        addEntitiesBetween(entitiesFound, start, target, ships);
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

        for (final Ship ship : ships) {
            if (ship.equals(entity)) {
                continue;
            }
            entityByDistance.put(entity.distance(ship), ship);
        }

        return entityByDistance;
    }

    public void update(@Nonnull final List<String> parts) {
        players.clear();
        planets.clear();
        ships.clear();

        final int numberOfPlayers = Integer.parseInt(parts.remove(0));
        for (int i = 0; i < numberOfPlayers; ++i) {
            final Map<Integer, Ship> currentPlayerShips = new TreeMap<>();
            final int playerId = Integer.parseInt(parts.remove(0));
            final Player currentPlayer = new Player(playerId, currentPlayerShips);
            players.add(currentPlayer);

            final long numberOfShips = Long.parseLong(parts.remove(0));
            for (int s = 0; s < numberOfShips; ++s) {
                final Ship ship = Ship.from(playerId, parts);
                ships.add(ship);
                currentPlayerShips.put(ship.getId(), ship);
            }
        }

        final int numberOfPlanets = Integer.parseInt(parts.remove(0));
        for (int i = 0; i < numberOfPlanets; ++i) {
            final List<Integer> dockedShips = new ArrayList<>();
            final Planet planet = Planet.from(parts);
            planets.put(planet.getId(), planet);
        }
    }
}
