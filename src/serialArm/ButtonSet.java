package serialArm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

@SuppressWarnings("serial")
public class ButtonSet extends JPanel implements ActionListener, SerialPortEventListener
{
	SerialPort s;
	JPanel top;
	JPanel bottom;
	JLabel lbl;
	JButton left;
	JButton right;
	String lAction;
	String rAction;
	
	public ButtonSet(String label, String leftButton, String rightButton, String leftAction, String rightAction, SerialPort serial) 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		top = new JPanel();
		top.setLayout(new FlowLayout());
		bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		add(top);
		add(bottom);
		lbl = new JLabel(label);
		top.add(lbl);
		left = new JButton(leftButton);
		left.addActionListener(this);
		right = new JButton(rightButton);
		right.addActionListener(this);
		bottom.add(left);
		bottom.add(right);
		s = serial;
		lAction = leftAction;
		rAction = rightAction;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		try {
			s.openPort();
			s.setParams(SerialPort.BAUDRATE_9600,
					SerialPort.DATABITS_8,
			        SerialPort.STOPBITS_1,
			        SerialPort.PARITY_NONE);
			s.addEventListener(this);
		} catch (SerialPortException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(e.getSource().equals(left))
		{
			
			try {
				s.writeString(lAction);
				System.out.println(s.readString());
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			try {
				s.writeString(rAction);
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			s.closePort();
		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public void serialEvent(SerialPortEvent arg0) 
	{
		try {
			System.out.println(s.readString(arg0.getEventValue()));
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
