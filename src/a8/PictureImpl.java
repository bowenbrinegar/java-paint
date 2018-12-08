package a8;

abstract public class PictureImpl implements Picture {

	private String _caption;
	
	protected PictureImpl(String caption) {
		if (caption == null) {
			throw new IllegalArgumentException("caption should not be null");
		}
		_caption = caption;
	}

	@Override
	public String getCaption() {
		return _caption;
	}

	@Override
	public void setCaption(String caption) {
		if (caption == null) {
			throw new IllegalArgumentException("caption is null");
		}
		
		_caption = caption;
	}

	public Picture paint(int x, int y, Pixel p) {
		return paint(x,y,p,1.0);
	}
	
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		return paint(ax, ay, bx, by, p, 1.0);
	}
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}
		
		if (factor < 0 || factor > 1.0) {
			throw new IllegalArgumentException("factor out of range");			
		}
		
		int min_x = (ax < bx) ? ax : bx;
		int max_x = (ax > bx) ? ax : bx;
		int min_y = (ay < by) ? ay : by;
		int max_y = (ay > by) ? ay : by;

		min_x = (min_x < 0) ? 0 : min_x;
		min_y = (min_y < 0) ? 0 : min_y;
		max_x = (max_x > getWidth()-1) ? getWidth()-1 : max_x;
		max_y = (max_y > getHeight()-1) ? getHeight()-1 : max_y;
		
		Picture result = this;
		for (int x=min_x; x <= max_x; x++) {
			for (int y=min_y; y<= max_y; y++) {
				result = result.paint(x,y,p,factor);
			}
		}
		return result;
	}

	public Picture paint(int cx, int cy, double radius, Pixel p) {
		return paint(cx, cy, radius, p, 1.0);
	}
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}
		
		if (factor < 0 || factor > 1.0) {
			throw new IllegalArgumentException("factor out of range");			
		}
		
		if (radius < 0) {
			throw new IllegalArgumentException("radius is negative");
		}

		int min_x = (int) (cx - (radius+1));
		int max_x = (int) (cx + (radius+1));
		int min_y = (int) (cy - (radius+1));
		int max_y = (int) (cy + (radius+1));

		min_x = (min_x < 0) ? 0 : min_x;
		min_y = (min_y < 0) ? 0 : min_y;
		max_x = (max_x > getWidth()-1) ? getWidth()-1 : max_x;
		max_y = (max_y > getHeight()-1) ? getHeight()-1 : max_y;
		
		Picture result = this;
		for (int x=min_x; x <= max_x; x++) {
			for (int y=min_y; y<= max_y; y++) {
				if (Math.sqrt((cx-x)*(cx-x)+(cy-y)*(cy-y)) <= radius) {
					result = result.paint(x,y,p,factor);
				}
			}
		}
		return result;		
	}

	
	public Picture paint(int x, int y, Picture p) {
		return paint(x,y,p,1.0);
	}
	public Picture paint(int x, int y, Picture p, double factor) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("x or y out of range");
		}

		if (factor < 0 || factor > 1.0) {
			throw new IllegalArgumentException("factor out of range");			
		}
		
		if (p == null) {
			throw new IllegalArgumentException("Picture p is null");
		}
		
		Picture result = this;
		for (int px=0; px < p.getWidth() && px + x < getWidth(); px++) {
			for (int py=0; py < p.getHeight() && py + y < getHeight(); py++) {
				result = result.paint(px+x, py+y, p.getPixel(px, py), factor);
			}
		}
		return result;
	}
	
	public SubPicture extract(int x, int y, int width, int height) {
		return new SubPictureImpl(this, x, y, width, height);
	}

	private static Pixel findAverage(SubPicture pic, int x, int y, Pixel average) {
		if (x + 3 < pic.getWidth()) {
			Pixel blend = average.blend(pic.getPixel(x + 3, y), .5);
			return findAverage(pic, x + 3, y, blend);
		} else if (y + 3 < pic.getHeight()) {
			Pixel blend = average.blend(pic.getPixel(x, y + 3), .5);
			return findAverage(pic, x, y + 3, blend);
		} else {
			return average;
		}
	}

	public Picture blur(int val, int _x, int _y, int radius, double blend_opacity, Pixel other) throws NoIntersectionException {
		Picture _blurred = this;

		int i;
		int j;
		for (i = _x; i < radius; i++) {
			for (j =  _y; j < radius; j++) {
				Pixel p = this.getPixel(i,j);
				int half = val / 2;
				int x = i - half > 0 ? i - half : 0;
				int y = j - half > 0 ? j - half : 0;
				int _delta_x = i + half < radius ? half : radius - i;
				int _delta_y = j + half < radius ? half : radius - j;
				_delta_x = _delta_x > 1 ? _delta_x : 1;
				_delta_y = _delta_y > 1 ? _delta_y : 1;

				SubPicture avgZone = new SubPictureImpl(this, x, y, _delta_x, _delta_y);

				p = findAverage(avgZone, 0, 0, p);

				_blurred.paint(i, j, p.blend(other, blend_opacity));
			}
		}

		return _blurred;
	}
}
