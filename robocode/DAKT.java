/*
	Daren Kostov
	robocode robot
	DAK-Test

	sources used:
	https://www.reddit.com/r/gamedev/comments/16ceki/turret_aiming_formula/
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
import java.awt.Color;

import java.lang.Math;


//for debugging
import java.awt.Graphics2D;




// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Test - a robot by (your name here)
 */
public class DAKT extends AdvancedRobot
{


	int targetX=0;
	int targetY=0;
	int targetDX=1;
	int targetDY=1;
	int targetDist=0;
	
	double rotation=0;
	int rotationD=1;
	
	double nextAngleD=0;
	double nextAngle=0;

	public void onPaint(Graphics2D g) {
	    // Set the paint color to red
	    g.setColor(java.awt.Color.RED);

		//draw target		
	    g.fillRect((int)(targetX+getX()), (int)(targetY+getY()), 10, 10);
		
		//draw target direction
	    g.setColor(java.awt.Color.GREEN);
	    g.fillRect((int)(targetX+getX()+targetDX), (int)(targetY+getY()+targetDY), 10, 10);
		
		//draw us
	    g.fillRect((int)getX(), (int)getY(), 10, 10);
		
		
		//draw where we think the target will be
		
	    g.setColor(java.awt.Color.ORANGE);
	    g.fillRect((int)(Math.cos(nextAngle)*targetDist+getX()), (int)(Math.sin(nextAngle)*targetDist+getY()), 10, 10);
	}


	/**
	 * run: Test's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		Color body=new Color(0, 0, 0);
		Color gun=new Color(0, 0, 0);
		Color radar=new Color(119, 255, 119);
		Color bullet=new Color(119, 255, 119);
		Color arc=new Color(119, 255, 119);
		
		setColors(body, gun, radar, bullet, arc); // body,gun,radar
		
				
		//setColors()


		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			//ahead(100);
			//turnGunRight(360);
			//back(100);i
			if(rotation<0)
				turnGunRight(Math.min(2,-rotationD));
			else
			
				turnGunLeft(Math.min(1,rotationD));
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	

	
		// getRobotCoords(e);
		// getRobotVel(e);
		aimAngle(getRobotCoords(e),getRobotVel(e), 1000);
		
		
		rotationD=200;

		
		
		
		
		
		//scan();
		// Replace the next line with any behavior you would like
		//fire(1);
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
	
	//grabs the targets dx and xy
	private double[] getRobotVel(ScannedRobotEvent target){
		double[] d= new double[2];
		
		
		double tarDir=target.getHeadingRadians();
		double tarVel=target.getVelocity();
		
		
		//X
		d[0]=Math.sin(tarDir);
		d[0]*=tarVel*5;	
				
		//Y
		d[1]=Math.cos(tarDir);
		d[1]*=tarVel*5;	
		
		targetDX=(int)d[0];
		targetDY=(int)d[1];
		
		
		return d;		
		
	
	}
	
	
	//sets angle of rotation

//0 is X, 1 us Y
private void aimAngle(double[] targetC, double[] targetD, double bulletSpeed) {
    double rCrossV = targetC[0] * targetD[0] - targetC[1] * targetD[0];
    double magR = Math.sqrt(targetC[0]*targetC[0] + targetC[1]*targetC[1]);
    double angleAdjust = Math.asin(rCrossV / (bulletSpeed * magR));
	
	double angle= angleAdjust + Math.atan2(targetC[1], targetC[0]);
	


	nextAngleD=(angle/Math.PI*180);
	nextAngle=angle;//(angle/Math.PI*180);
//(int)getGunHeading();
	
	rotation=(nextAngle-getRotation());
	
	rotationD=(int)Math.toDegrees(rotation);


	System.out.println("next:       "+nextAngle);
	System.out.println("current:    "+getRotation());
	System.out.println("difference: "+rotation);
}
	
	
	//get the rotation, does not go above pi*2 or bellow 0 
	private double getRotation(){
				
		double output=getGunHeadingRadians();
		
		output%=(Math.PI*2);
		
		return output;



	}
	
	
	
	
}
