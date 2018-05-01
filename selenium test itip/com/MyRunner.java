package network.ite.run.com;

import java.io.IOException;

public class MyRunner implements Runnable {
	public void run(){
		   try {
			   Process p = Runtime.getRuntime().exec("wscript C:\\Users\\c-automation\\Desktop\\RUNME\\GETSTATUS.vbs" );
			   p.waitFor();
		   }
		   catch( IOException | InterruptedException e ) {
		      System.out.println(e);
		      System.exit(0);
		   }
		}
	}