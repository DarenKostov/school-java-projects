/*
	Daren Kostov
	robocode robot
	Mental map robot- a robot that creates a map of all enemies

	sources used:
	



	
	
*/



//TODO
//get target coords
//get targed dx, dy



package dk;
import robocode.*;
import robocode.util.Utils;


import java.awt.Color;

import java.lang.Math;





import java.util.*;

//for debugging
import java.awt.Graphics2D;




// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Test - a robot by (your name here)
 */
public class MentalMap extends AdvancedRobot
{


	// final Map<String, RobotData> enemyMap;


	
	public MentalMap(){
	// 	enemyMap = new LinkedHashMap<String, RobotData>(5, 2, true);


	}
	
	
	

	public void onPaint(Graphics2D g) {
	    // Set the paint color to red
	    g.setColor(java.awt.Color.RED);

	
		g.setColor(new Color(0xff, 0x00, 0x00, 0x50));
	
		// for (RobotData robot : enemyMap.values()) {
		//     g.fillRect((int)robot.X - 20, (int)robot.Y-20, 40, 40);
		// }	
	
	
	}


	/**
	 * run: Test's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:



		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);

		
		Color body=new Color(0, 0, 0);
		Color gun=new Color(0, 0, 0);
		Color radar=new Color(119, 255, 119);
		Color bullet=new Color(119, 255, 119);
		Color arc=new Color(119, 255, 119);
		
		setColors(body, gun, radar, bullet, arc); // body,gun,radar

		
		setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
		while(true) {
			// System.out.println()
	
			// turnRadarRightRadians(Math.PI*2);
			turnGunRight(5);
			
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	
		// String name=e.getName();
		
		// RobotData robot=enemyMap.get(name);
		
		
		// if(robot==null){//if robot isnt in our map, add it
		// 	robot = new RobotData(e);
		// 	enemyMap.put(name, robot);
			
		// }else//if it is in our map, update its info
		// 	robot.update(e);
		
		
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		//back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		//back(20);
	}	
	
	
	class RobotData{
		double X;
		double Y;
		String Name;		
		
		RobotData(ScannedRobotEvent robot){
			
			//set the coords
			update(robot);
			
			//set the name, so its distiguashable
			Name=robot.getName();
		}
		
		void update (ScannedRobotEvent robot){
		
			double angle = getHeadingRadians() + robot.getBearingRadians();
			double distance=robot.getDistance();
			
			//set X and Y
			X=getX() + Math.sin(angle) * distance;
			Y=getY() + Math.cos(angle) * distance;

		}
	}	
	
	
}
