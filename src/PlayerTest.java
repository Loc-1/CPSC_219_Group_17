import org.junit.Test;

import static org.junit.Assert.assertEquals;



public class PlayerTest{

	@Test
	public void test_x_coordinate_movement(){
		Player tester = new Player(0,0,0,0,"null");
		assertEquals("failed to initialize x coordinate", 0, tester.getxLocation());
		tester.moveUp();
		assertEquals("movement up failed", -1, tester.getxLocation());
		tester = new Player(1,1,1,1,"null");
		tester.moveUp();
		assertEquals("movement up failed", 0, tester.getxLocation());
		tester = new Player(3,3,3,3,"null");
		tester.moveUp();
		assertEquals("movement up failed",2,tester.getxLocation());
		tester = new Player(5,5,5,5,"null");
		tester.moveUp();
		tester.moveUp();
		assertEquals("consecutive movements up failed",3,tester.getxLocation());
		tester = new Player(0,0,0,0,"null");
		assertEquals("failed to initialize x coordinate", 0, tester.getxLocation());
		tester.moveDown();
		assertEquals("movement down failed", 1, tester.getxLocation());
		tester = new Player(1,1,1,1,"null");
		tester.moveDown();
		assertEquals("movement down failed", 2, tester.getxLocation());
		tester = new Player(3,3,3,3,"null");
		tester.moveDown();
		assertEquals("movement down failed",4,tester.getxLocation());
		tester = new Player(5,5,5,5,"null");
		tester.moveDown();
		tester.moveDown();
		assertEquals("consecutive movements down failed",7,tester.getxLocation());

	}



	@Test
	public void test_y_coordinate_movement(){
		Player tester = new Player(0,0,0,0,"null");
		assertEquals("failed to initialize y coordinate", 0, tester.getyLocation());
		tester.moveLeft();
		assertEquals("movement left failed", -1, tester.getyLocation());
		tester = new Player(1,1,1,1,"null");
		tester.moveLeft();
		assertEquals("movement left failed", 0, tester.getyLocation());
		tester = new Player(3,3,3,3,"null");
		tester.moveLeft();
		assertEquals("movement left failed",2,tester.getyLocation());
		tester = new Player(5,5,5,5,"null");
		tester.moveLeft();
		tester.moveLeft();
		assertEquals("consecutive left movements failed",3,tester.getyLocation());
		tester = new Player(0,0,0,0,"null");
		assertEquals("failed to initialize y coordinate", 0, tester.getyLocation());
		tester.moveRight();
		assertEquals("movement right failed", 1, tester.getyLocation());
		tester = new Player(1,1,1,1,"null");
		tester.moveRight();
		assertEquals("movement right failed", 2, tester.getyLocation());
		tester = new Player(3,3,3,3,"null");
		tester.moveRight();
		assertEquals("movement right failed",4,tester.getyLocation());
		tester = new Player(5,5,5,5,"null");
		tester.moveRight();
		tester.moveRight();
		assertEquals("consecutive right movements failed",7,tester.getyLocation());
		
	}
	
	
	@Test
	public void test_setter_getter_score(){
		Player tester = new Player(0,0,0,0,"null");
		tester.setScore(-19);
		assertEquals("Score set/get error",-19,tester.getScore());
		tester = new Player(0,0,0,0,"null");
		tester.setScore(0);
		assertEquals("Score set/get error",0,tester.getScore());
		tester = new Player(0,0,0,0,"null");
		tester.setScore(8);
		assertEquals("Score set/get error",8,tester.getScore());
		tester = new Player(0,0,0,0,"null");
		tester.setScore(24);
		assertEquals("Score set/get error",24,tester.getScore());

	}
	
		
	@Test
	public void test_alive_kill_actions(){
		Player tester = new Player(10,15,0,0,"null");
		assertEquals("failed to initialize life status", true, tester.isAlive());
		tester.kill();
		assertEquals("failed to kill player", false, tester.isAlive());
	
	}


}