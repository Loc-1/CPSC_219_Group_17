import static org.junit.Assert.*;
import org.junit.Test;


//testing the PowerUp class

public class PowerUpTest {


	// test for setType() method
	@Test
	public void test_set_type(){
		PowerUp tester = new PowerUp();
		tester.setType("null");
		assertNotNull("type not set",tester.type);
		tester = new PowerUp();
		tester.setType("type");
		assertEquals("","expected type",tester.type);
	}
	
	
	//test for setLocation() method
	@Test
	public void test_set_location(){
		PowerUp tester = new PowerUp();
		tester.setLocation(0,1);
		assertEquals("x coordinate not set",0,tester.x_Location);
		assertEquals("y coordinate not set",1,tester.y_Location);
	
	}




}