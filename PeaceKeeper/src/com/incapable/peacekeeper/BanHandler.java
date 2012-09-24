package com.incapable.peacekeeper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.incapable.peacekeeper.datahandling.Config;
import com.incapable.peacekeeper.datahandling.PersistantStorage;


public class BanHandler {
	private Config config;
	private PersistantStorage storage;
	private Main main;
	private static KickExpireTimer kickExpireTimer;

	public BanHandler(Config config, PersistantStorage storage, Main main) {
		this.config = config;
		this.storage = storage;
		this.main = main;
	}

	public void kick(Player player, String reason) {
		if (player.GetKickWarnings() >= config.kicksBeforeBan && config.enableTempBan) {
			int banMinutes = config.tempBanStartMinutes;
			int multiplier = (player.GetTempBans() * config.tempBanScale);
			
			if(player.GetTempBans() <= config.tempBanMaxStep)
				player.AddTempBan();
			
			if(player.GetTempBans() > config.tempBanMaxStep && config.enableBan)
				ban(player);
			else
			{			
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
					"tempban " + player.GetName() + " " + (banMinutes * multiplier) + " minutes spamming");
			}
		} else {
			Bukkit.getPlayer(player.GetName()).kickPlayer(
					"PeaceKeeper: " + reason);
			sendToPlayersWithPermission(ChatColor.RED + "[PeaceKeeper]: "
					+ ChatColor.BLUE + player.GetName()
					+ " was kicked for spamming!");
			player.AddKickWarning();
			player.ResetWarnings();
			player.ResetMessageCount();
			player.ResetCoolDownWarnings();
			kickExpireTimer = new KickExpireTimer(config, storage);
			kickExpireTimer.run(config.kickExpireTime, player);
		}
	}

	public void ban(Player player) {
		if(config.enableBan)
		{
			String bannedMsg = main.getConfig()
					.getString("Custom ban message");
			Bukkit.getPlayer(player.GetName()).kickPlayer(bannedMsg);
			player.SetIsBanned(true);
			sendToPlayersWithPermission(ChatColor.RED + "[PeaceKeeper]: "
					+ ChatColor.BLUE + player.GetName()
					+ " was banned for spamming!");
			storage.resetPlayerData(player);
			player.ResetReputation();
			storage.savePlayerMap(false);
		}
	}

	public void floodKick(Player player) {
		if (player.GetKickWarnings() >= config.kicksBeforeBan && config.enableTempBan) {
			int banMinutes = config.tempBanStartMinutes;
			int multiplier = (player.GetTempBans() * config.tempBanScale);
			
			if(player.GetTempBans() <= config.tempBanMaxStep)
				player.AddTempBan();			
			if(player.GetTempBans() > config.tempBanMaxStep && config.enableBan)
				ban(player);
			else
			{			
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
					"tempban " + player.GetName() + " " + (banMinutes * multiplier) + " minutes FlooooOOOooodiiiiIIIiiiing");
			}
		} else {
			Bukkit.getPlayer(player.GetName()).kickPlayer(
					"PeaceKeeper: FlooooOOOooodiiiiIIIiiiing");
			player.AddFloodKickWarning();
			player.ResetWarnings();
			player.ResetMessageCount();
			player.ResetCoolDownWarnings();
			Bukkit.broadcastMessage("[PeaceKeeper] Kicked " + player.GetName()
					+ " for flooding(" + player.GetKickWarnings() + "/"
					+ config.kicksBeforeBan + ")");

			kickExpireTimer = new KickExpireTimer(config, storage);
			kickExpireTimer.run(config.kickExpireTime, player);

		}
	}

	public void floodBan(Player player) {
		if(config.enableBan)
		{
			String bannedMsg = "You flooded waaaaaaaaaaay too much";
			Bukkit.getPlayer(player.GetName()).kickPlayer(bannedMsg);
			player.SetIsBanned(true);
			sendToPlayersWithPermission(ChatColor.RED + "[PeaceKeeper]: "
					+ ChatColor.BLUE + player.GetName()
					+ " was banned for flooding!");
			storage.resetPlayerData(player);
			player.ResetReputation();
			storage.savePlayerMap(false);
		}
	}

	public void sendToPlayersWithPermission(String message) {
		for (org.bukkit.entity.Player player : main.getServer()
				.getOnlinePlayers()) {
			if (player.hasPermission("peacekeeper.recievemessages")) {
				player.sendMessage(message);
			}
		}
		Bukkit.broadcastMessage(message);
	}
	
	public void warnPlayer(Player player, String reason, Boolean addWarning) {
		if (addWarning.booleanValue())
			player.AddWarning();
		Bukkit.getPlayer(player.GetName()).sendMessage(
				ChatColor.RED + "[PeaceKeeper]: " + ChatColor.BLUE + reason);
		if (addWarning.booleanValue()) {
			Bukkit.getPlayer(player.GetName()).sendMessage(
					ChatColor.RED + "[PeaceKeeper]: " + ChatColor.BLUE
							+ "You have recieved " + player.GetWarnings() + "/"
							+ config.warningsToKick + " warnings.");
		}
		if (player.GetKickWarnings() >= config.kicksBeforeBan)
			ban(player);
		else if (player.GetWarnings() >= config.warningsToKick)
			kick(player, "You were kicked for recieving "
					+ config.warningsToKick + " warnings!");
	}
}
