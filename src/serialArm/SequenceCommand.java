package serialArm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
		setAlignmentX(JPanel.LEFT_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		//setPreferredSize(new Dimension(300, 40));
		n = name;
		d = direction;
		v = very;
		x = new JButton("X");
		x.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		add(x);
		add(Box.createRigidArea(new Dimension(2,0)));
		up = new JButton("▲");
		up.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		add(up);
		add(Box.createRigidArea(new Dimension(2,0)));
		down = new JButton("▼");
		down.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		add(down);
		add(Box.createRigidArea(new Dimension(5,0)));
		label = new JLabel("Motor: " + name);
		label.setForeground(Color.black);
		label.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		add(label);
		add(Box.createRigidArea(new Dimension(5,0)));
		directionLabel = new JLabel("Direction: " + direction);
		directionLabel.setForeground(Color.blue);
		directionLabel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		add(directionLabel);
		add(Box.createRigidArea(new Dimension(5,0)));
		veryLabel = new JLabel("Very: " + very);
		veryLabel.setForeground(Color.red);
		veryLabel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		add(veryLabel);
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
