package a8.Tools;

import a8.*;
import a8.Pixels.ColorPixel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PaintBrushToolUI extends JPanel implements ChangeListener {

	private JSlider red_slider;
	private JSlider green_slider;
	private JSlider blue_slider;
	private JSlider opacity_slider;
	private JSlider blur_slider;
	private JSlider brush_size;
	private PictureView color_preview;
	private double opacity_factor;
	private int blur_factor;
	private int brush_size_val;
	private ImageEditorModel model;

	public double getOpacity_factor() {
		return opacity_factor;
	}

	public void setOpacity_factor(double opacity_factor) {
		this.opacity_factor = opacity_factor;
	}

	public int getBlur_factor() {
		return blur_factor;
	}

	public void setBlur_factor(int blur_factor) {
		this.blur_factor = blur_factor;
	}

	public int getBrush_size_val() {
		return brush_size_val;
	}

	public void setBrush_size_val(int brush_size_val) {
		this.brush_size_val = brush_size_val;
	}

	public PaintBrushToolUI(ImageEditorModel model) {
		this.model = model;
		setLayout(new GridLayout(0,1));
		
		JPanel color_chooser_panel = new JPanel();
		color_chooser_panel.setLayout(new BorderLayout());
		
		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(0,1));
		
		JPanel red_slider_panel = new JPanel();
		JLabel red_label = new JLabel("Red:");
		red_slider_panel.setLayout(new BorderLayout());
		red_slider_panel.add(red_label, BorderLayout.WEST);
		red_slider = new JSlider(0,100);
		red_slider.addChangeListener(this);
		red_slider_panel.add(red_slider, BorderLayout.CENTER);

		JPanel green_slider_panel = new JPanel();
		JLabel green_label = new JLabel("Green:");
		green_slider_panel.setLayout(new BorderLayout());
		green_slider_panel.add(green_label, BorderLayout.WEST);
		green_slider = new JSlider(0,100);
		green_slider.addChangeListener(this);
		green_slider_panel.add(green_slider, BorderLayout.CENTER);

		JPanel blue_slider_panel = new JPanel();
		JLabel blue_label = new JLabel("Blue: ");
		blue_slider_panel.setLayout(new BorderLayout());
		blue_slider_panel.add(blue_label, BorderLayout.WEST);
		blue_slider = new JSlider(0,100);
		blue_slider.addChangeListener(this);
		blue_slider_panel.add(blue_slider, BorderLayout.CENTER);

		JPanel size_slider_panel = new JPanel();
		JLabel size_label = new JLabel("Size: ");
		size_slider_panel.setLayout(new BorderLayout());
		size_slider_panel.add(size_label, BorderLayout.WEST);
		brush_size = new JSlider(0,100);
		brush_size.addChangeListener(this);
		size_slider_panel.add(brush_size, BorderLayout.CENTER);

		JPanel opacity_slider_panel = new JPanel();
		JLabel opacity_label = new JLabel("Opacity: ");
		opacity_slider_panel.setLayout(new BorderLayout());
		opacity_slider_panel.add(opacity_label, BorderLayout.WEST);
		opacity_slider = new JSlider(0,100);
		opacity_slider.addChangeListener(this);
		opacity_slider_panel.add(opacity_slider, BorderLayout.CENTER);

		JPanel blur_slider_panel = new JPanel();
		JLabel blur_label = new JLabel("Blur: ");
		blur_slider_panel.setLayout(new BorderLayout());
		blur_slider_panel.add(blur_label, BorderLayout.WEST);
		blur_slider = new JSlider(0,50);
		blur_slider.addChangeListener(this);
		blur_slider_panel.add(blur_slider, BorderLayout.CENTER);

		// Assumes green label is widest and asks red and blue label
		// to be the same.
		Dimension d = opacity_label.getPreferredSize();
		green_label.setPreferredSize(d);
		red_label.setPreferredSize(d);
		blue_label.setPreferredSize(d);
		size_label.setPreferredSize(d);
		opacity_label.setPreferredSize(d);
		blur_label.setPreferredSize(d);

		slider_panel.add(red_slider_panel);
		slider_panel.add(green_slider_panel);
		slider_panel.add(blue_slider_panel);
		slider_panel.add(size_slider_panel);
		slider_panel.add(opacity_slider_panel);
		slider_panel.add(blur_slider_panel);

		color_chooser_panel.add(slider_panel, BorderLayout.CENTER);

		color_preview = new PictureView(Picture.createSolidPicture(50, 50, Pixel.WHITE, true).createObservable());
		color_chooser_panel.add(color_preview, BorderLayout.EAST);

		add(color_chooser_panel);
		stateChanged(null);
		blur_slider.setValue(0);
		brush_size.setValue(5);
		opacity_slider.setValue(100);

		setBlur_factor(blur_slider.getValue());
		setBrush_size_val(brush_size.getValue());
		model.setCurr_opacity(opacity_slider.getValue() / 100.0);
		model.setCurrent_pixel(Pixel.WHITE);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Pixel p = new ColorPixel(red_slider.getValue()/100.0,
				                 green_slider.getValue()/100.0,
				                 blue_slider.getValue()/100.0);

		setBrush_size_val(brush_size.getValue());
		setBlur_factor(blur_slider.getValue());
		setOpacity_factor(opacity_slider.getValue() / 100.0);

		model.setBlur_factor(blur_factor);
		model.setBrush_size(brush_size_val);
		model.setCurr_opacity(opacity_factor);
		model.setCurrent_pixel(p);

		ObservablePicture preview_frame = color_preview.getPicture();
		preview_frame.paint(0, 0, preview_frame.getWidth()-1, preview_frame.getHeight()-1, p, getOpacityFactor());
	}
	
	public Pixel getBrushColor() {
		return color_preview.getPicture().getPixel(0,0);
	}

	public double getOpacityFactor() {
		return opacity_factor;
	}

	public JSlider getRedSlider() {
		return red_slider;
	}

	public JSlider getGreenSlider() {
		return green_slider;
	}

	public JSlider getBlueSlider() {
		return blue_slider;
	}
}
