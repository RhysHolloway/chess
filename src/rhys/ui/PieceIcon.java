package rhys.ui;

import com.kitfox.svg.SVGCache;
import com.kitfox.svg.app.beans.SVGIcon;
import rhys.pieces.Piece;

import javax.swing.*;
import java.net.URI;
import java.net.URL;
import java.util.Locale;

public class PieceIcon extends SVGIcon {

    public PieceIcon(Piece piece) {
        var universe = SVGCache.getSVGUniverse();
        URI svg = universe.loadSVG(uri(piece));
        this.setScaleToFit(true);
        this.setSvgURI(svg);
    }

    private static URL uri(Piece piece) {
        URL resource = PieceIcon.class.getResource(("/" + piece.side + "/" + piece.type.name()).toLowerCase(Locale.ENGLISH) + ".svg");
        if (resource == null) {
            throw new RuntimeException("Cannot load icon file for the " + piece + "!");
        } else {
            return resource;
        }
    }

}
