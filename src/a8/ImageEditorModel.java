package a8;

public class ImageEditorModel {

	private Picture original;
	private ObservablePicture current;
	
	public ImageEditorModel(Picture f) {
		original = f;
		current = original.copy(true).createObservable();
	}

	public ObservablePicture getCurrent() {
		return current;
	}

	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	public void paintAt(int x, int y, Pixel brushColor, int brush_size) {
		current.paint(x-brush_size+1, y-brush_size+1, x+brush_size-1, y+brush_size-1, brushColor);
	}
}
