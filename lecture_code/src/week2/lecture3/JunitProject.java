package week2.lecture3;

import static org.junit.Assert.*;
import org.junit.Test;


public class JunitProject {
	@Test
	public void test_Junit() {
		System.out.println("Let's check this string");
		String str1 = "Let's check this string1";
		assertEquals("Let's check this string", str1);
		
	}
//	Run As Junit Test -> Failure Trace -> Show Stack Trace in Console View
	
}
