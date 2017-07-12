package aeft;

import java.io.*;
import java.net.*;

public class ClientTest {
	
	public static OutputStream out = null;
	public static Socket socket = null;
	public static File myFile = new File("C:\\Users\\Josh\\Desktop\\ctas.txt");
	public static byte[] buffer = new byte[(int) myFile.length()];

	public static void main(String[] args) throws IOException{
		socket = new Socket("192.168.1.101",25000);
		FileInputStream fis = new FileInputStream(myFile);
		BufferedInputStream in = new BufferedInputStream(fis);
		in.read(buffer, 0, buffer.length);
		out = socket.getOutputStream();
		System.out.println("Sending files");
		out.write(buffer, 0, buffer.length);
		out.flush();
		out.close();
		in.close();
		socket.close();
		System.out.println("COMPLETED");
	}
}
