package a8;

import a8.ImageEditorModel;
import a8.ImageEditorView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Directionals extends JPanel implements MouseMotionListener {
    private JPanel panel;
    private String position;
    private ImageEditorView view;
    private ImageEditorModel model;
    private int prev_x;
    private int prev_y;

    public Directionals(String position, ImageEditorView view, ImageEditorModel model) {
        this.view = view;
        this.model = model;
        this.position = position;
        this.panel = new JPanel();
        setBorder(new LineBorder(Color.BLACK, 5));
        addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        switch(position) {
            case "left":
                if (x < prev_x) {
                    view.expandLeft();
                } else {
                    view.contractLeft();
                }
                break;
            case "right":
                if (x > prev_x) {
                    view.expandRight();
                } else {
                    view.contractRight();
                }
                break;
            case "top":
                if (y < prev_y) {
                    view.expandTop();
                } else {
                    view.contractTop();
                }
                break;
            case "bottom":
                if (y > prev_y) {
                    view.expandBottom();
                } else {
                    view.contractBottom();
                }
                break;
            default:
                break;
        }

        prev_x = x;
        prev_y = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
