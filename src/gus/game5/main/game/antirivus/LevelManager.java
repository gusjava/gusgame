package gus.game5.main.game.antirivus;

import java.awt.Color;
import java.util.List;

import javax.swing.JOptionPane;

import gus.game5.core.persister.Persister1;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilRandom;

public class LevelManager {
	
	public static final String CURRENT_PROFILE_ID = "CURRENT_PROFILE_ID";
	public static final String OFFSET_PROFILE = "profile_";
	public static final String KEY_NAME = "name";
	public static final String KEY_LEVEL = "level";
	
	private GameAntivirus game;
	private Persister1 persister;
	private Profile profile;
	private int level;

	public LevelManager(GameAntivirus game) {
		this.game  = game;
		persister = new Persister1(game);
		level = 1;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getLevelTitle() {
		return UtilAntivirus.getLevelTitle(level);
	}
	
	public Color getLevelColor() {
		return UtilAntivirusColor.getLevelColor(level);
	}
	
	public int getLastLevel() {
		if(profile!=null) return profile.getLevel();
		return UtilAntivirus.LEVEL_NUMBER;
	}
	
	public void levelUp() {
		changeLevel(level+1);
	}
	
	public void levelDown() {
		changeLevel(level-1);
	}
	
	public void levelFirst() {
		changeLevel(1);
	}
	
	public void levelLast() {
		changeLevel(getLastLevel());
	}
	
	private boolean changeLevel(int level) {
		if(level<1) return false;
		if(level>getLastLevel()) return false;
		
		this.level = level;
		int[][] newData = UtilAntivirus.dataForLevel(level);
		game.setData(newData);
		return true;
	}
	
	public void levelWon() {
		if(profile!=null && level<UtilAntivirus.LEVEL_NUMBER) 
			profile.accessLevel(level+1);
		levelUp(); 
	}
	
	public void loadCurrent() {
		String currentProfileId = persister.get(CURRENT_PROFILE_ID);
		if(currentProfileId!=null) loadProfile(currentProfileId);
		else changeLevel(1);
	}
	
	
	/*
	 * PROFILE
	 */
	
	public class Profile {
			private String id;
			private String name;
			private int level;
			
			public Profile(String id, String name, int level) {
				this.id = id;
				this.name = name;
				this.level = level;
			}
			
			public int getLevel() {
				return level;
			}
			
			public String getDisplay() {
				return name+" "+level;
			}
			
			public void accessLevel(int newLevel) {
				if(newLevel>level) level = newLevel;
				persister.put(OFFSET_PROFILE+id, KEY_LEVEL, ""+level);
			}
		}
		
		public boolean hasProfile() {
			return profile!=null;
		}
		
		public String getProfileDisplay() {
			return profile!=null ? profile.getDisplay() : "";
		}
	
	public void createNewProfile() {
		String profileName = JOptionPane.showInputDialog("Please, enter profile's name:");
		if(profileName==null || profileName.equals("")) return;
		
		String profileId = ""+UtilRandom.randomInt(1000);
		persister.put(OFFSET_PROFILE+profileId, KEY_NAME, profileName);
		persister.put(OFFSET_PROFILE+profileId, KEY_LEVEL, "1");
		
		profile = new Profile(profileId, profileName, 1);
		persister.put(CURRENT_PROFILE_ID, profileId);
		changeLevel(1);
	}
	
	public void loadProfile() {
		List<String> names = persister.names(name->name.startsWith(OFFSET_PROFILE));
		List<String> profileIds = UtilList.collect(names, name->name.substring(OFFSET_PROFILE.length()));
		int profileNb = profileIds.size();
		if(profileNb==0) return;
		if(profileNb==1) loadProfile(profileIds.get(0));
		else {
			//CHOOSE ONE PROFILE
		}
	}
	
	public void loadProfile(String profileId) {
		String profileName = persister.get("profile_"+profileId, KEY_NAME);
		int profileLevel = persister.getInt("profile_"+profileId, KEY_LEVEL);
		
		profile = new Profile(profileId, profileName, profileLevel);
		changeLevel(profileLevel);
	}
	
	public void closeProfile() {
		profile = null;
		changeLevel(1);
	}
}
