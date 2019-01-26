package a8;

import a8.ImageEditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class ToolBeltWidget extends JPanel implements MouseListener {
    private JPanel toolbelt;
    private JButton do_undo;
    private JButton circle;
    private JButton rectangle;
    private JButton paste;
    private JButton import_image;
    private String selected_tool;
    private ImageEditorModel model;

    public ToolBeltWidget(ImageEditorModel model) {
        this.toolbelt = new JPanel();
        toolbelt.setLayout(new BorderLayout());

        this.do_undo = new JButton();
        do_undo.setText("Undo");
        do_undo.addMouseListener(this);

        this.circle = new JButton();
        circle.setText("Circle Brush");
        circle.addMouseListener(this);


        this.rectangle = new JButton();
        rectangle.setText("Rectangle Brush");
        rectangle.addMouseListener(this);


        this.paste = new JButton();
        paste.setText("Copy");
        paste.addMouseListener(this);


        this.import_image = new JButton();
        import_image.setText("Import Img");
        import_image.addMouseListener(this);


        add(do_undo, BorderLayout.WEST);
        add(circle, BorderLayout.WEST);
        add(rectangle, BorderLayout.WEST);
        add(paste, BorderLayout.WEST);
        add(import_image, BorderLayout.WEST);


        this.selected_tool = null;
        this.model = model;
    }

    public void setTool(String toolname) {
        selected_tool = toolname;
    }

    public String getCurrentSubToolName() {
        return selected_tool;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(circle)) {
            model.setCircle(true);
        } else if (e.getSource().equals(rectangle)) {
            model.setCircle(false);
        } else if (e.getSource().equals(do_undo)) {
            model.setPrevious();
        } else if (e.getSource().equals(import_image)) {
            try {
                model.fireReRender();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource().equals(paste)) {
            if (model.getHasSelection()) {
                model.setHasSelection(false);
                model.createSubPicCopy();
                model.replaceRect();
            }
        }
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
}
