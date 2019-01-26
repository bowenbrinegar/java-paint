package a8;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CopyPasteTool implements Tool, CopyTool {
    private ImageEditorModel model;
    private CopyPasteUI ui;

    public CopyPasteTool(ImageEditorModel model) {
        this.model = model;
        this.ui = new CopyPasteUI(1,1,10,10);
    }

    @Override
    public String getName() {
        return "Copy / Paste";
    }

    @Override
    public Canvas getCopyUI() {
        return ui;
    }

    public JPanel getUI() {
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean ifTrue = model.getClipboardFull();
        int x = e.getX();
        int y = e.getY();

        if (ifTrue) {
            System.out.println("hit");
            model.pasteIfPossible(x, y);
            return;
        }

        model.setCurrentlySelecting(true);
        model.setStartCoordinate(new Coordinate(x, y));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (model.getCurrentlySelecting()) {
            model.setHasSelection(true);
        }

        model.setCurrentlySelecting(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        model.setEndCoordinate(new Coordinate(e.getX(), e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
