package ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.InvalidDirectoryException;
import exceptions.InvalidPathException;
import exceptions.InvalidPortException;
import server.*;
import server.HTTPServer;
import server.ServerWrapper;

public class ServerGUI implements Runnable{
	
	JButton setRoot, setMaintenance, apply;
	JFileChooser fc1, fc2;
	JFrame frame;
	JTextField root, maintenance, port;
	JLabel portCheck, rootCheck, maintenanceCheck;
	BufferedImage passPic, failPic;
	Thread t1, t2;
	
	public void readImages() {
		try {
			passPic = ImageIO.read(new File(".\\src\\check.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			failPic = ImageIO.read(new File(".\\src\\remove.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enableConfigPanel(boolean enable) {
		port.setEditable(enable);
		root.setEditable(enable);
		maintenance.setEditable(enable);
		setRoot.setEnabled(enable);
		setMaintenance.setEnabled(enable);
		apply.setEnabled(enable);
	}
	
	
	public void run() {  
		readImages();
		HTTPServer.initConfig();
		
		StateDriver stateDriver = new StateDriver();
		
		frame = new JFrame("SVV WEB SERVER");       
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel1 = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());
		JPanel panel3 = new JPanel(new GridBagLayout());
		
		panel1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("WebServer info"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		panel1.setPreferredSize(new Dimension(320, 140));
		panel2.setPreferredSize(new Dimension(320, 140));
		
		panel2.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("WebServer control"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
		
		panel3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("WebServer configuration"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		frame.getContentPane();
		      
		JLabel statusLabel = new JLabel("Server status: ");
		JLabel addressLabel = new JLabel("Server address: ");
		JLabel portLabel = new JLabel("Server listening on port: ");
		JButton state = new JButton("Start server");
		JCheckBox checkBox1 = new JCheckBox("Switch to maintenance mode");
		JLabel setPort = new JLabel("Server listening on port: ");
		JLabel setRootLabel = new JLabel("Web root directory: ");
		JLabel setMaintenanceLabel = new JLabel("Maintenance directory: ");
		
		
		portCheck = new JLabel("");
		rootCheck = new JLabel("");
		maintenanceCheck = new JLabel("");
		
		JLabel status = new JLabel(stateDriver.getState().toString());
		JLabel address = new JLabel(stateDriver.getState().toString());
		JLabel runningPort = new JLabel(stateDriver.getState().toString());
		
		checkBox1.setEnabled(false);
		checkBox1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkBox1.isSelected())
				{
					stateDriver.setMaintenance();;
					enableConfigPanel(true);
					status.setText(stateDriver.getState().toString());
				}
				else
				{
					if(HTTPServer.isRunning())
						stateDriver.setRunning();
					else
						stateDriver.setStopped();
					
					enableConfigPanel(false);
					status.setText(stateDriver.getState().toString());
				}
				
			}
			
		});
		
		port = new JTextField(10);
		root = new JTextField(25);
		maintenance = new JTextField(25);
		
		fc1 = new JFileChooser();
		fc2 = new JFileChooser();
		
		fc1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		setRoot = new JButton("Browse");
		setMaintenance = new JButton("Browse");
		apply = new JButton("Apply");
		
		port.setText(HTTPServer.get("port"));
		root.setText(HTTPServer.get("rootDirectory"));
		maintenance.setText(HTTPServer.get("maintenanceDirectory"));
		
		
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					HTTPServer.set("port", port.getText());
					//portCheck = new JLabel(new ImageIcon(passPic));
					portCheck.setIcon(new ImageIcon(passPic));
					runningPort.setText(HTTPServer.get("port"));
				} catch (NumberFormatException | InvalidPortException | InvalidDirectoryException
						| InvalidPathException e1) {
					portCheck.setIcon(new ImageIcon(failPic));
				}
				try {
					HTTPServer.set("rootDirectory", root.getText());
					rootCheck.setIcon(new ImageIcon(passPic));
				} catch (NumberFormatException | InvalidPortException | InvalidDirectoryException
						| InvalidPathException e1) {
					// TODO Auto-generated catch block
					rootCheck.setIcon(new ImageIcon(failPic));
				}
				try {
					HTTPServer.set("maintenanceDirectory", maintenance.getText());
					maintenanceCheck.setIcon(new ImageIcon(passPic));
				} catch (NumberFormatException | InvalidPortException | InvalidDirectoryException
						| InvalidPathException e1) {
					// TODO Auto-generated catch block
					maintenanceCheck.setIcon(new ImageIcon(failPic));
				}
				HTTPServer.terminate();
				t1.interrupt();
				try {
					t1.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				stateDriver.setMaintenance();
				ServerWrapper sw = new ServerWrapper(stateDriver);
				t2 = new Thread(sw);
				HTTPServer.start();
				t2.start();
			}
		});
		
