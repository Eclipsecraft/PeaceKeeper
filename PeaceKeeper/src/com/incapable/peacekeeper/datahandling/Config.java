package com.incapable.peacekeeper.datahandling;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

import com.incapable.peacekeeper.BanHandler;
import com.incapable.peacekeeper.Main;
import com.incapable.peacekeeper.PlayerListener;

public class Config {
	public Logger logger;
	public PlayerListener playerListener;
	public int mpsTime;
	public int mpsKick;
	public int messageCoolDownTime;
	public int allowedRepeats;
	public boolean enableCapsBlocking;
	public boolean enableFloodBlocking;
	public boolean enableFloodPatternBlocking;
	public boolean countSpecialAsCaps;
	public int disallowPercent;
	public int warningsToKick;
	public int floodWarningsToKick;
	public int kicksBeforeBan;
	public String bannedMessage;
	public boolean messageCoolDownEnabled;
	public List<String> FilterList;
	public int coolDownWarningsToKick;
	public boolean commandCoolDownEnabled;
	public int commandCoolDownTime;
	public boolean enableCensoring;
	public boolean giveWarningForBlock;
	public boolean coolDownKickingEnabled;
	public boolean enableBan;
	public int saveTime;
	public int kickExpireTime;
	public boolean filterEnabled;
	public int startCounting;
	public int tempBanScale;
	public int tempBanMaxStep;
	public int tempBanStartMinutes;
	public BanHandler banHandler;

	public Config() {
		this.logger = Logger.getLogger("Minecraft");
	}

	public void load(Main main, PersistantStorage storage) {
		if(storage == null)
			storage = new PersistantStorage(main);
		
		this.banHandler = new BanHandler(this, storage, main);
		this.playerListener = new PlayerListener(storage, this, main);
		
		this.FilterList = GetStringList(main, "Word Filter.Filtered Words");
		this.mpsTime = GetInt(main, "mpsTime", 3);
		this.mpsKick = GetInt(main, "mpsKick", 4);
		this.messageCoolDownTime = GetInt(main, "messageCoolDownTime", 1);
		this.messageCoolDownEnabled = GetBoolean(main, "messageCoolDownEnabled", true);
		this.allowedRepeats = GetInt(main, "allowedRepeats", 3);;
		this.enableCapsBlocking = GetBoolean(main, "enableCapsBlocking", true);
		this.enableFloodBlocking = GetBoolean(main, "enableFloodBlocking", true);
		this.enableFloodPatternBlocking = GetBoolean(main, "enableFloodPatternBlocking", false);
		this.countSpecialAsCaps = GetBoolean(main, "countSpecialAsCaps", false);
		this.enableBan = GetBoolean(main, "enableBan", true);
		this.disallowPercent = GetInt(main, "disallowPercent", 50);
		this.warningsToKick = GetInt(main, "warningsToKick", 3);
		this.floodWarningsToKick = GetInt(main, "floodWarningsToKick", 5);
		this.kicksBeforeBan = GetInt(main, "kicksBeforeBan", 3);
		this.bannedMessage = GetString(main, "bannedMessage", " was banned by peacekeeper");
		this.coolDownWarningsToKick = GetInt(main, "coolDownWarningsToKick", 3);
		this.commandCoolDownEnabled = GetBoolean(main, "commandCoolDownEnabled", true);
		this.commandCoolDownTime = 1;
		this.giveWarningForBlock = GetBoolean(main, "giveWarningForBlock", true);
		this.coolDownKickingEnabled = GetBoolean(main, "coolDownKickingEnabled", true);
		this.saveTime = GetInt(main, "saveTime", 10);
		this.kickExpireTime = GetInt(main, "kickExpireTime", 5);
		this.filterEnabled = GetBoolean(main, "filterEnabled", true);
		this.startCounting = GetInt(main, "startCounting", 10);
		this.tempBanScale = GetInt(main, "tempBanScale", 3);
		this.tempBanMaxStep = GetInt(main, "tempBanScale", 10);
		this.tempBanStartMinutes = GetInt(main, "tempBanStartMinutes", 10);
		main.saveConfig();
	}
	
