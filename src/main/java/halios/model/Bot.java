package halios.model;

import halios.io.IO;
import halios.model.move.Move;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class Bot implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    private final int playerId;
    @Nonnull
    private final String name;

    public Bot(final int playerId, @Nonnull final String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            final Game game = Game.from(getPlayerId(), IO.read());

            IO.write(getName());
            IO.flush();

            final int maxTurns = 500; // TODO
            for (int turn = 0; turn < maxTurns; turn++) {
                turn(turn, game);
            }
        } catch (final Throwable error) {
            LOGGER.error("Failed to run bot", error);
            throw new RuntimeException("Failed to run bot", error);
        }
    }

    private void turn(final int turn, @Nonnull final Game game) {
        game.update(IO.read());

        final Player player = game.getPlayer();

        player.turn(turn, game)
                .stream()
                .map(Move::serialize)
                .forEach(IO::write);

        IO.flush();
    }

    @Override
    @Nonnull
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", getName())
                .build();
    }
}
