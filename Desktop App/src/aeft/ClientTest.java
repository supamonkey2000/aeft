package aeft;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClientTest implements ActionListener{
	
	JFrame frame;
	JTextField ipTF;
	
	public void createAndShowGUI(){
		frame=new JFrame("Android Easy File Transfer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(320,240);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.NORTH;
		gbc.weightx=0.25;
		gbc.weighty=0.25;

		ipTF=new JTextField();
		ipTF.setText("255.255.255.255");
		ipTF.setSize(40, 5);
		gbc.gridx=0;gbc.gridy=0;
		frame.add(ipTF);
		
		JButton startB=new JButton("Send File");
		startB.addActionListener(this);
		gbc.gridx=0;gbc.gridy=1;
		frame.add(startB,gbc);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//NOTHING
			}
		});
	}
	
	public void chooseFile(){
		JFileChooser chooser=new JFileChooser();
		int returnvalue=chooser.showOpenDialog(frame);
		if(returnvalue==JFileChooser.APPROVE_OPTION){
            File file=chooser.getSelectedFile();
            System.out.println("Opening: " + file.getName() + ".");
            int result=sendFile(file);
        }else{
            System.out.println("Open command cancelled by user.");
        }
	}
	
	public int sendFile(File file){
		String IP=ipTF.getText().toString();
		int count;
		try{
			long total=0;
			Socket socket=new Socket();
			socket.connect(new InetSocketAddress(IP,25000),5000);
			DataOutputStream nameos=new DataOutputStream(socket.getOutputStream());
			nameos.writeUTF(file.getName());
			nameos.flush();
			byte[]buffer=new byte[(int)file.length()/100];
			FileInputStream fis=new FileInputStream(file);
			BufferedInputStream in=new BufferedInputStream(fis);
			OutputStream out=socket.getOutputStream();
			while((count=in.read(buffer,0,buffer.length))!=-1){
				total+=count;
				out.write(buffer,0,buffer.length);
				System.out.println(total);
			}
			out.flush();
			out.close();
			nameos.close();
			in.close();
			socket.close();
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}

	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()){
		case("Send File"):
			chooseFile();
		}
	}
	
	public static void main(String[]args){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ex){ex.printStackTrace();}
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				ClientTest gui=new ClientTest();
				gui.createAndShowGUI();
			}
		});
	}
}