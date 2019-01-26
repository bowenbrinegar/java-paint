package a8;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ColorPickerTool implements Tool {
    private ImageEditorModel model;
    private ImageEditorController controller;

    public ColorPickerTool(ImageEditorModel model, ImageEditorController controller) {
        this.model = model;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public JPanel getUI() {
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Pixel p = model.getCurrent().getPixel(e.getX(), e.getY());
        controller.changeSliders(p);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