		enableConfigPanel(false);
		
		setRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
	            int returnVal = fc1.showOpenDialog(frame);
	 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc1.getSelectedFile();
	                root.setText(file.getAbsolutePath());
	            } else {
	                
	            }
		 
		    }
		});
		
		setMaintenance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    
	            int returnVal = fc2.showSaveDialog(frame);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc2.getSelectedFile();
	                maintenance.setText(file.getAbsolutePath());
	            } else {
	                
	            }
		      
		    }
		});
		
		state.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!HTTPServer.isRunning())
				{
					stateDriver.setRunning();
					ServerWrapper sw = new ServerWrapper(stateDriver);
					t1 = new Thread(sw);
					HTTPServer.start();
					t1.start();
					status.setText(stateDriver.getState().toString());
					InetAddress localIP = null;
					try {
						localIP=InetAddress.getLocalHost();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					address.setText(localIP.getHostAddress());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					runningPort.setText(HTTPServer.get("port"));
					checkBox1.setEnabled(true);
					state.setText("Stop server");
				}
				else if(HTTPServer.isRunning())
				{
					HTTPServer.terminate();
					stateDriver.setStopped();
					status.setText(stateDriver.getState().toString());
					address.setText(stateDriver.getState().toString());
					runningPort.setText(stateDriver.getState().toString());
					state.setText("Start server");
					enableConfigPanel(false);
					checkBox1.setSelected(false);
					checkBox1.setEnabled(false);
				}
			}
		});
		
		port.addKeyListener((KeyListener) new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyChar() == 8)
				{
					port.setEditable(true);
				}
				else
				{
					port.setEditable(false);
				}
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		 
		panel1.add(statusLabel, c);
		panel2.add(state, c);
		panel3.add(setPort, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		panel1.add(addressLabel, c);
		panel2.add(checkBox1, c);
		panel3.add(setRootLabel, c);
		
		c.gridy = 2;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		panel1.add(portLabel, c);
		panel3.add(setMaintenanceLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		
		panel3.add(port, c);
		panel1.add(status, c);
		
		c.gridy = 1;
		
		panel3.add(root, c);
		panel1.add(address, c);
		
		c.gridy = 2;
		
		panel3.add(maintenance, c);
		panel1.add(runningPort, c);
		
		c.gridx = 2;
		c.gridy = 1;
		
		panel3.add(setRoot, c);
		
		c.gridy = 2;
		
		panel3.add(setMaintenance, c);
		
		c.gridy = 3;
		c.gridx = 1;
		
		panel3.add(apply, c);
		
		c.gridx = 3;
		c.gridy = 0;
		
		panel3.add(portCheck, c);
		c.gridy = 1;
		panel3.add(rootCheck, c);
		c.gridy = 2;
		panel3.add(maintenanceCheck, c);
		
		frame.add(panel1, BorderLayout.WEST);
		frame.add(panel2, BorderLayout.EAST);
		frame.add(panel3, BorderLayout.PAGE_END);
		
		//Display the window.       
		frame.setLocationRelativeTo(null);       
		//frame.pack(); 
		frame.setSize(new Dimension(700, 300));
		frame.setVisible(true);    
	}    
	
	public void actionPerformed(ActionEvent e) {
		 
        //Handle open button action.
        if (e.getSource() == setRoot) {
            int returnVal = fc1.showOpenDialog(frame);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc1.getSelectedFile();
                //This is where a real application would open the file.
                root.setText(file.getAbsolutePath());
            } else {
                
            }
 
        //Handle save button action.
        } else if (e.getSource() == setMaintenance) {
            int returnVal = fc2.showSaveDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc2.getSelectedFile();
                //This is where a real application would save the file.
                maintenance.setText(file.getAbsolutePath());
            } else {
                
            }
        }
    }
	
	public static void main(String[] args) {
		ServerGUI gui = new ServerGUI();
		Thread t = new Thread(gui);
		t.start();
	}

}
