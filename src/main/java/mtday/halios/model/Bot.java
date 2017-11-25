package mtday.halios.model;

import mtday.halios.io.Comms;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;

public abstract class Bot implements Runnable {
    @Nonnull
    private final String name;

    @Nonnull
    private final Comms comms = new Comms();

    public Bot(@Nonnull final String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        final int playerId = comms.readPlayerId();
    }

    @Override
    @Nonnull
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", getName())
                .build();
    }
}
