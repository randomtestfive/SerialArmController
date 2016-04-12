package serialArm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SequenceCommand extends JPanel implements ActionListener 
{
	JLabel label;
	JLabel veryLabel;
	JLabel directionLabel;
	JButton x;
	JButton up;
	JButton down;
	String n;
	String d;
	boolean v;

	public SequenceCommand(String name, String direction, boolean very) 
	{
		super();
		n = name;
		d = direction;
		v = very;
		label = new JLabel("Motor: " + name);
		label.setForeground(Color.black);
		add(label);
		directionLabel = new JLabel("Direction: " + direction);
		directionLabel.setForeground(Color.blue);
		add(directionLabel);
		veryLabel = new JLabel("Very: " + very);
		veryLabel.setForeground(Color.red);
		add(veryLabel);
		up = new JButton("▲");
		add(up);
		down = new JButton("▼");
		add(down);
		x = new JButton("X");
		add(x);
		x.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		SequenceOption.bottom.remove(this);
		SequenceOption.bottom.revalidate();
		SequenceOption.bottom.repaint();
		SerialArm.sequencer.pack();
	}

}
