/*
	Daren Kostov
	robocode robot
	DAKT- DAK Test, a testing robot

	sources used:
	http://mark.random-article.com/weber/java/robocode/	
	https://www.reddit.com/r/gamedev/comments/16ceki/turret_aiming_formula/
	https://stackoverflow.com/questions/1878907/how-can-i-find-the-smallest-difference-between-two-angles-around-a-point
	https://www.geeksforgeeks.org/concurrenthashmap-in-java/
	
	
*/






package dk;
import robocode.*;
import robocode.util.Utils;


import java.awt.Color;

import java.lang.Math;

import java.util.concurrent.*;


import java.util.*;

//for debugging
import java.awt.Graphics2D;




// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Test - a robot by (your name here)
 */
public class DAKT extends AdvancedRobot
{


	final ConcurrentHashMap<String, RobotData> enemyMap;
	
	RobotData currentTarget;	
	
	


	
	double fa=0;
	double nextX;
	double nextY;
	
	// double width=getBattleFieldWidth();
	// double height=getBattleFieldHeight();
	


	
	
	
	public DAKT(){
		enemyMap = new ConcurrentHashMap<String, RobotData>();

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

			
						
			g.drawLine((int)(getX()+1000*Math.sin(fa)), (int)(getY()+1000*Math.cos(fa)), (int)(getX()-1000*Math.sin(fa)), (int)(getY()-1000*Math.cos(fa)));	
		}	
	
	
		    g.setColor(java.awt.Color.BLUE);
	
			g.drawLine((int)(getX()), (int)(getY()), (int)(getX()+1000*Math.sin(getHeadingRadians())), (int)(getY()+1000*Math.cos(getHeadingRadians())));	
			
			
			g.setColor(new Color(0xff, 0x77, 0x00, 0x50));
			g.fillOval((int)nextX - 20, (int)nextY-20, 40, 40);
	}


	/**
	 * run: Test's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:


		//make every part of the robot move independently
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);

		
		Color body=new Color(0, 0, 0);
		Color gun=new Color(0, 0, 0);
		Color radar=new Color(119, 255, 119);
		Color bullet=new Color(119, 255, 119);
		Color arc=new Color(119, 255, 119);
		
		setColors(body, gun, radar, bullet, arc); // body,gun,radar

		
		
		
		nextX=getX();
		nextY=getY();
		
		setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
		while(true) {
				
			//reset target
			currentTarget=null;
		
		
			// System.out.println()
	
			if(enemyMap.isEmpty())
				turnGunRight(5);
			

			
			System.out.println("starting for loop");
			for (RobotData robot : enemyMap.values()) {
				
				//if there is no target, this is our target
				if(currentTarget==null)
					currentTarget=robot;
					
				//if this robot has lower health than our target, this is our target
				
				if(currentTarget.energy>robot.energy)
					currentTarget=robot;
				
				System.out.println("starting current loop");
			
				System.out.println("updating: "+robot.name);
				
				robot.predictNextCoords();

				System.out.println("updated: "+robot.name);
						
					
				if(robot.hasFired()){
				

					//turn perpendicular to the fired shot
					turnRightRadians(robot.bearing+Math.PI/2);
					

					double heading=getHeadingRadians();
					
					
					//make sure we dont go into a wall
					nextX=getX()+Math.sin(heading)*100;
					nextY=getY()+Math.cos(heading)*100;
					int distanceFromWall=30;
					//"set" because there can be multiple attackers and we dont wanna just dodge aimlessly all bullets
					if(nextX<distanceFromWall || nextY<distanceFromWall || nextX>getBattleFieldWidth()-distanceFromWall || nextY>getBattleFieldHeight()-distanceFromWall){
						setBack(100);
					
						System.out.println("going back");
					}else{
						setAhead(100);
						System.out.println("going ahead");					
					}
					
					
					
				}
				//predict where the robot will be
				robot.predictNextCoords();
				
				
			}
			
			//aim and shoot if we have a target
			if(currentTarget!=null){

				double angle=aimAngle(currentTarget, Rules.getBulletSpeed(1));
				fa=angle;
				angle-=getGunHeadingRadians();
				
				angle=Utils.normalRelativeAngle(angle);
				// System.out.println(Rules.getBulletSpeed(1));
				// System.out.println("=================");
								
				
				if(angle<Math.PI)
					turnGunRightRadians(angle);
				else
					turnGunLeftRadians(angle-Math.PI);
				
				
				fire(1);
			}							
						
			System.out.println("ending current loop");
			
			System.out.println("size of map: "+enemyMap.size());

		System.out.println("ending for loop");
		}
		
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		String name=e.getName();
		
		System.out.println("scanned: "+name);
		RobotData robot=enemyMap.get(name);
		System.out.println("got data from: "+name);
		
		if(robot==null){//if robot isnt in our map, add it
			robot = new RobotData(e);
			enemyMap.put(name, robot);
			System.out.println("==added: "+name);
		}else//if it is in our map, update its info
			robot.update(e);
		
		System.out.println("stopped scanning: "+name);
	}


	
	//remove dead robots from our mental map
	public void onRobotDeath(RobotDeathEvent e) {
	
		System.out.println("removed: "+e.getName());
		enemyMap.remove(e.getName());
	}




	public void onBulletHit(BulletHitEvent bhe){
		//set the robot with the name we hit to "hit" since we just hit it
		RobotData robot= enemyMap.get(bhe.getName());
		
		if(!(robot==null))//if the robot didnt die with this hit
			robot.gotHit=true;	
	
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

	
	
	
	
	


	double aimAngle(RobotData target, double bulletSpeed) {
		
		double targetx=target.x-getX();
		double targety=target.y-getY();
		double targetdx=target.dX;
		double targetdy=target.dY;
	
	
	
	    double rCrossV = targetx * targetdy - targety * targetdx;
	    double distance = Math.sqrt(targetx*targetx + targety*targety);
	    double angleAdjust = Math.asin(rCrossV / (bulletSpeed * distance));
	    return -angleAdjust + Math.atan2(targetx, targety);
	}

	
	
	
	
	

    //number and mod
    private double modulo(double n, double m){
            return (n - Math.floor(n/m) * m);
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
		
		boolean gotHit=false;

		ScannedRobotEvent event;
		
		
		RobotData(ScannedRobotEvent robot){

			//set the coords
			update(robot);
			
			//set the name, so its distiguashable
			name=robot.getName();
		}
		
		//updates everything in a RobotData
		void update (ScannedRobotEvent robot){
			event=robot;
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
			
			
			
			//if we didnt hit the robot
			if(!gotHit){
			//checks if the robot fired (lost 0-4 energy)
				if(energy-robot.getEnergy()>0 && energy-robot.getEnergy()<4){
					xWhenFired=x;
					yWhenFired=y;
					fired=true;
				}
			}else{
				gotHit=false;
			}
				
			
			energy=robot.getEnergy();			
		}
		
		
		boolean hasFired(){
			boolean tmp=fired;
			fired=false;
			return tmp;
		
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
