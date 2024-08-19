package rhys.moves.steps;

import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;
import rhys.util.Move;
import rhys.moves.PieceStep;

import java.util.stream.Stream;

public class KnightStep extends PieceStep {

    public KnightStep() {
        super(1, true, Vec2.diagonals().flatMap(d -> Stream.of(new Vec2(d.x() * 2, d.y()), new Vec2(d.x(), d.y() * 2))));
    }

    @Override
    protected boolean moveCondition(BoardState state, Move move, Side side) {
        return emptyOrTake(state, move.to(), side);
    }
}
