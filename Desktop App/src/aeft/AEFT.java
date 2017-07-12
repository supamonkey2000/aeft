package aeft;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class AEFT implements ActionListener {

	public ServerSocket server = null;
	public boolean server_up = false;
	public Socket socket = null;
	public int maxsize = 36700160;
	public String file_name = "file.txt";
	public int byteread, current;
	public JTextField fileTF;

	public static String get_address() {
		try{return Inet4Address.getLocalHost().getHostAddress();}catch(Exception ex){return "ERROR: IP could not be resolved.";}
	}

	public void start_server() {
		String start_fail = "ERROR: Failed to start server.";
		//JOptionPane.showMessageDialog(null, "ERROR: Failed to start server.");
		if(!server_up) {
			try {
				server = new ServerSocket(25000);
				server_up = true;
				JOptionPane.showMessageDialog(null, "Server started successfully.");
				System.out.println("INFO: Server started successfully.");
				wait_for_file();
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "ERROR: Failed to start server.");
				System.out.println(start_fail + ": Unknown error occured.");
			}
		}
		else {
			System.out.println(start_fail + ": Server already running!");
		}
	}
	
	public void wait_for_file() throws IOException {
		socket = server.accept();
		System.out.println("INFO: Accepted connection.");
		byte[] buffer = new byte[maxsize];
		InputStream is = socket.getInputStream(); System.out.println("INFO: Getting InputStream.");
		file_name = fileTF.getText();
		File file = new File(file_name);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream out = new BufferedOutputStream(fos);
		byteread = is.read(buffer, 0, buffer.length);
		current = byteread;
		do {
			byteread = is.read(buffer, 0, buffer.length - current);
			if(byteread >= 0) current += byteread;
		} while (byteread > -1);
		out.write(buffer, 0, current);
		out.flush();
		fos.close();
		is.close();
		System.exit(0);
	}
	
	
	public void createAndShowGUI() {
		JFrame frame = new JFrame("Android Easy File Transfer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(320, 240);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NORTH;
		gbc.weightx = 0.25;
		gbc.weighty = 0.25;


		JLabel ip = new JLabel("Your local IP is: " + get_address());
		gbc.gridx = 0;gbc.gridy = 0;
		frame.add(ip,gbc);

		fileTF = new JTextField("<path and file name to save>");
		gbc.gridx = 0;gbc.gridy = 1;
		frame.add(fileTF, gbc);

		JButton startB = new JButton("Start Server");
		startB.addActionListener(this);
		gbc.gridx = 1;gbc.gridy = 1;
		frame.add(startB, gbc);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					socket.close();
					System.out.println("INFO: Socket closed successfully.");
				}catch(Exception ex){System.out.println("ERROR: Failed to close socket");}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case("Start Server"):
			start_server();
		}
	}

	public static void main(String args[]) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ex) { ex.printStackTrace();}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AEFT gui = new AEFT();
				gui.createAndShowGUI();
			}
		});		
	}
}
