package a8;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageEditorModel {

	private Picture original;
	private ObservablePicture current;
	private Pixel current_pixel;
	private double curr_opacity;
	private int brush_size;
	private int blur_factor;
	private boolean isCircle = false;
	private List<ObservablePicture> history;
	private ImageEditorView view;
	private long last_call = System.currentTimeMillis();
	private JTextField link_box;

	public ImageEditorModel(Picture f) {
		original = createCopy(f);
		current = original.copy(true).createObservable();
		history = new ArrayList<>();
	}

	public ObservablePicture getCurrent() {
		return current;
	}

	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	public void paintAt(int x, int y) {
		if (last_call + 150 < System.currentTimeMillis()) {
			history.add(createCopy(current.getPicture()));
			last_call = System.currentTimeMillis();
		}

		if (isCircle) {
			int half = brush_size / 2;
			current.paint(x - half, y - half, brush_size, current_pixel, curr_opacity);
		} else {
			current.paint(x - brush_size + 1, y - brush_size + 1, x + brush_size - 1, y + brush_size - 1, current_pixel, curr_opacity);
		}
	}

	public void blurAt(int x, int y) throws NoIntersectionException {
		if (last_call + 150 < System.currentTimeMillis()) {
			history.add(createCopy(current.getPicture()));
			last_call = System.currentTimeMillis();
		}

		current.blur(blur_factor, x, y, brush_size, curr_opacity, current_pixel);
	}


	public void fireReRender() throws IOException {
		Picture f = Picture.readFromURL(link_box.getText());
		current = createCopy(f);
		view.rerender(current);
	}

	public void setCurrent_pixel(Pixel current_pixel) {
		this.current_pixel = current_pixel;
	}

	public void setCurr_opacity(double curr_opacity) {
		this.curr_opacity = curr_opacity;
	}

	public void setBrush_size(int brush_size) {
		this.brush_size = brush_size;
	}

	public void setBlur_factor(int blur_factor) {
		this.blur_factor = blur_factor;
	}

	public void setLinkBox(JTextField input) {
		this.link_box = input;
	}

	public void setPrevious() {
		if (history.size() - 1 > -1) {
			history.remove(history.get(history.size() - 1));
		}

		if (history.size() > 0) {
			current = history.get(history.size() - 1);
		} else {
			current = createCopy(original);
		}

		view.rerender(current);
	}

	public void setCircle(boolean circle) {
		System.out.println(circle);
		isCircle = circle;
	}

	public void addView(ImageEditorView view) {
		this.view = view;
	}

	public ObservablePicture createCopy(Picture p) {
		Pixel[][] temp = new Pixel[p.getWidth()][p.getHeight()];
		int i;
		int j;
		for (i = 0; i < p.getWidth(); i++) {
			for (j = 0; j < p.getHeight(); j++) {
				temp[i][j] = p.getPixel(i, j);
			}
		}

		return new ObservablePictureImpl(new MutablePixelArrayPicture(temp, "history"));
	}
}
