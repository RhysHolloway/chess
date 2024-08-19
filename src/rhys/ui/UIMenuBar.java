package rhys.ui;

import rhys.board.Board;

import javax.swing.*;

public class UIMenuBar extends JMenuBar {

    public UIMenuBar(UIBoard uiBoard, Board board) {
        super();
        JMenu file = new JMenu("File");
        JMenuItem reset = new JMenuItem("Reset");
        file.add(reset);
        reset.addActionListener(e -> board.reset(uiBoard::update));
        this.add(file);

    }

}
