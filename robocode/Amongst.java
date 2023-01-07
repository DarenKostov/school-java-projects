package dk;
import robocode.*;
import robocode.util.Utils;
import java.awt.Color;
import java.util.*;
import java.lang.Math;
import java.awt.Graphics2D;
import java.awt.BasicStroke;



/*
Daren Kostov
Amongst- a bot that.... it just shoots and rams into a target


*/

/*
sources:
    https://www.reddit.com/r/gamedev/comments/16ceki/turret_aiming_formula/
    https://gist.github.com/rogerluan/d62a26039d9bcb9395f5d391fb1a17ae
*/

public class Amongst extends AdvancedRobot {

    int direction=1;

    double nowX=0;
    double nowY=0;
    //(exadurated)
    double nextX=0;
    double nextY=0;



	public void onPaint(Graphics2D g) {

		// g.setStroke(new BasicStroke(2f));
    
        g.setColor(new Color(0xff, 0x00, 0x00, 0x50));
        g.fillOval((int)(getX()+nowX) - 20, (int)(getY()+nowY)-20, 40, 40);
        
        g.setColor(new Color(0xff, 0x00, 0x00));
        g.setStroke(new BasicStroke(10f));
    	g.drawLine((int)(getX()+nowX), (int)(getY()+nowY), (int)(getX()+nextX), (int)(getY()+nextY));	


    }






    public void run() {
        
        //set the color    
		Color body=new Color(0, 0, 0);
		Color gun=new Color(0, 0, 0);
		Color radar=new Color(119, 255, 119);
		Color bullet=new Color(119, 255, 119);
		Color arc=new Color(119, 255, 119);
        setColors(body, gun, radar, bullet, arc);

		//make every part of the robot move independently
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);


        //spin the radar infinitly
		setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
        while(true){
            doNothing();
        }
        
    }


    public void onScannedRobot(ScannedRobotEvent target) {
        //=calc bullet power

        int bulletPower=1;
    	if(target.getDistance()<500)
        	bulletPower++;
    	if(target.getDistance()<200)
            bulletPower++;

        //=calc target parameters

        //at which angle is the target from us
        double angle=getHeadingRadians()+target.getBearingRadians();
 
        double targetx=Math.sin(angle)*target.getDistance();
        double targety=Math.cos(angle)*target.getDistance();
        nowX=targetx; nowY=targety;
        double targetdx=Math.sin(target.getHeadingRadians())*target.getVelocity();
        double targetdy=Math.cos(target.getHeadingRadians())*target.getVelocity();
        nextX=targetx+targetdx*5; nextY=targety+targetdy*5;

        //=calc gun rotation     
        double gunRotation=aimAngle(targetx, targety, targetdx, targetdy, Rules.getBulletSpeed(bulletPower));
        gunRotation-=getGunHeadingRadians();
    	gunRotation=Utils.normalRelativeAngle(gunRotation);

        //=set gun rotation
        if(gunRotation<Math.PI)
        	setTurnGunRightRadians(gunRotation);
        else
        	setTurnGunLeftRadians(gunRotation-Math.PI);


        //=set movement
        setTurnRightRadians(target.getBearingRadians());
        setAhead(100);
        direction=-direction;
        setTurnRadarRightRadians(direction*Math.PI*2);  


        

          
        execute();


        //Shoot! at the end because we still havent rotated the barrel
        setFire(bulletPower);
        
    }


    //calculates where the barrel should be given the target parameters and bullet speed
	double aimAngle(double targetx, double targety, double targetdx, double targetdy, double bulletSpeed) {
	    double rCrossV = targetx * targetdy - targety * targetdx;
	    double magR = Math.sqrt(targetx*targetx + targety*targety);
	    double angleAdjust = Math.asin(rCrossV / (bulletSpeed * magR));
	    return -angleAdjust + Math.atan2(targetx, targety);
	}





}



