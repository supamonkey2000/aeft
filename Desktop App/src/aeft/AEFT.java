package aeft;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class AEFT implements ActionListener{

	public ServerSocket server=null;
	public boolean server_up=false;
	public Socket socket=null;
	public int maxsize=999999999;
	public String file_name="file.txt";
	public int byteread,current;
	public JTextField fileTF;

	public static String getAddress(){
		try{return Inet4Address.getLocalHost().getHostAddress();}catch(Exception ex){return"ERROR: IP could not be resolved.";}
	}

	public void startServer(){
		String start_fail="ERROR: Failed to start server";
		if(!server_up){
			try{
				server=new ServerSocket(25000);
				server_up=true;
				System.out.println("INFO: Server started successfully.");
				waitForFile();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null,"ERROR: Failed to start server.");
				System.out.println(start_fail+": Unknown error occured.");
			}
		}
		else{
			System.out.println(start_fail+": Server already running!");
		}
	}

	public String getRandom(){
		return(Double.toString(Math.random()*100)).replace(".","");
	}
	
	public void waitForFile(){
		int count;
		try{
			socket=server.accept();
			byte[]buffer=new byte[maxsize];
			file_name=fileTF.getText();
			String[]splitfile=file_name.split("\\.");
			File file=new File(splitfile[0]+"_"+getRandom()+"."+splitfile[1]);
			file.createNewFile();
			InputStream is=socket.getInputStream();
			FileOutputStream fos=new FileOutputStream(file);
			BufferedOutputStream out=new BufferedOutputStream(fos);
			@SuppressWarnings("unused")
			long total=0;
			while((count=is.read(buffer,0,buffer.length))!=-1){
				total+=count;
				out.write(buffer,0,count);
			}
			out.flush();
			fos.close();
			is.close();
			server.close();
			//Garbage collecting
			out=null;
			fos=null;
			is=null;
			server=null;
			buffer=null;
			file_name=null;
			file=null;
			System.gc();
		}catch(Exception ex){ex.printStackTrace();}
		
		server_up=false;
		startServer();
	}

	public void createAndShowGUI(){
		JFrame frame=new JFrame("Android Easy File Transfer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(320,240);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.NORTH;
		gbc.weightx=0.25;
		gbc.weighty=0.25;

		JLabel ip=new JLabel("Your local IP is: "+getAddress());
		gbc.gridx=0;gbc.gridy=0;
		frame.add(ip,gbc);

		fileTF=new JTextField("C:\\Users\\Josh\\Desktop\\fileingtxt");
		gbc.gridx=0;gbc.gridy=1;
		frame.add(fileTF,gbc);

		JButton startB=new JButton("Start Server");
		startB.addActionListener(this);
		gbc.gridx=1;gbc.gridy=1;
		frame.add(startB,gbc);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try{
					socket.close();
					System.out.println("INFO: Socket closed successfully.");
				}catch(Exception ex){System.out.println("ERROR: Failed to close socket");}
			}
		});
	}

	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()){
		case("Start Server"):
			startServer();
		}
	}

	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ex){ex.printStackTrace();}
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				AEFT gui=new AEFT();
				gui.createAndShowGUI();
			}
		});		
	}
}