package rhys.moves.steps.pawn;

import rhys.util.Move;
import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;

public class SingleStep extends PawnStep {
    public SingleStep() {
        super(false, new Vec2(0, 1));
    }

    @Override
    protected boolean moveCondition(BoardState state, Move move, Side side) {
        return empty(state, move.to());
    }
}
