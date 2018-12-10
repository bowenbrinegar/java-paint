package a8.Tools;

import a8.ImageEditor;
import a8.ImageEditorModel;
import a8.ImageEditorView;
import a8.Pixels.Coordinate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SelectionBox extends JPanel implements MouseListener, MouseMotionListener, SelectionBoxInterface {
    private JPanel selection_box;
    private int w;
    private int h;
    private ImageEditorView view;
    private ImageEditorModel model;

    public SelectionBox(Coordinate c, int w, int h, ImageEditorView view, ImageEditorModel model) {
        this.view = view;
        this.model = model;

        selection_box = new JPanel();
        setLayout(new BorderLayout());

        add(new Directionals("top", view, model), BorderLayout.NORTH);
        add(new Directionals("left", view, model), BorderLayout.WEST);
        add(new Directionals("right", view, model), BorderLayout.EAST);
        add(new Directionals("bottom", view, model), BorderLayout.SOUTH);

        addMouseListener(this);
        addMouseMotionListener(this);
        setBounds(c.getX(), c.getY(), w, h);
        setBorder(new LineBorder(Color.BLACK, 5));
    }


    public void setWidthHeight(int w, int h) {
        this.w = w;
        this.h = h;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.setExpanding(true);
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
        model.setMainCoordinate(new Coordinate(e.getX(), e.getY()));
        view.transportSelection();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
