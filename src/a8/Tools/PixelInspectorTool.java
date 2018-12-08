package a8.Tools;

import a8.*;
import a8.Pixels.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PixelInspectorTool implements Tool, InspectorTool {

	private PixelInspectorUI ui;
	private ImageEditorModel model;
	private ObservablePicture picture;
	private Coordinate coordinate;
	private Pixel pixel;
	private ImageEditorController controller;
	
	public PixelInspectorTool(ImageEditorModel model, ImageEditorController controller) {
		this.model = model;
		this.controller = controller;
		expandAndSet(model.getCurrent().extract(0,0, 25,25));
		ui = new PixelInspectorUI(picture);
	}

	private void expandAndSet(SubPicture sub) {
		int w = 125;
		int h = 125;

		Pixel[][] arr = new Pixel[w][h];
		Pixel[][] res = expander(arr, sub, 0, 0, 0, 0);

		this.picture = new ObservablePictureImpl(new MutablePixelArrayPicture(res, "magnified"));
	}

	private Pixel[][] expander(Pixel[][] arr, SubPicture sub, int curr_i, int curr_j, int delta_x, int delta_y) {
		int i;
		int j;
		for (i = curr_i; i < curr_i + 25; i++) {
			for (j = curr_j; j < curr_j + 25; j++) {
				arr[i][j] = sub.getPixel(delta_x, delta_y);
			}
		}

		if (curr_j + 25 < 125) {
			return expander(arr, sub, curr_i, curr_j + 25, delta_x, delta_y + 1);
		} else if (curr_i + 25 < 125){
			return expander(arr, sub, curr_i + 25, 0, delta_x + 1, 0);
		} else {
			return arr;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			ui.setInfo(e.getX(), e.getY(), model.getPixel(e.getX(), e.getY()));
		}
		catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
		}

		this.coordinate = new Coordinate(e.getX(), e.getY());
		this.pixel = model.getPixel(e.getX(), e.getY());

		SubPicture sub = model.getCurrent().extract(e.getX(), e.getY(),25,25);
		expandAndSet(sub);
		controller.setInspectorBox(picture);
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
	public String getName() {
		return "Pixel Inspector";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	public PictureView getPixelBox() {
		return ui.getPixelBox();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			ui.setInfo(e.getX(), e.getY(), model.getPixel(e.getX(), e.getY()));
		}
		catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
