package mtday.halios.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum Config {
    PLAYERS_MAX,
    SPEED_MAX,
    SHIP_RADIUS,
    SHIP_HEALTH_MAX,
    SHIP_HEALTH_BASE,
    WEAPON_COOLDOWN,
    WEAPON_RADIUS,
    WEAPON_DAMAGE,
    EXPLOSION_RADIUS,
    DOCK_RADIUS,
    DOCK_TURNS,
    BASE_PRODUCTIVITY,
    SPAWN_RADIUS,
    FUDGE_FACTOR,
    NAVIGATION_CORRECTIONS,
    MIN_DISTANCE

    ;

    private static final PropertiesConfiguration CONFIG;
    static {
        CONFIG = new PropertiesConfiguration();
        try (final InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.properties");
             final InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            CONFIG.read(inputStreamReader);
        } catch (final ConfigurationException | IOException exception) {
            throw new RuntimeException("Failed to load configuration properties file", exception);
        }
    }

    @Nonnull
    private String getKey() {
        return name().toLowerCase().replaceAll("_", ".");
    }

    @Nullable
    public String getString() {
        return CONFIG.getString(getKey());
    }

    @Nonnull
    public String getString(@Nullable final String defaultValue) {
        return CONFIG.getString(getKey(), defaultValue);
    }

    public boolean getBoolean() {
        return CONFIG.getBoolean(getKey());
    }

    public boolean getBoolean(final boolean defaultValue) {
        return CONFIG.getBoolean(getKey(), defaultValue);
    }

    public int getInt() {
        return CONFIG.getInt(getKey());
    }

    public int getBoolean(final int defaultValue) {
        return CONFIG.getInt(getKey(), defaultValue);
    }

    public double getDouble() {
        return CONFIG.getDouble(getKey());
    }

    public double getDouble(final double defaultValue) {
        return CONFIG.getDouble(getKey(), defaultValue);
    }
}
