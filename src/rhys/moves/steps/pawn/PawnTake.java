package rhys.moves.steps.pawn;

import rhys.util.Move;
import rhys.util.Side;
import rhys.util.Vec2;
import rhys.board.BoardState;

public class PawnTake extends PawnStep {

    public PawnTake() {
        super(true, new Vec2(-1, 1), new Vec2(1, 1));
    }

    @Override
    protected boolean moveCondition(BoardState state, Move move, Side side) {
        return take(state, move.to(), side);
    }
}
