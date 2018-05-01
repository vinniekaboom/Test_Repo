package network.ite.run.com;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TestDateTime {
public void getdatetime(){
	Date date = new Date();
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
	dateFormat.setTimeZone(TimeZone.getTimeZone("EST")); 
	System.out.println("");
	System.out.println(dateFormat.format(date));
	}
}
