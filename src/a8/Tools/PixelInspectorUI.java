package a8.Tools;

import a8.*;

import javax.swing.*;
import java.awt.*;

public class PixelInspectorUI extends JPanel {

	private JLabel x_label;
	private JLabel y_label;
	private JLabel pixel_info;
	private PictureView pixel_box;
	private JPanel stats_container;

	public PixelInspectorUI(ObservablePicture p) {
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		pixel_info = new JLabel("(r,g,b)");
		pixel_box = new PictureView(p);
		stats_container = new JPanel(new GridLayout());

		setLayout(new BorderLayout());
		stats_container.add(x_label);
		stats_container.add(y_label);
		stats_container.add(pixel_info);

		add(stats_container, BorderLayout.WEST);
		add(pixel_box, BorderLayout.EAST);
	}

	public PictureView getPixelBox() {
		return pixel_box;
	}

	public void setInfo(int x, int y, Pixel p) {
		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		pixel_info.setText(String.format("(%3.2f, %3.2f, %3.2f)", p.getRed(), p.getBlue(), p.getGreen()));		
	}
}
