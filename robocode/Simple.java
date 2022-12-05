/*

	Daren Kostov
	robocode robot
 	simple bot made for testing



	
	
*/





package dk;
import robocode.*;
import java.awt.Color;

import java.lang.Math;






// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Test - a robot by (your name here)
 */
public class Simple extends AdvancedRobot
{



	/**
	 * run: Test's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		

		

		turnRightRadians(Math.PI/2-getHeadingRadians());

	

		while(true) {
			ahead(600);	
			ahead(-600);		
		
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	

	
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
	}	
	
	
}
