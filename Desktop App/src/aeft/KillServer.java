package aeft;
public class KillServer{
	public static void main(String[]args){
		try{
			java.lang.Runtime.getRuntime().exec("taskkill /f /im javaw.exe");
		}catch(Exception ex){
			System.err.println(ex);
		}
	}
}