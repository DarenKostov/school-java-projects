package dk;
import robocode.*;
import java.awt.Color;

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


    public void run() {
        
        //set the color    
		Color body=new Color(0, 0, 0);
		Color gun=new Color(0, 0, 0);
		Color radar=new Color(119, 255, 119);
		Color bullet=new Color(119, 255, 119);
		Color arc=new Color(119, 255, 119);
        setColors(body, gun, radar, bullet, arc);

        while(true)
    		turnGunRightRadians(Math.PI*2);   
        
    }


    public void onScannedRobot(ScannedRobotEvent target) {
        setTurnRightRadians(target.getBearingRadians());
        setAhead(100);
        setFire(3);
        direction=-direction;
        setTurnGunRightRadians(direction*Math.PI*2);    
        execute();

    }


}



