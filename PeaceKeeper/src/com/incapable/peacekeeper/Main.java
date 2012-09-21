package com.incapable.peacekeeper;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.incapable.peacekeeper.datahandling.Config;
import com.incapable.peacekeeper.datahandling.PersistantStorage;
import com.incapable.peacekeeper.extended.BadWord;
import com.incapable.peacekeeper.extended.Punish;


public class Main extends JavaPlugin {
	private static Main plugin;
	private Config config;
	private PersistantStorage storage;
	Timer MessageCountTimer;
	Timer SaveTimer;


	public void onDisable() {
		Bukkit.broadcastMessage("Peacekeeper is now disabled.");
		storage.savePlayerMap(true);
	}

	public void onEnable() {
		config = new Config();
		storage = new PersistantStorage(this);
		config.load(this, storage);
		config.banHandler = new BanHandler(config, storage, this);
		config.FilterList = this.getConfig().getStringList("Word Filter.Filtered Words");

		MessageCountTimer = new Timer();
		SaveTimer = new Timer();

		Main.plugin = this;
		

		config.playerListener = new PlayerListener(storage, config, this);

		plugin.getServer().getPluginManager()
				.registerEvents(config.playerListener, plugin);		
		
		getCommand("pk").setExecutor(new Cmd(config, storage, this));
		getCommand("punish").setExecutor(new Punish(storage, this));
		getCommand("addbadword").setExecutor(new BadWord(config, this));
		
		Bukkit.broadcastMessage("Peacekeeper version 1.2.2 is now enabled.");
		
		this.MessageCountTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (!storage.getAll().isEmpty()) {
					Map<String, Player> map = storage.getAll();
					for (Object objSpamPlayer : map.values()) {
						Player player = (Player) objSpamPlayer;
						if ((player.GetMessageCount() >= config.mpsKick)
								&& (!Bukkit.getPlayer(player.GetName())
										.hasPermission("peacekeeper.ignore"))) {
							int KicksToBan = Main.plugin.getConfig().getInt("Kicks before ban");
							if (player.GetKickWarnings() >= KicksToBan && config.enableBan)
								config.banHandler.ban(player);
							else
								config.banHandler.kick(player,
										"You were kicked for sending more than "
												+ config.mpsKick
												+ " messages in "
												+ config.mpsTime + " seconds!");
						} else {
							player.ResetMessageCount();
						}
					}
				}
			}
		}, config.mpsTime * 1000, config.mpsTime * 1000);
		this.SaveTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				storage.savePlayerMap(false);
				config.logger
						.info("PeaceKeeper: Saving and printing stats to file...");
			}
		}, config.saveTime * 1000 * 60, config.saveTime * 1000 * 60);
	}
}
