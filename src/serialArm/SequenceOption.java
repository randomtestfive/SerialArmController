package serialArm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SequenceOption extends JPanel implements ActionListener 
{
	JComboBox<String> options;
	JButton add;
	JButton left;
	JButton right;
	JLabel label;
	String[] names;
	String[] lDirections;
	String[] rDirections;
	String[] lLabels;
	String[] rLabels;
	boolean[] verys;
	JCheckBox very;
	JPanel top;
	JPanel topIn;
	public static JPanel bottom;
	
	public SequenceOption(ButtonSet[] set) 
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		top = new JPanel();
		topIn = new JPanel();
		topIn.setLayout(new FlowLayout(FlowLayout.LEADING));
		top.add(topIn);
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.setAlignmentX(LEFT_ALIGNMENT);
		add(top);
		bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		bottom.setAlignmentX(LEFT_ALIGNMENT);
		add(bottom);
		names = new String[set.length];
		lDirections = new String[set.length];
		rDirections = new String[set.length];
		verys = new boolean[set.length];
		lLabels = new String[set.length];
		rLabels = new String[set.length];
		for(int i = 0; i < set.length; i++)
		{
			names[i] = set[i].lbl.getText();
			lDirections[i] = set[i].lAction;
			rDirections[i] = set[i].rAction;
			lLabels[i] = set[i].left.getText();
			rLabels[i] = set[i].right.getText();
			verys[i] = (set[i].veryLeft != null);
		}
		options = new JComboBox<String>(names);
		options.addActionListener(this);
		topIn.add(options);
		topIn.add(Box.createRigidArea(new Dimension(5,0)));
		left = new JButton(lLabels[0]);
		left.setPreferredSize(new Dimension(70, 30));
		left.addActionListener(this);
		left.setEnabled(false);
		right = new JButton(rLabels[0]);
		right.setPreferredSize(new Dimension(70, 30));
		right.addActionListener(this);
		topIn.add(left);
		topIn.add(Box.createRigidArea(new Dimension(5,0)));
		topIn.add(right);
		topIn.add(Box.createRigidArea(new Dimension(5,0)));
		System.out.println("yes");
		label = new JLabel("Very: ");
		topIn.add(label);
		topIn.add(Box.createRigidArea(new Dimension(5,0)));
		very = new JCheckBox();
		topIn.add(very);
		topIn.add(Box.createRigidArea(new Dimension(5,0)));
		add = new JButton("Add");
		add.addActionListener(this);
		topIn.add(add);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == options)
		{
			very.setEnabled(verys[options.getSelectedIndex()]);
			very.setSelected(verys[options.getSelectedIndex()]);
			left.setText(lLabels[options.getSelectedIndex()]);
			right.setText(rLabels[options.getSelectedIndex()]);
		}
		else if(arg0.getSource() == left)
		{
			right.setEnabled(true);
			left.setEnabled(false);
		}
		else if(arg0.getSource() == right)
		{
			left.setEnabled(true);
			right.setEnabled(false);
		}
		else if(arg0.getSource() == add)
		{
			System.out.println(names[options.getSelectedIndex()]);
			String dir;
			if(left.isEnabled())
			{
				dir = right.getText();
			}
			else
			{
				dir = left.getText();
			}
			bottom.add(new SequenceCommand(names[options.getSelectedIndex()], dir, very.isSelected()));
			SerialArm.sequencer.pack();
		}
	}

}