	public void GetConfig(CommandSender sender)
	{
		sender.sendMessage("mpsTime: " + this.mpsTime);
		sender.sendMessage("mpsKick: " + this.mpsKick);
		sender.sendMessage("messageCoolDownTime - the time that has to be between messages(seconds): " + this.messageCoolDownTime);
		sender.sendMessage("messageCoolDownEnabled - enables/disables message cooldown: " + this.messageCoolDownEnabled);
		sender.sendMessage("allowedRepeats - the allowed times a message can be repeated: " + this.allowedRepeats);
		sender.sendMessage("enableCapsBlocking - blocks caps messages: " + this.enableCapsBlocking);
		sender.sendMessage("enableFloodBlocking - blocks flood messages: " + this.enableFloodBlocking);
		sender.sendMessage("enableFloodPatternBlocking - enables/disables flood pattern searching: " + this.enableFloodPatternBlocking);
		sender.sendMessage("countSpecialAsCapsk - count special characters(symbols) as caps: " + this.countSpecialAsCaps);
		sender.sendMessage("enableBan - genericly enables/disables banning by peacekeeper: " + this.enableBan);
		sender.sendMessage("disallowPercent - the percentage of capital letters before recognized as caps: " + this.disallowPercent);
		sender.sendMessage("warningsToKick - how many warnings a player gets before he/she gets kicked: " + this.warningsToKick);
		sender.sendMessage("floodWarningsToKick - how many warnings for flooding a player gets before he/she gets kicked: " + this.floodWarningsToKick);
		sender.sendMessage("kicksBeforeBan - how many kicks there has to be before a player gets banned: " + this.kicksBeforeBan);
		sender.sendMessage("coolDownWarningsToKick - how many cooldown warnings a player gets before he/she gets kicked: " + this.coolDownWarningsToKick);
		sender.sendMessage("bannedMessage - the message shown when a player gets banned: " + this.bannedMessage);
		sender.sendMessage("commandCoolDownEnabled - enables/disables cooldowntime on commands: " + this.commandCoolDownEnabled);
		//sender.sendMessage("giveWarningForBlock: " + this.giveWarningForBlock);
		sender.sendMessage("coolDownKickingEnabled - kick on message cooldown: " + this.coolDownKickingEnabled);
		sender.sendMessage("kickExpireTime - the time in seconds before the kick counter gets reset: " + this.kickExpireTime);
		sender.sendMessage("filterEnabled - enables/disables filtering on bad words: " + this.filterEnabled);
		//sender.sendMessage("tempBanScale: " + this.tempBanScale);
		//sender.sendMessage("tempBanMaxStep: " + this.filterEnabled);
		//sender.sendMessage("tempBanStartMinutes: " + this.tempBanStartMinutes);
	}
	
	private boolean GetBoolean(Main main, String param, boolean defaultVal) {
		if(main.getConfig().isSet(param))
			return main.getConfig().getBoolean(param, defaultVal);
		else {
			main.getConfig().set(param, defaultVal);
			return defaultVal;
		}
	}
	
	private String GetString(Main main, String param, String defaultVal) {
		if(main.getConfig().isSet(param))
			return main.getConfig().getString(param, defaultVal);
		else {
			main.getConfig().set(param, defaultVal);
			return defaultVal;
		}
	}
	
	private int GetInt(Main main, String param, int defaultVal) {
		if(main.getConfig().isSet(param))
			return main.getConfig().getInt(param, defaultVal);
		else {
			main.getConfig().set(param, defaultVal);
			return defaultVal;
		}
	}
	
	private List<String> GetStringList(Main main, String param){
		if(main.getConfig().isSet(param))
			return main.getConfig().getStringList(param);
		else {
			return new ArrayList<String>();
		}
	}
}
