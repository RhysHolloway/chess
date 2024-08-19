package rhys.moves.steps;

import rhys.util.Move;
import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;
import rhys.moves.PieceStep;

import java.util.stream.Stream;

public class MultiStepAndTake extends PieceStep {
    public MultiStepAndTake(Stream<Vec2> direction) {
        super(true, direction.toArray(Vec2[]::new));
    }

    @Override
    protected boolean moveCondition(BoardState state, Move move, Side side) {
        return emptyOrTake(state, move.to(), side);
    }
}
