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
public class DAKT extends AdvancedRobot
{


	final Map<String, RobotData> enemyMap;


	
	public DAKT(){
		enemyMap = new LinkedHashMap<String, RobotData>(5, 2, true);


	}
	
	
	

	public void onPaint(Graphics2D g) {
	    // Set the paint color to red
	    g.setColor(java.awt.Color.RED);

	
	
		for (RobotData robot : enemyMap.values()) {
			if(robot.fired)
				g.setColor(new Color(0xff, 0x00, 0x00, 0x50));
			else			
				g.setColor(new Color(0x00, 0xff, 0x00, 0x50));
						
			g.fillOval((int)robot.x - 20, (int)robot.y-20, 40, 40);
			
			
			g.setColor(new Color(0x00, 0x00, 0x00));
			g.drawOval((int)robot.x - 20, (int)robot.y-20, 40, 40);
			
			
		    g.setColor(java.awt.Color.RED);

			g.drawLine((int)robot.x, (int)robot.y, (int)(robot.x+Math.sin(robot.gunAngle)*100), (int)(robot.y+Math.cos(robot.gunAngle)*100));	
		}	
	
	
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
			
			
			
			
			for (RobotData robot : enemyMap.values()) {
				robot.predictNextCoords();
			
			
			
			
			}
			
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	
		String name=e.getName();
		
		RobotData robot=enemyMap.get(name);
		
		
		if(robot==null){//if robot isnt in our map, add it
			robot = new RobotData(e);
			enemyMap.put(name, robot);
			
		}else//if it is in our map, update its info
			robot.update(e);
		

		// setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());	
	}


	
	//remove dead robots from our mental map
	public void onRobotDeath(RobotDeathEvent e) {
		enemyMap.remove(e.getName());
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

	
		
			
	//class that stores robot data
	//TODO find a way to get the robots gun & radar angles
	class RobotData{
		final String name;		
		//coords
		double x;
		double y;
		
		//deltas
		double dX;
		double dY;
		double velocity;
		
		//angles
		double bodyAngle;
		double gunAngle;
		double radarAngle;
			
			
		//the angle between us and this robot
		//(we gotta have this angle to shoot the robot) 
		double usAngle;
		//same as usAngle but in the future
		double nextUsAngle;

		//misc
		double energy;
		double bearing;
		double distance;
		
		
		//when fired variables
		boolean fired=false;
		double xWhenFired;
		double yWhenFired;
		
		RobotData(ScannedRobotEvent robot){
			
			//set the coords
			update(robot);
			
			//set the name, so its distiguashable
			name=robot.getName();
		}
		
		//updates everything in a RobotData
		void update (ScannedRobotEvent robot){
			updateNoCalc(robot);
			updateCoords(robot);
			updateDeltas(robot);
			

		}
		
		
		//updates data that doesnt have to be calculated
		void updateNoCalc (ScannedRobotEvent robot){
			
			bearing=robot.getBearingRadians();		
			distance=robot.getDistance();
		
			bodyAngle=robot.getHeadingRadians();
			gunAngle=robot.getHeadingRadians();
			velocity=robot.getVelocity();

			//checks if the robot fired (lost 0-4 energy)
			if(energy-robot.getEnergy()>0 && energy-robot.getEnergy()<4){
				xWhenFired=x;
				yWhenFired=y;
				fired=true;
			}else
				fired=false;
			energy=robot.getEnergy();			
		}
		
		
		//updates the coordinates 
		void updateCoords (ScannedRobotEvent robot){
			usAngle = getHeadingRadians()+bearing;
			
			//set X and Y
			x=getX()+Math.sin(usAngle)*distance;
			y=getY()+Math.cos(usAngle)*distance;

		}
		
		
		//updates the deltas
		void updateDeltas (ScannedRobotEvent robot){
			
			dX=Math.sin(bodyAngle)*velocity;
			dY=Math.cos(bodyAngle)*velocity;
		}
		
		void predictNextCoords(){
			x+=dX;
			y+=dY;
		}
		
	}	
	
	
}
