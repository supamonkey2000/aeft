package aeft;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class Progress {

	JProgressBar progressBar;

	public void createAndShowGUI(){
		JFrame frame=new JFrame("Upload Progress");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.setLayout(new FlowLayout());

		progressBar=new JProgressBar(0,100);
		frame.add(progressBar);

		frame.setVisible(true);
	}

	public void updateBar(int newValue){
		progressBar.setValue(newValue);
	}

	public static void main(String[]args) {
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ex){ex.printStackTrace();}
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				Progress gui=new Progress();
				gui.createAndShowGUI();
			}
		});
	}
}