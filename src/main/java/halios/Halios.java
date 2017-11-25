package halios;

import static java.lang.Integer.parseInt;

import halios.io.IO;
import halios.model.Bot;

import javax.annotation.Nonnull;

public class Halios implements Runnable {
    @Override
    public void run() {
        final int playerId = parseInt(IO.read());
        System.setProperty("log.file", String.format("%d_mtday.log", playerId));
        new Bot(playerId, Halios.class.getSimpleName()).run();
    }

    public static void main(@Nonnull final String[] args) {
        new Halios().run();
    }
}
