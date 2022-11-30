/*
	Daren Kostov
	robocode robot
	DAK-Test

	sources used:
	https://www.reddit.com/r/gamedev/comments/16ceki/turret_aiming_formula/
	Older projects of mine
	https://stackoverflow.com/questions/1878907/how-can-i-find-the-smallest-difference-between-two-angles-around-a-point
	
EVERYTHING IS IN RADIANS, NO DEGREES, NEVER. USE Math.toDegrees() IF THE FUNCTION EXPECTS DEGREES 
CORDINATES ARE NOT RELATIVE TO OUR TANK


	
	
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
public class AimBot extends AdvancedRobot
{


	int targetX=0;
	int targetY=0;
	
	int targetDX=1;
	int targetDY=1;
	
	int targetDist=0;
	
	double CurRotation=5;//current rotation
	
	double nextAngle=0;

	public void onPaint(Graphics2D g) {
	    // Set the paint color to red
	    g.setColor(java.awt.Color.RED);

		//draw target		
	    g.fillRect((int)(targetX), (int)(targetY), 10, 10);
		
		//draw target direction
	    g.setColor(java.awt.Color.GREEN);
	    g.fillRect((int)(targetX+targetDX), (int)(targetY+targetDY), 10, 10);
		
		//draw us
	    g.fillRect((int)getX(), (int)getY(), 10, 10);
		
		
		
		
		//draw where we think the target will be
		
	    g.setColor(java.awt.Color.ORANGE);
	    g.fillRect((int)(Math.cos(nextAngle)*targetDist+getX()), (int)(Math.sin(nextAngle)*targetDist+getY()), 10, 10);
	
	
	
	
	
	
	
	
	
		g.setColor(new Color(0xff, 0x00, 0x00, 0x20));

	    // Draw a line from our robot to the scanned robot
	    g.drawLine(targetX, targetY, (int)getX(), (int)getY());

	    // Draw a filled square on top of the scanned robot that covers it
	    g.fillRect(targetX - 20, targetY - 20, 40, 40);
	
	
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

				
		while(true) {
			// System.out.println()
		
		
			if(-CurRotation<0)
				turnGunRight(deg(CurRotation));
			else
				turnGunLeft(deg(-CurRotation));
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	

	
		// getRobotCoords(e);
		// getRobotVel(e);
		setGunBearing(getTargetAngle(getRobotCoords(e),getRobotVel(e), 10000));
		
		

		scan();
		
		
		
		
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
	
	
	//accepts only radians, nothing else
	private void setGunBearing(double n){
	
		
		
		// double difference=n-getGunRotation();
		// double rotation=mod(((difference) + Math.PI),Math.PI*2) - Math.PI
		double sour=getGunRotation();
		double dest=n;
		CurRotation=Math.atan2(Math.sin(dest-sour), Math.cos(dest-sour))/10;
		
		// System.out.println(deg(CurRotation));
		
		
	}
	
	
	
	
	
	//grabs the targets coords
	private double[] getRobotCoords(ScannedRobotEvent target){
		double[] coords= new double[2];
		
		
		double myDir=getGunHeadingRadians();
		double tarDis=target.getDistance();
		
		targetDist=(int)tarDis;
		//X
		coords[0]=Math.sin(myDir);
		coords[0]*=tarDis;	
		coords[0]+=getX();			
				
		//Y
		coords[1]=Math.cos(myDir);
		coords[1]*=tarDis;	
		coords[1]+=getY();
		
		
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
	
	
	//gets what our angle should be to be able to shoot the target
	//0 is X, 1 us Y
	private double getTargetAngle(double[] targetC, double[] targetD, double bulletSpeed) {
		targetC[0]-=getX();
		targetC[1]-=getY();
	
	    double rCrossV = targetC[0] * targetD[0] - targetC[1] * targetD[0];
	    double magR = Math.sqrt(targetC[0]*targetC[0] + targetC[1]*targetC[1]);
	    double angleAdjust = -Math.asin(rCrossV / (bulletSpeed * magR));
	
		double angle= angleAdjust + Math.atan2(targetC[1], targetC[0]);
	
		

		nextAngle=angle;
		

		System.out.println("next:       "+nextAngle);
		// System.out.println("current:    "+getGunRotation());
		
		return modulo(angle, Math.PI*2);
		
		
		
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
	
	
}
