package a8.Tools;

import a8.ImageEditorModel;
import a8.NoIntersectionException;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class PaintBrushTool implements Tool, PaintTool {

	private PaintBrushToolUI ui;
	private ImageEditorModel model;
	private int brush_size = 5;
	
	public PaintBrushTool(ImageEditorModel model) {
		this.model = model;
		ui = new PaintBrushToolUI(model);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		model.paintAt(e.getX(), e.getY());
		try {
			model.blurAt(e.getX(), e.getY());
		} catch (NoIntersectionException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		model.paintAt(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return "Paint Brush";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	@Override
	public JSlider getRedSlider() {
		return ui.getRedSlider();
	}

	@Override
	public JSlider getGreenSlider() {
		return ui.getGreenSlider();
	}

	@Override
	public JSlider getBlueSlider() {
		return ui.getBlueSlider();
	}
}
