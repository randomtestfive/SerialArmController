package serialArm;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	public static JPanel bottom;
	
	public SequenceOption(ButtonSet[] set) 
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		top = new JPanel();
		add(top);
		bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
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
		top.add(options);
		left = new JButton(lLabels[0]);
		left.setPreferredSize(new Dimension(70, 30));
		left.addActionListener(this);
		left.setEnabled(false);
		right = new JButton(rLabels[0]);
		right.setPreferredSize(new Dimension(70, 30));
		right.addActionListener(this);
		top.add(left);
		top.add(right);
		System.out.println("yes");
		label = new JLabel("Very: ");
		top.add(label);
		very = new JCheckBox();
		top.add(very);
		add = new JButton("Add");
		add.addActionListener(this);
		top.add(add);
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
