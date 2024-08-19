package rhys.board;

import rhys.util.Move;
import rhys.pieces.Piece;
import rhys.util.Vec2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class BoardHistory {

    public record PreviousMove(Move move, Optional<Piece> taken) { }

    public ArrayList<PreviousMove> moves = new ArrayList<>();

    public void reset() {
        moves.clear();
    }

    public void addMove(Move move, Optional<Piece> taken) {
        moves.add(new PreviousMove(move, taken));
    }

    public PreviousMove undoMove(Board board) {
        if (this.moves.isEmpty())
            return null;

        PreviousMove prev = this.moves.remove(this.moves.size() - 1);

        board.pieces.stream().filter(piece -> piece.position.equals(prev.move.to())).findFirst().ifPresent(piece -> piece.position = prev.move.from());
        prev.taken.ifPresent(board.pieces::add);

        board.state.turn.decrement();

        return prev;
    }

    public ArrayList<Vec2> movesOf(Piece piece) {
        ArrayList<Vec2> pieceMoves = new ArrayList<>();
        Vec2 last = piece.position;
        pieceMoves.add(last);
        for (int i = moves.size() - 1; i > 0; i--) {
            Move move = moves.get(i).move;
            if (last.equals(move.to())) {
                last = move.from();
                pieceMoves.add(last);
            }
        }
        Collections.reverse(pieceMoves);
        return pieceMoves;
    }
}
