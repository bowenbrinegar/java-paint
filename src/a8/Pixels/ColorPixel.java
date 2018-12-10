package a8.Pixels;

import a8.Pixel;

public class ColorPixel implements Pixel, AlphaPixel {

	double _red;
	double _green;
	double _blue;
	double _alpha;
	
	public ColorPixel(double r, double g, double b) {
		if (r < 0.0 || r > 1.0 || g < 0.0 || g > 1.0 || b < 0.0 || b > 1.0) {
			throw new IllegalArgumentException("Color component out of range");
		}
		
		_red = r;
		_green = g;
		_blue = b;
	}


	public ColorPixel(double r, double g, double b, double a) {
		if (r < 0.0 || r > 1.0 || g < 0.0 || g > 1.0 || b < 0.0 || b > 1.0 || a < 0.0 || a > 1.0) {
			throw new IllegalArgumentException("Color component out of range");
		}

		_red = r;
		_green = g;
		_blue = b;
		_alpha = a;
	}

	@Override
	public double getRed() {
		return _red;
	}

	@Override
	public double getGreen() {
		return _green;
	}

	@Override
	public double getBlue() {
		return _blue;
	}

	@Override
	public double getAlpha() { return _alpha; }
}
