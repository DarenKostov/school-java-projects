/*
	Daren Kostov
	robocode robot
	DAK-Test

	sources used:
	https://www.reddit.com/r/gamedev/comments/16ceki/turret_aiming_formula/
	Older projects of mine
	https://stackoverflow.com/questions/1878907/how-can-i-find-the-smallest-difference-between-two-angles-around-a-point
	
EVERYTHING IS IN RADIANS, NO DEGREES, NEVER. USE Math.toDegrees() IF THE FUNCTION EXPECTS DEGREES 



	
	
*/



/*
function aimAngle(target, bulletSpeed) {
    var rCrossV = target.x * target.vy - target.y * target.vx;
    var magR = Math.sqrt(target.x*target.x + target.y*target.y);
    var angleAdjust = Math.asin(rCrossV / (bulletSpeed * magR));

    return angleAdjust + Math.atan2(target.y, target.x);
}

*/

//TODO
//get target coords
//get targed dx, dy



package dk;
import robocode.*;
import robocode.util.Utils;


import java.awt.Color;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//for debugging
import java.awt.Graphics2D;




// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Test - a robot by (your name here)
 */
public class MentalMap extends AdvancedRobot
{


	int targetX=0;
	int targetY=0;
	int targetDist=0;
	List<RobotData> enemies=new ArrayList<RobotData>();
	
	

	public void onPaint(Graphics2D g) {
	    // Set the paint color to red
	    g.setColor(java.awt.Color.RED);

	
	
	
	
	
		g.setColor(new Color(0xff, 0x00, 0x00, 0x50));

	    // Draw a line from our robot to the scanned robot
	    g.drawLine(targetX+(int)getX(), targetY+(int)getY(), (int)getX(), (int)getY());

	    // Draw a filled square on top of the scanned robot that covers it
	    g.fillRect(targetX+(int)getX() - 20, targetY+(int)getY() - 20, 40, 40);
	
	
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

		
				
		//setColors()

//turnGunRight(360);
		// Robot main loop
		
		while(true) {
			// System.out.println()
	
		setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
			 turnGunRight(5);
			
			// if(CurRotation<0)
			// 	turnGunRight(deg(-CurRotation));
			// else
	
			// 	turnGunLeft(deg(CurRotation));
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
		// getRobotCoords(e);
		// getRobotVel(e);
		
		
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
	
	//custom functions
	
	
	
	//grabs the targets coords
	private double[] getRobotCoords(ScannedRobotEvent target){
		double[] coords= new double[2];
		
		
		double myDir=getGunHeadingRadians();
		double tarDis=target.getDistance();
		
		targetDist=(int)tarDis;
		//X
		coords[0]=Math.sin(myDir);
		coords[0]*=tarDis;	
				
		//Y
		coords[1]=Math.cos(myDir);
		coords[1]*=tarDis;	
		
		targetX=(int)coords[0];
		targetY=(int)coords[1];
		
		
		return coords;		
		
	
	}
	
	
	
	
		//get the rotation, does not go above pi*2 or bellow 0 
		private double getGunRotation(){
				
			double output=getGunHeadingRadians();
		
			output%=(Math.PI*2);
			
			return output;



		}
	
		private double deg(double n){
		return Math.toDegrees(n);
	
		}
	//number and mod
	private double modulo(double n, double m){
		return (n - Math.floor(n/m) * m);
	}	
	
	
	class RobotData{
		double X
		double Y
		String Name
	
	}	
	
	
	
}
