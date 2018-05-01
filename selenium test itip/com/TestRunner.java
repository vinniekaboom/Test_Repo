package network.ite.run.com;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	
   private static final Result NULL = null;

public static void main(String[] args) {
       System.out.println("...\n");
	   if (args.length > 0){
		   for(int i =0;i<args.length;i++){
			   Result result = null;
			   switch(args[i]){			   
				   case "-all":
					   MyRunner myRunner = new MyRunner(); 
					   Thread myThread = new Thread(myRunner);
					   myThread.start();
					   result = JUnitCore.runClasses(JunitTestSuite.class);
					   break;		   
				   case "-aim":
					   result = JUnitCore.runClasses(aimTest.class);
					   break;   
				   case "-altiris":
					   result = JUnitCore.runClasses(altirisTest.class);
					   break;
				   case "-bft":
					   result = JUnitCore.runClasses(bftTest.class);
					   break;			   		   
				   case "-cims":
					   result = JUnitCore.runClasses(cimsTest.class);
					   break; 
				   case "-cms":
					   result = JUnitCore.runClasses(cmsTest.class);
					   break;
				   case "-cognos":
					   result = JUnitCore.runClasses(cognosTest.class);
					   break;					   
				   case "-cws":
					   result = JUnitCore.runClasses(cwsTest.class);
					   break; 
				   case "-edb":
					   result = JUnitCore.runClasses(edbTest.class);
					   break;
				   case "-eri":
					   result = JUnitCore.runClasses(osciseriTest.class);
					   break;			   
				   case "-esm":
					   result = JUnitCore.runClasses(esmTest.class);
					   break; 
				   case "-fas":
					   result = JUnitCore.runClasses(fasTest.class);
					   break;
				   case "-grads":
					   result = JUnitCore.runClasses(gradsTest.class);
					   break;			   
				   case "-iacms":
					   result = JUnitCore.runClasses(iacmsTest.class);
					   break;
				   case "-paygov":
					   result = JUnitCore.runClasses(paygovTest.class);
					   break;
				   case "-ideafactory":
					   result = JUnitCore.runClasses(ideafactoryTest.class);
					   break;	   
				   case "-ims":
					   result = JUnitCore.runClasses(imsTest.class);
					   break;
				   case "-ishare":
					   result = JUnitCore.runClasses(iShareTest.class);
					   break;			   
				   case "-ksms":
					   result = JUnitCore.runClasses(ksmsTest.class);
					   break;					   
				   case "-leda":
					   result = JUnitCore.runClasses(ledaTest.class);
					   break;			   
				   case "-links":
					   result = JUnitCore.runClasses(linksTest.class);
					   break;
				   case "-wintel":
					   result = JUnitCore.runClasses(MOPTest.class);
					   break;
				   case "-mytsa":
					   result = JUnitCore.runClasses(mytsaTest.class);
					   break;			   
				   case "-nfl":
					   result = JUnitCore.runClasses(nflTest.class);
					   break;
				   case "-ereview":
					   result = JUnitCore.runClasses(osciseReview.class);
					   break;		   
				   case "-osofaq":
					   result = JUnitCore.runClasses(osofaqTest.class);
					   break;					   
				   case "-pims":
					   result = JUnitCore.runClasses(pimsTest.class);
					   break;		   
				   case "-pmis":
					   result = JUnitCore.runClasses(pmisTest.class);
					   break;
				   //case "-rail":
					   //result = JUnitCore.runClasses(railTest.class);
					   //break;				   			      
				   case "-sma":
					   result = JUnitCore.runClasses(smaTest.class);
					   break;	   
				   case "-stat":
					   result = JUnitCore.runClasses(statTest.class);
					   break;
				   case "-stip":
					   result = JUnitCore.runClasses(stipTest.class);
					   break;	   
				   case "-oscbft":
					   result = JUnitCore.runClasses(oscisbftTest.class);
					   break;
				   case "-ooiwebapps":
					   result = JUnitCore.runClasses(ooiwebappsTest.class);
					   break;					   
				   case "-tms":
					   result = JUnitCore.runClasses(oscistmsTest.class);
					   break;			   
				   case "-unix":
					   result = JUnitCore.runClasses(TOPTest.class);
					   break;
				   case "-trip":
					   result = JUnitCore.runClasses(tripTest.class);
					   break;			   
				   case "-um":
					   result = JUnitCore.runClasses(umTest.class);
					   break;   
				   case "-vims":
					   result = JUnitCore.runClasses(vimsTest.class);
					   break;
				   case "-vovici":
					   result = JUnitCore.runClasses(voviciTest.class);
					   break;	
				   case "-ntp":
					   result = JUnitCore.runClasses(osontpTest.class);
					   break;	
				   case "-help":
					   help helped = new help();
					   helped.getHelp();
					   result = NULL;
					   break;
				   case "-serverstatus":
					   result = JUnitCore.runClasses(serverTest.class);
					   break;			   
				   default:
					   System.out.println("No tests match your entry!");
					   result = NULL;
					   break;
			   } 
			   if (result !=NULL){
				   TestDateTime datetime = new TestDateTime();
				   System.out.println("\n-----------------------RESULTS-------------------------"); 
				   datetime.getdatetime();
				   System.out.println("Failure(s): "   + result.getFailureCount());
				   System.out.println("Run Count: "    + result.getRunCount());
				   System.out.println("Duration: "     + result.getRunTime() + "ms");		      
				   System.out.println("-------------------------------------------------------");			   
				   for (Failure failure : result.getFailures()) {
					   System.out.println(failure.toString());}
					} 
			       System.out.println("-------------------------------------------------------");	
		   }
	   } else {
		   System.out.println("Tests Failed To Run or No Test Were Chosen");
	   } 
      }
	}
 