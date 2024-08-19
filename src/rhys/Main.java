package rhys;

import rhys.board.Board;
import rhys.ui.UIBoard;
import rhys.ui.UIMenuBar;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Chess");

            Board board = new Board();
            UIBoard uiBoard = new UIBoard(board);

            frame.setJMenuBar(new UIMenuBar(uiBoard, board));
            frame.getContentPane().add(uiBoard);

//        frame.setSize(600, 600);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

    }

}