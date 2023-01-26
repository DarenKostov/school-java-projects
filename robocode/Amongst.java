package teamNu;
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

public class Amongst extends TeamRobot {

    int direction=1;

    double nowX=0;
    double nowY=0;
    //(exadurated)
    double nextX=0;
    double nextY=0;

    long frame=0;

    double damageD=0;
    double energy=0;

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
        if(isTeammate(target.getName())){
            System.out.println("teamate!!!!");
            return;
        }
        System.out.println("enemy");

            
        frame++;


        damageD=Math.min(4,damageD+Math.max(0,energy-getEnergy()));

        energy=getEnergy();


        damageD*=0.9;

        
        //=color stuff


        Color color1=new Color(50, 255, 50);
        Color color2=new Color(255, 50, 50);

        
        //from which clor to which color
        Color body=new Color(0, 0, 0);
        // Color accent=new Color((int)map(colorPhase, 1, 0, 255, 255), (int)map(colorPhase, 1, 0, 204, 0), (int)map(colorPhase, 1, 0, 0, 0));
        Color arc=colorRamp(0.2, color1, color2);
        Color radar=colorRamp(0, map(damageD, 3, 1, 0, 1), color1, color2);
        setColors(body, body, radar, arc, arc);
        

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


     

    public void HitRobotEvent(String name, double bearing, double energy, boolean atFault){
    
        if(isTeammate(name))
            back(200);

    }


    public void onWin(WinEvent event){
        while(true){
            System.out.println("win event");
            frame++;
            Color color2=new Color(255, 0, 0);
            Color color1=new Color(0, 255, 0);
            Color color3=new Color(0, 0, 255);
            Color all=colorRamp(Math.PI/4, color1, color2);
            setColors(all, all, all, all, all);
            doNothing();
        }
    
    
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

    Color colorRamp(double speed, double pos, Color color1, Color color2){

        int c1r=color1.getRed();
        int c1g=color1.getGreen();
        int c1b=color1.getBlue();
        int c2r=color2.getRed();
        int c2g=color2.getGreen();
        int c2b=color2.getBlue();

        
        return new Color(
        (int)Math.max(0, Math.min(255, map(pos, 1, 0, c1r, c2r))),
        (int)Math.max(0, Math.min(255, map(pos, 1, 0, c1g, c2g))),
        (int)Math.max(0, Math.min(255, map(pos, 1, 0, c1b, c1b)))
        );
    
    }
    

    Color colorRamp(double speed, Color color1, Color color2){
        double position=(Math.sin(1.0*frame*speed)+1)/2;
        return colorRamp(0, position, color1, color2);
    }
}



