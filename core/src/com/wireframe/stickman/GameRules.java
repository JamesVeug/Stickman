package com.wireframe.stickman;

public class GameRules {
	public static int redKills = 0;
	public static int blueKills = 0;
	
	public static void kill(Character victim, Character killer){
		World.removeCharacter(victim);
		
		// Player dies
		if( victim == World.getPlayer() ){
			System.err.println("PLAYER DIED");
			System.err.println("PLAYER DIED");
			System.err.println("PLAYER DIED");
			System.err.println("PLAYER DIED");
		}
		
		// Team 1 dies
		else if( victim.getTeamNumber() == Character.TEAM_BLUE ){
			if( killer.getTeamNumber() == Character.TEAM_BLUE ){
				blueKills--;
			}
			else if( killer.getTeamNumber() == Character.TEAM_RED ){
				redKills++;
			}			
		}
		
		// Team 2 dies
		else if( victim.getTeamNumber() == Character.TEAM_RED ){			
			if( killer.getTeamNumber() == Character.TEAM_RED ){
				redKills--;
			}
			else if( killer.getTeamNumber() == Character.TEAM_BLUE ){
				blueKills++;
			}			
		}
	}
}
