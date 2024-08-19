package rhys.moves.steps.pawn;

import rhys.util.Move;
import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;
import rhys.moves.PieceStep;

public class DoubleStep extends PieceStep {
    public DoubleStep() {
        super(1, false, new Vec2(0, 2));
    }

    @Override
    protected boolean moveCondition(BoardState state, Move move, Side side) {
        return move.from().y() == side.offset(1) && empty(state, move.from().add(0, side.forward())) && empty(state, move.to());
    }

}
