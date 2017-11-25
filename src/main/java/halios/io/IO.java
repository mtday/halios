package halios.io;

import java.io.IOException;

import javax.annotation.Nonnull;

public class IO {
    @Nonnull
    public static String read() {
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
                builder.append((char) buffer);
            }
            return builder.toString().trim();
        } catch (final IOException ioException) {
            throw new RuntimeException("Failed to read input", ioException);
        }
    }

    public static void write(@Nonnull final String output) {
        System.out.print(output);
    }

    public static void flush() {
        System.out.println();
        System.out.flush();
    }
}
