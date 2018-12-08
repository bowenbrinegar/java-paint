package a8.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ToolChooserWidget extends JPanel implements ItemListener {

	private JComboBox tool_choices;
	private List<ToolChoiceListener> listeners;

	private static final String[] choices = {"Pixel Inspector", "Paint Brush", "Color Picker"};

	public ToolChooserWidget() {
		setLayout(new BorderLayout());
		add(new JLabel("Tool: "), BorderLayout.WEST);

		tool_choices = new JComboBox(choices);
		add(tool_choices, BorderLayout.CENTER);
		tool_choices.addItemListener(this);

		listeners = new ArrayList<ToolChoiceListener>();
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		listeners.add(l);
	}

	@Override

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) { 
			String tool_name = (String) e.getItem();

			for (ToolChoiceListener l : listeners) {
				l.toolChosen(tool_name);
			}
		}
	}

	public String getCurrentToolName() {
		return (String) tool_choices.getSelectedItem();
	}
}
