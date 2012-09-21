package com.incapable.peacekeeper;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 5122491982621077862L;
	private String name;
	private Boolean canTalk;
	private int warnings;
	private int kickwarnings;
	private int coolDownWarnings;
	private int floodWarnings;
	private int totalFloods;
	private int totalWarnings;
	private int totalKicks;
	private int totalFloodKicks;
	private int totalRepeats;
	private int tempBans;
	private String lastMessage;
	private int repeats;
	private int MessageCount;
	private Boolean isBanned;
	private Boolean canUseCmd;
	private int reputation;
	private long lastFlood;

	public boolean canPlayerChat(Player player) {
		return this.canTalk.booleanValue();
	}

	public Player(String name) {
		this.name = name;
		this.canTalk = true;
		this.warnings = 0;
		this.kickwarnings = 0;
		this.repeats = 0;
		this.MessageCount = 0;
		this.coolDownWarnings = 0;
		this.isBanned = false;
		this.canUseCmd = true;
		this.reputation = 0;
		this.tempBans = 0;
		this.lastFlood = System.currentTimeMillis();
	}

	// Get methods
	public String GetName() {
		return this.name;
	}

	public Boolean CanTalk() {
		return this.canTalk;
	}

	public int GetWarnings() {
		return this.warnings;
	}

	public int GetKickWarnings() {
		return this.kickwarnings;
	}

	public int GetCooldownWarnings() {
		return this.coolDownWarnings;
	}

	public int GetFloodWarnings() {
		return this.floodWarnings;
	}

	public String GetLastMessage() {
		return this.lastMessage;
	}

	public int GetRepeats() {
		return this.repeats;
	}

	public int GetMessageCount() {
		return this.MessageCount;
	}

	public Boolean IsBanned() {
		return this.isBanned;
	}

	public Boolean CanUseCmd() {
		return this.canUseCmd;
	}

	public int GetReputation() {
		return this.reputation;
	}

	public long GetLastFlood() {
		return this.lastFlood;
	}
	
	public int GetTotalFloods() {
		return this.totalFloods;
	}
	
	public int GetTotalWarnings() {
		return this.totalWarnings;
	}
	
	public int GetTotalKicks() {
		return this.totalKicks;
	}
	
	public int GetTotalFloodKicks() {
		return this.totalFloodKicks;
	}
	
	public int GetTotalRepeats() {
		return this.totalRepeats;
	}
	
	public int GetTempBans(){
		return this.tempBans;
	}

	// Set methods
	public void SetCanTalk(Boolean canTalk) {
		this.canTalk = canTalk;
	}

	public void AddWarning() {
		this.warnings++;
		this.totalWarnings++;
	}

	public void ResetWarnings() {
		this.warnings = 0;
	}

	public void AddKickWarning() {
		this.kickwarnings++;
		this.totalKicks++;
	}
	
	public void AddFloodKickWarning() {
		this.kickwarnings++;
		this.totalFloodKicks++;
	}
	
	public void AddFloodWarning() {
		this.floodWarnings++;
		this.totalFloods++;
	}
	
	public void ResetFloodWarnings() {
		this.floodWarnings = 0;
	}

	public void ResetKickWarnings() {
		this.kickwarnings = 0;
	}

	public void AddCoolDownWarning() {
		this.coolDownWarnings++;
	}

	public void ResetCoolDownWarnings() {
		this.coolDownWarnings = 0;
	}

	public void SetLastMessage(String message) {
		this.lastMessage = message;
	}

	public void AddRepeats() {
		this.repeats++;
		this.totalRepeats++;
	}

	public void ResetRepeats() {
		this.repeats = 0;
	}

	public void AddMessageCount() {
		this.MessageCount++;
	}

	public void ResetMessageCount() {
		this.MessageCount = 0;
	}

	public void SetIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public void SetCanUseCmd(Boolean canUse) {
		this.canUseCmd = canUse;
	}

	public void AddReputation() {
		this.reputation++;
	}

	public void ResetReputation() {
		this.reputation = 0;
	}

	public void SetLastFlood() {
		this.lastFlood = System.currentTimeMillis();
	}
	
	public void AddTempBan(){
		this.tempBans++;
	}
	
	public void ResetTempBans(){
		this.tempBans = 0;
	}
}