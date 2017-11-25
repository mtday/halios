package halios.strategy;

import halios.model.Game;
import halios.model.move.Move;

import javax.annotation.Nonnull;

public interface Strategy {
    @Nonnull
    Move run(@Nonnull Game game);
}
