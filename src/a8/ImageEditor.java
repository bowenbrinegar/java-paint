package a8;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageEditor {
	public static void main(String[] args) throws IOException, InterruptedException {
		Picture f = Picture.readFromURL("http://brinegarphotography.businesscatalyst.com/images/0q1a4748-crop-u22940.jpg?crc=4052602086");

		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Image Editor");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageEditorModel model = new ImageEditorModel(f);
		ImageEditorView view = new ImageEditorView(main_frame, model);

		model.addView(view);

 		ImageEditorController controller = new ImageEditorController(view, model);
		

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(view, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);
		main_frame.setSize(new Dimension(f.getWidth() + 400, f.getHeight()));
		main_frame.setPreferredSize(new Dimension(f.getWidth() + 400, f.getHeight()));

		main_frame.pack();
		main_frame.setVisible(true);
	}
}