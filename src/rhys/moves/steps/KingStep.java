package rhys.moves.steps;

import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;
import rhys.util.Move;
import rhys.moves.PieceStep;

import java.util.stream.Stream;

public class KingStep extends PieceStep {

    public KingStep() {
        super(1, true, Stream.concat(Vec2.directions(), Vec2.diagonals()));
    }

    @Override
    protected boolean moveCondition(BoardState state, Move move, Side side) {
        return emptyOrTake(state, move.to(), side) && state.pieces().filter(side::other).flatMap(p -> p.targets(state)).noneMatch(move::to);
    }

}
