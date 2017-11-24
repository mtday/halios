package mtday.halios.io;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import mtday.halios.model.move.Move;
import mtday.halios.starterkit.GameMap;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

public class Comms {
    public int readPlayerId() {
        return parseInt(readLine().remove(0));
    }

    public GameMap readGame(final int playerId) {
        final GameMap gameMap = GameMap.from(playerId, readLine());
        gameMap.update(readLine());
        return gameMap;
    }

    public void updateGame(@Nonnull final GameMap gameMap) {
        gameMap.update(readLine());
    }

    public void sendBotName(@Nonnull final String botName) {
        System.out.println(botName);
    }

    public void send(@Nonnull final Collection<Move> moves) {
        final String output = String.join(" ", moves.stream()
                .map(Move::serialize)
                .collect(toList()));

        System.out.println(output);
    }

    private List<String> readLine() {
        try {
            final StringBuilder builder = new StringBuilder();
            int buffer;

            while ((buffer = System.in.read()) >= 0) {
                if (buffer == '\n') {
                    break;
                }
                if (buffer == '\r') {
                    // Ignore carriage return if on windows for manual testing.
                    continue;
                }
                builder.append((char)buffer);
            }
            return new LinkedList<>(asList(builder.toString().trim().split(" ")));
        } catch(final IOException ioException) {
            throw new RuntimeException("Failed to read input", ioException);
        }
    }
}
