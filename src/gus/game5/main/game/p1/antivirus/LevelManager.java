package gus.game5.main.game.p1.antivirus;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import gus.game5.core.persister.Persister1;
import gus.game5.core.util.UtilMap;
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
	
	public boolean changeLevel(int level) {
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
		if(!loadProfile(currentProfileId)) changeLevel(1);
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
	
	/*
	 * LOAD PROFILE
	 */
	
	public boolean loadProfile() {
		return loadProfile(chooseProfileId());
	}
	
	public boolean loadProfile(String profileId) {
		if(profileId==null) return false;
		String profileName = persister.get(OFFSET_PROFILE+profileId, KEY_NAME);
		Integer profileLevel = persister.getInt(OFFSET_PROFILE+profileId, KEY_LEVEL);
		if(profileName==null || profileLevel==null) return false;
		
		profile = new Profile(profileId, profileName, profileLevel);
		persister.put(CURRENT_PROFILE_ID, profileId);
		return changeLevel(profileLevel);
	}
	
	/*
	 * REMOVE PROFILE
	 */
	
	public void removeProfile() {
		String profileId = chooseProfileId();
		if(profileId!=null) removeProfile(profileId);
	}
	
	public void removeProfile(String profileId) {
		persister.remove(OFFSET_PROFILE+profileId);
		if(isProfile(profileId)) closeProfile();
	}
	
	/*
	 * CLOSE PROFILE
	 */
	
	public void closeProfile() {
		profile = null;
		persister.put(CURRENT_PROFILE_ID, null);
		changeLevel(1);
	}
	
	private String chooseProfileId() {
		Map<String, String> m = persister.allValuesForKey(KEY_NAME, name->name.startsWith(OFFSET_PROFILE));
		if(m.isEmpty()) return null;
		
		m = UtilMap.invMap0(m);
		List<String> names = new ArrayList<>(m.keySet());
		Collections.sort(names);
		
		if(names.size()==1) {
			return m.get(names.get(0)).substring(OFFSET_PROFILE.length());
		}
		Object[] values = names.toArray();
		String value = (String) JOptionPane.showInputDialog(null, "Please, choose your profile:", "Profile chooser", 
				JOptionPane.PLAIN_MESSAGE, null, values, names.get(0));
		if(value==null) return null;
		return m.get(value).substring(OFFSET_PROFILE.length());
	}
	
	/*
	 * IS PROFILE
	 */
	
	public boolean isProfile(String id) {
		return profile!=null && profile.id.equals(id);
	}
}
