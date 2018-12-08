package a8;

import a8.Pixels.Coordinate;
import a8.Tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

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
	
	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		
		setLayout(new BorderLayout());
		
		frame_view = new PictureView(model.getCurrent());
		
		add(frame_view, BorderLayout.CENTER);
		
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
