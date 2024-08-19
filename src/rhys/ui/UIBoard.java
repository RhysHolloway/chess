package rhys.ui;

import rhys.board.Board;
import rhys.util.Move;
import rhys.pieces.Piece;
import rhys.util.Vec2;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UIBoard extends JPanel {

    private final Map<Vec2, JButton> squares;
    private final GrabbedPiece grabbed = new GrabbedPiece();

    public UIBoard(Board board) {
        this.setLayout(new GridLayout(8, 8));
        this.setMinimumSize(new Dimension(200, 200));
        this.setPreferredSize(new Dimension(600, 600));

        this.squares = IntStream.range(0, 8)
                .mapToObj(y -> IntStream.range(0, 8).mapToObj(x -> new Vec2(x, 7 - y)))
                .flatMap(stream -> stream)
                .collect(Collectors.toMap(Function.identity(), p -> {
                    JButton square = new JButton();
                    square.setBackground(color(p));
                    board.at(p).ifPresent(piece -> setPiece(square, piece));
                    this.add(square);
                    return square;
                }));

        squares.forEach((pos, square) -> square.addActionListener(l -> {
            if (grabbed.position == null) {
                grab(board, pos);
            } else {
                Move move = new Move(grabbed.position, pos);
                boolean moved = board.move(move, this::update);
                grabbed.moves.forEach(p -> squares.get(p).setBackground(color(p)));
                grabbed.position = null;
                if (!moved) {
                    grab(board, pos);
                }
            }
        }));

    }

    public void update(Piece piece) {
        setPiece(squares.get(piece.position), piece);
    }

    private void grab(Board board, Vec2 pos) {
        board.at(pos).filter(p -> p.side.equals(board.turn())).ifPresent(piece -> {
            grabbed.moves = piece.moves(board.state()).collect(Collectors.toSet());
            grabbed.moves.forEach(p -> squares.get(p).setBackground(Color.blue));
            grabbed.position = pos;
        });
    }

    private static final Color BROWN = new Color(0x8d5959);

    public static Color color(Vec2 position) {
        return (position.x() + position.y()) % 2 == 0 ? Color.WHITE : BROWN;
    }

    public static void setPiece(JButton button, Piece piece) {
        button.setIcon(piece.type == null ? null : new PieceIcon(piece));
    }

    private static class GrabbedPiece {
        public Vec2 position;
        public Set<Vec2> moves;
    }

}
