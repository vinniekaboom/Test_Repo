package network.ite.run.com;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner1 {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(JunitTestSuite.class);
      System.out.println("\n\n-----------------------RESULTS-------------------------\n\n");
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }      
      System.out.println(result.wasSuccessful());
      System.out.println("------------------------------------------------------");
   }
}
