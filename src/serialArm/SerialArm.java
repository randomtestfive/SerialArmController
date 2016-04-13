package serialArm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialArm implements ActionListener, SerialPortEventListener
{
	public static SerialPort serial;
	public static JFrame frame;
	public static JFrame sequencer;
	public static JComboBox<String> comSelect;
	public static JButton goButton;
	public static JLabel label;
	public static ButtonSet base;
	public static ButtonSet shoulder;
	public static ButtonSet elbow;
	public static ButtonSet wrist;
	public static ButtonSet claw;
	
	public static void main(String[] args) throws Exception 
	{
		SerialArm a = new SerialArm();
		frame = new JFrame();
		frame.setSize(350, 350);
		frame.setTitle("Arm Controller");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		base = new ButtonSet("Base", "Left", "Right", "<<", ">>", "o", "p");
		shoulder = new ButtonSet("Shoulder", "Up", "Down", "<<", ">>", "q", "w");
		elbow = new ButtonSet("Elbow", "Up", "Down", "<<", ">>", "e", "r");
		wrist = new ButtonSet("Wrist", "CCW", "CC", "u", "i");
		claw = new ButtonSet("Claw", "Open", "Close", "y", "t");
		sequencer = new JFrame("Sequencer");
		sequencer.setLocation(500, 50);
		ButtonSet[] set = {base, shoulder, elbow, wrist, claw};
		SequenceOption option =  new SequenceOption(set);
		sequencer.add(option);
		sequencer.setSize(200,100);
		sequencer.getContentPane().setLayout(new BoxLayout(sequencer.getContentPane(), BoxLayout.Y_AXIS));
		sequencer.setVisible(true);
		sequencer.pack();

		String[] portNames = SerialPortList.getPortNames();
        
		if (portNames.length == 0) 
		{
		    label = new JLabel("Could not find any COM ports.");
		    frame.add(label);
		    goButton = new JButton("OK");
		    goButton.addActionListener(new ActionListener() 
		    {	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					System.exit(0);
				}
			});
		    frame.add(goButton);
		    frame.setVisible(true);
		    while(true);
		}

		for (int i = 0; i < portNames.length; i++){
		    System.out.println(portNames[i]);
		}
		label = new JLabel("Select Arduino COM port: ");
		frame.add(label);
		comSelect = new JComboBox<>(portNames);
		comSelect.setMaximumSize(new Dimension(70, 30));
		frame.add(comSelect);
		goButton = new JButton("Go");
		goButton.addActionListener(a);
		frame.add(goButton);
		frame.setLocation(200, 50);
		frame.setVisible(true);

		//frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(goButton))
		{
			if(serial == null)
			{
				System.out.println("selected " + comSelect.getSelectedItem());
				serial = new SerialPort((String)comSelect.getSelectedItem());
				try {
					serial.openPort();
					serial.setParams(SerialPort.BAUDRATE_9600,
	                        SerialPort.DATABITS_8,
	                        SerialPort.STOPBITS_1,
	                        SerialPort.PARITY_NONE);
					serial.addEventListener(this);
					serial.writeString("asdf");
					//serial.closePort();
					frame.getContentPane().removeAll();
					frame.revalidate();
					frame.repaint();
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
					frame.add(base);
					frame.add(shoulder);
					frame.add(elbow);
					frame.add(wrist);
					frame.add(claw);
					frame.pack();
					frame.validate();
				} catch (SerialPortException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				try {
					serial.writeString("go");
				} catch (SerialPortException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) 
	{
		try {
			System.out.print(serial.readString(arg0.getEventValue()));
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}