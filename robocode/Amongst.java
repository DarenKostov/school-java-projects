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
the bot is amongst the.... other bots? 

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

    long colorFrame=0;


	public void onPaint(Graphics2D g) {

		// g.setStroke(new BasicStroke(2f));
    
        g.setColor(new Color(0xff, 0x00, 0x00, 0x50));
        g.fillOval((int)(getX()+nowX) - 20, (int)(getY()+nowY)-20, 40, 40);
        
        g.setColor(new Color(0xff, 0x00, 0x00));
        g.setStroke(new BasicStroke(10f));
    	g.drawLine((int)(getX()+nowX), (int)(getY()+nowY), (int)(getX()+nextX), (int)(getY()+nextY));	


    }






    public void run() {

        {
            //set the color    
    		Color body=new Color(0, 0, 0);
    		Color gun=new Color(0, 0, 0);
    		Color radar=new Color(255, 204, 119);
    		Color bullet=new Color(255, 204, 119);
    		Color arc=new Color(255, 204, 119);
            setColors(body, gun, radar, bullet, arc);
        }

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


            colorFrame++;
    		Color body=new Color(0, 0, 0);

            double colorPhase=(Math.sin(colorFrame/2)+1)/2;

            
    		Color accent=new Color((int)map(colorPhase, 1, 0, 255, 0), (int)map(colorPhase, 1, 0, 0, 255), (int)map(colorPhase, 1, 0, 0, 119));

            
            setColors(body, body, accent, accent, accent);

            // System.out.println((int)Math.max(1,(Math.sin(colorPhase)*204)));
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


        //are we about to die before the target?
        if(getEnergy()+70<target.getEnergy()){
            //calc out next position
            double myNextRotation=getHeadingRadians()+target.getBearingRadians()+Math.PI;
            double myNextX=getX()+Math.sin(myNextRotation)*100;
            double myNextY=getY()+Math.cos(myNextRotation)*100;
        

            
            
            //we don't hit a wall right?
			int distanceFromWall=100;
            if(myNextX>distanceFromWall && myNextY>distanceFromWall && myNextX<getBattleFieldWidth()-distanceFromWall && myNextY<getBattleFieldHeight()-distanceFromWall)
                setTurnRightRadians(target.getBearingRadians()+Math.PI);
            
            //we do?
            else{
                //recalculate position in other direction
                myNextRotation=getHeadingRadians()+target.getBearingRadians()-Math.PI;
                myNextX=getX()+Math.sin(myNextRotation)*100;
                myNextY=getY()+Math.cos(myNextRotation)*100;
                // if we go the other direction we wont hit a wall again right?
                if(myNextX>distanceFromWall && myNextY>distanceFromWall && myNextX<getBattleFieldWidth()-distanceFromWall && myNextY<getBattleFieldHeight()-distanceFromWall)
                    setTurnRightRadians(target.getBearingRadians()-Math.PI);
                //we hit a wall again?
                else{
                    //we are most likely at a corner so no need to try to do 180 (we'll hit a a wall again)
                    //so lets just crank up the speed (if it wasnt already)
                	setMaxVelocity(Rules.MAX_VELOCITY);
                    setAhead(200);
                }
            }
        }   
          
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

    double map(double n, double start1, double stop1, double start2, double stop2){
        return ((n-start1)/(stop1-start1))*(stop2-start2)+start2;
    }
    


}



