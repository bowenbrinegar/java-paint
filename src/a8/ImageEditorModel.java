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
	private boolean isCircle = true;
	private List<ObservablePicture> history;
	private ImageEditorView view;
	private long last_call = System.currentTimeMillis();
	private JTextField link_box;
	private boolean isBlur = false;
	private Coordinate start_coordinate = new Coordinate(0,0);
	private Coordinate end_coordinate;
	private Coordinate main_coordinate;
	private boolean selecting = false;
	private boolean expanding = false;
	private int w;
	private int h;
	private Picture clipboard;
	private boolean clipboard_not_null;
	private boolean has_selection = false;

	public boolean getCurrentlySelecting() {
		return selecting;
	}

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


	public boolean getHasSelection() {
		return has_selection;
	}

	public void setHasSelection(boolean b) {
		has_selection = b;
	}

	public void paintAt(int x, int y) {
		if (isBlur) {
			return;
		}

		if (last_call + 150 < System.currentTimeMillis()) {
			history.add(createCopy(current.getPicture()));
			last_call = System.currentTimeMillis();
		}

		if (isCircle) {
			int half = brush_size / 2;
			try {
				current.paint(x - half, y - half, brush_size, current_pixel, curr_opacity);
			} catch (IllegalArgumentException e) {
			}
		} else {
			try {
				current.paint(x - brush_size + 1, y - brush_size + 1, x + brush_size - 1, y + brush_size - 1, current_pixel, curr_opacity);
			} catch (IllegalArgumentException e) {
			}
		}
	}

	public void blurAt(int x, int y) throws NoIntersectionException {
		if (last_call + 150 < System.currentTimeMillis()) {
			history.add(createCopy(current.getPicture()));
			last_call = System.currentTimeMillis();
		}

		if (isCircle) {
			int half = brush_size / 2;
			try {
				current.blur(x - half, y - half, brush_size, current_pixel, curr_opacity, blur_factor);
			} catch (IllegalArgumentException e) {
			}
		} else {
			try {
				current.blur(x - brush_size + 1, y - brush_size + 1, x + brush_size - 1, y + brush_size - 1, current_pixel, curr_opacity, blur_factor);
			} catch (IllegalArgumentException e) {
			}
		}
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
		if (blur_factor > 0) {
			this.isBlur = true;
		}
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

	public void setStartCoordinate(Coordinate c) {
		if (selecting) {
			this.start_coordinate = c;
		}
	}

	public void setEndCoordinate(Coordinate c) {
		if (last_call + 150 < System.currentTimeMillis()) {
			this.end_coordinate = c;

			int w;
			int h;

			w = Math.abs(start_coordinate.getX() - end_coordinate.getX());
			h = Math.abs(start_coordinate.getY() - end_coordinate.getY());

			int x;
			int y;

			if (start_coordinate.getX() > end_coordinate.getX()) {
				x = end_coordinate.getX();
			} else {
				x = start_coordinate.getX();
			}

			if (start_coordinate.getY() > end_coordinate.getY()) {
				y = end_coordinate.getY();
			} else {
				y = start_coordinate.getY();
			}

			main_coordinate = new Coordinate(x,y);

			view.updateRect(new Coordinate(x,y), w, h);

			last_call = System.currentTimeMillis();
			this.has_selection = true;
		}
	}

	public void setCurrentlySelecting(boolean selecting) {
		this.selecting = selecting;
	}

	public void replaceRect() {
		if (!selecting) {
			view.removeRect();
            view.addRect();
		}
	}

	public void createSubPicCopy() {
		if (this.clipboard == null) {
			int source_w = current.getWidth();
			int source_h = current.getHeight();

			int x = main_coordinate.getX();
			int y = main_coordinate.getY();

			int _w = x + w > source_w ? source_w - x : w;
			int _h = y + h > source_h ? source_h - y : h;

			SubPicture sub = current.extract(x, y, _w, _h);
			this.clipboard = createCopy(sub);
			this.clipboard_not_null = true;
		}
	}

	public void setMainCoordinate(Coordinate c) {
		main_coordinate = c;
	}

	public Coordinate getCoordinate() {
		return main_coordinate;
	}

	public void setExpanding(boolean expanding) {
		this.expanding = expanding;
	}


	public void setWidthHeight(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	private ObservablePicture createCopy(Picture p) {
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

	public void pasteIfPossible(int x, int y) {
		if (this.clipboard != null) {
			history.add(createCopy(current.getPicture()));

			int half_w = this.clipboard.getWidth() / 2;
			int half_h = this.clipboard.getHeight() / 2;

			int min_x = x - half_w > 0 ? x - half_w : 0;
			int min_y = y - half_h > 0 ? y - half_h : 0;
			current.paint(min_x, min_y, this.clipboard);
			this.clipboard = null;
			this.clipboard_not_null = false;
			this.has_selection = false;
		}
	}


	public boolean getClipboardFull() {
		return clipboard_not_null;
	}

	private double dist(double ax, double ay, double bx, double by) {
		double x = Math.pow((ax - bx), 2);
		double y = Math.pow((ay - by), 2);
		double d = Math.sqrt(x + y);
		return d;
	}
}
