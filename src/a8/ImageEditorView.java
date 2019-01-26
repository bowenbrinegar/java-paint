package a8;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ImageEditorView extends JPanel {
	private JFrame main_frame;
	private PictureView frame_view;
	private ImageEditorModel model;
	private ToolChooserWidget chooser_widget;
	private JPanel tool_ui_panel;
	private JPanel tool_ui;
	private JPanel toolbelt_widget;
	private Canvas mag_ui;
	private JTextField link_box;
	private SelectionBox selection_rect;
	private JLayeredPane image_container;

	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		
		setLayout(new BorderLayout());

		image_container = new JLayeredPane();
		frame_view = new PictureView(model.getCurrent());;

		image_container.add(frame_view, 0, 1);
		image_container.setSize(new Dimension(800, 1500));

		add(image_container, BorderLayout.CENTER);
		
		tool_ui_panel = new JPanel();
		tool_ui_panel.setLayout(new BorderLayout());
		
		chooser_widget = new ToolChooserWidget();
		tool_ui_panel.add(chooser_widget, BorderLayout.NORTH);

		toolbelt_widget = new ToolBeltWidget(model);
		tool_ui_panel.add(toolbelt_widget, BorderLayout.EAST);

		this.link_box = new JTextField();
		model.setLinkBox(link_box);
		tool_ui_panel.add(link_box, BorderLayout.SOUTH);

		add(tool_ui_panel, BorderLayout.SOUTH);

		tool_ui = null;

		this.model = model;
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		chooser_widget.addToolChoiceListener(l);
	}
	
	public String getCurrentToolName() {
		return chooser_widget.getCurrentToolName();
	}

	public void installToolUI(JPanel ui) {
		if (tool_ui != null) {
			tool_ui_panel.remove(tool_ui);
		}
		tool_ui = ui;
		tool_ui_panel.add(tool_ui, BorderLayout.CENTER);
		validate();
		main_frame.pack();
	}

	public void updateSliders(Pixel p, PaintBrushTool tool) {
		if (getCurrentToolName() == "Color Picker") {
			tool.getRedSlider().setValue((int) Math.round(p.getRed() * 100));
			tool.getGreenSlider().setValue((int) Math.round(p.getGreen() * 100));
			tool.getBlueSlider().setValue((int) Math.round(p.getBlue() * 100));
		}
	}

	public void updateInspectorBox(ObservablePicture p, PixelInspectorTool tool) {
		if (getCurrentToolName() == "Pixel Inspector") {
			tool.getPixelBox().setPicture(p);
		}

		validate();
		main_frame.pack();
	}

	public void rerender(ObservablePicture p) {
		frame_view.setPicture(p);
		frame_view.revalidate();
		frame_view.repaint();
	}

	public void removeRect() {
		image_container.remove(selection_rect);
	}

	public void addRect() {
		selection_rect = new SelectionBox(new Coordinate(0,0), 0, 0, this, model);
		image_container.add(selection_rect,1, 2);
	}

	public void setRect(Coordinate c, int w, int h) {
		selection_rect = new SelectionBox(c, w, h, this, model);
		image_container.add(selection_rect,1, 2);
	}

	public void updateRect(Coordinate c, int w, int h) {
		selection_rect.setBounds(c.getX(), c.getY(), w, h);
		model.setWidthHeight(w, h);
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void transportSelection() {
		int w = model.getW();
		int h = model.getH();

		updateRect(model.getCoordinate(), w, h);
	}

	public void expandLeft() {
		Coordinate c = model.getCoordinate();

		int w = model.getW() + 3;
		int h = model.getH();

		int x = c.getX() - 3;
		int y = c.getY();

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void contractLeft() {
		Coordinate c = model.getCoordinate();

		int w = model.getW() - 3;
		int h = model.getH();

		int x = c.getX() + 3;
		int y = c.getY();

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void expandRight() {
		Coordinate c = model.getCoordinate();

		int w = model.getW() + 3;
		int h = model.getH();

		int x = c.getX();
		int y = c.getY();

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void contractRight() {
		Coordinate c = model.getCoordinate();

		int w = model.getW() - 3;
		int h = model.getH();

		int x = c.getX();
		int y = c.getY();

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void expandTop() {
		Coordinate c = model.getCoordinate();

		int w = model.getW();
		int h = model.getH() + 3;

		int x = c.getX();
		int y = c.getY() - 3;

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void contractTop() {
		Coordinate c = model.getCoordinate();

		int w = model.getW();
		int h = model.getH() - 3;

		int x = c.getX();
		int y = c.getY() + 3;

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void expandBottom() {
		Coordinate c = model.getCoordinate();

		int w = model.getW();
		int h = model.getH() + 3;

		int x = c.getX();
		int y = c.getY();

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	public void contractBottom() {
		Coordinate c = model.getCoordinate();

		int w = model.getW();
		int h = model.getH() - 3;

		int x = c.getX();
		int y = c.getY();

		selection_rect.setBounds(x, y, w, h);
		model.setWidthHeight(w, h);
		model.setMainCoordinate(new Coordinate(x,y));
		selection_rect.revalidate();
		selection_rect.repaint();
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		frame_view.addMouseMotionListener(l);
	}
	
	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		frame_view.removeMouseMotionListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		frame_view.addMouseListener(l);
	}
	
	public void removeMouseListener(MouseListener l) {
		frame_view.removeMouseListener(l);
	}
}
