package week2.lecture3;

import org.junit.Test;
//import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

public class TestJunit {
	
	String message = "Simplilearn";

    @Test
    public void testPrintMessage() {
        System.out.println("Inside testPrintMessage()");
        assertEquals(message, "Simplilearn12");
    }
	
	public static void main(String[] args) {
		
		TestJunit result = new TestJunit();
        result.testPrintMessage();

	}

}
