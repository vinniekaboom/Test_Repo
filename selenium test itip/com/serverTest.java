package network.ite.run.com;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class serverTest {
	;

	@Before
	public void setUp() throws Exception {		
		System.out.println("\n...Running Server test tests.\n\n");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Completed..., See C:\\Users\\c-automation\\Desktop\\RUNME results spreadsheet for status!\n");
	}

	@Test
	public void test() throws InterruptedException {
		   try {
			   Process p = Runtime.getRuntime().exec("wscript C:\\Users\\c-automation\\Desktop\\RUNME\\GETSTATUS.vbs" );
			   p.waitFor();
		   }
		   catch( IOException e ) {
		      System.out.println(e);
		      System.exit(0);
		   }
		}
	}