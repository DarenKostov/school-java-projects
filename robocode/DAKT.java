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
	int targetDX0=0;
	int targetDY=0;

	public void onPaint(Graphics2D g) {
	    // Set the paint color to red
	    g.setColor(java.awt.Color.RED);
	    // Paint a filled rectangle at (50,50) at size 100x150 pixels
	    g.fillRect((int)(targetX+getX()), (int)(targetY+getY()), 10, 10);
	    g.fillRect((int)getX(), (int)getY(), 10, 10);
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
			//back(100);
			turnGunRight(5);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	

	
		getRobotCoords(e);
		scan();
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
	
	private double[] getRobotCoords(ScannedRobotEvent target){
		double[] coords= new double[2];
		
		
		double myDir=getGunHeadingRadians();
		double tarDis=target.getDistance();
		
		
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
	
	
	
	
	
}
