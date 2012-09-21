package com.incapable.peacekeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.incapable.peacekeeper.datahandling.Config;
import com.incapable.peacekeeper.datahandling.PersistantStorage;
import com.incapable.peacekeeper.datahandling.SetConfig;


public class Cmd implements CommandExecutor {

	private Config config;
	private PersistantStorage storage;
	private Main main;

	public Cmd(Config config, PersistantStorage storage, Main main) {
		this.config = config;
		this.storage = storage;
		this.main = main;
	}

	public Logger logger = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length >= 1) {
			if ((args[0].equalsIgnoreCase("setconfig"))) {
				if (sender.hasPermission("peacekeeper.setconfig")) {
					if(!args[1].isEmpty() && !args[2].isEmpty())
					{
						SetConfig setConfig = new SetConfig(config, main, storage);
						if(setConfig.set(args[1], args[2]))
							sender.sendMessage("The config parameter '" + args[1] + "' has been updated to '" + args[2] + "' and the config has been saved");
						else
							sender.sendMessage("The config parameter '" + args[1] + "' could not be updated, probably wrong parameter or wrong value type.");
					}
				}
			}
			if ((args[0].equalsIgnoreCase("getconfig"))) {
				if (sender.hasPermission("peacekeeper.getconfig")) {
					sender.sendMessage("Current config:");
					config.GetConfig(sender);
				}
			}
			if ((args[0].equalsIgnoreCase("unban"))
					|| (args[0].equalsIgnoreCase("pardon"))) {
				if (sender.hasPermission("peacekeeper.unban")) {
					if (args.length >= 2) {
						if (storage.doesContain(args[1])) {
							Player player = storage.getPlayer(args[1]);
							if (player.IsBanned()) {
								player.SetIsBanned(false);
								sender.sendMessage(player.GetName()
										+ " has been unbanned!");
								this.logger.info(": " + player.GetName()
										+ " was unbanned by "
										+ sender.getName());
							} else {
								sender.sendMessage("That player has been verified, but is not banned.");
							}
						} else {
							sender.sendMessage("That player does not exist!");
						}
					} else
						sender.sendMessage("Use this instead: /pk unban <username>");
				} else {
					sender.sendMessage(ChatColor.RED
							+ "You do not have permission to do that.");
				}

			} else if (args[0].equalsIgnoreCase("resetplayer")) {
				if (sender.hasPermission("peacekeeper.resetplayer")) {
					if (args.length >= 2) {
						if (storage.doesContain(args[1])) {
							Player player = storage.getPlayer(args[1]);
							storage.resetPlayerData(player);
							sender.sendMessage(player.GetName()
									+ "'s data has been reset!");
							this.logger.info(": " + player.GetName()
									+ " was reset by " + sender.getName());
						} else {
							sender.sendMessage("That player does not exist!");
						}
					} else
						sender.sendMessage("Use this instead: /ph resetPlayer <username>");
				} else {
					sender.sendMessage(ChatColor.RED
							+ "You do not have permission to do that.");
				}

			} else if ((args[0].equalsIgnoreCase("info"))
					|| (args[0].equalsIgnoreCase("version"))
					|| (args[0].equalsIgnoreCase("about"))) {
				sendPluginInfo(sender);
			} else if (args[0].equalsIgnoreCase("printdata")) {
				if (sender.hasPermission("peacekeeper.printdata")) {
					this.logger.info(": " + sender.getName()
							+ " has initiated a data print.");
					try {
						storage.printPlayerData();
						sender.sendMessage("Data has been printed to file!");
					} catch (IOException e) {
						sender.sendMessage("Failed to print data to file!");
						this.logger
								.severe(" failed to print data to file! Error: "
										+ e.getMessage());
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "You do not have permission to do that.");
				}

			} else if (args[0].equalsIgnoreCase("getdata")) {
				if (sender.hasPermission("peacekeeper.getdata")) {
					if (args.length >= 2) {
						if (storage.doesContain(args[1])) {
							Player player = storage.getPlayer(args[1]);
							sender.sendMessage("---Data for "
									+ player.GetName() + "---");
							sender.sendMessage("");
							sender.sendMessage("Flood warnings: "
									+ player.GetFloodWarnings());
							sender.sendMessage("Total flood warnings: "
									+ player.GetTotalFloods());
							sender.sendMessage("Total flood kicks: "
									+ player.GetTotalFloodKicks());
							sender.sendMessage("Warnings: "
									+ player.GetWarnings());
							sender.sendMessage("Total warnings: "
									+ player.GetTotalWarnings());
							sender.sendMessage("Kicks: "
									+ player.GetKickWarnings());
							sender.sendMessage("Total kicks: "
									+ player.GetTotalKicks());
							sender.sendMessage("CoolDown warnings: "
									+ player.GetCooldownWarnings());
							sender.sendMessage("Banned: " + player.IsBanned());
							sender.sendMessage("________________________________________");
							this.logger.info(": " + sender.getName()
									+ " has gotten data for "
									+ player.GetName());
						} else {
							sender.sendMessage("That player does not exist!");
						}
					} else
						sender.sendMessage("Use this instead: /pk getData <username>");
				} else {
					sender.sendMessage(ChatColor.RED
							+ "You do not have permission to do that.");
				}

			} else if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("peacekeeper.reload")) {
					config.load(main, storage);
				} else {
					sender.sendMessage(ChatColor.RED
							+ "You do not have permission to do that.");
				}

			} else {
				sendCommands(sender);
			}
		} else
			sendCommands(sender);

		return true;
	}

	public void sendCommands(CommandSender sender) {
		ArrayList<String> commands = new ArrayList<String>();

		if (sender.hasPermission("peacekeeper.unban"))
			commands.add("unban");
		if (sender.hasPermission("peacekeeper.resetplayer"))
			commands.add("resetPlayer");
		if (sender.hasPermission("peacekeeper.printdata"))
			commands.add("printData");
		if (sender.hasPermission("peacekeeper.getdata"))
			commands.add("getData");
		if (sender.hasPermission("peacekeeper.reload")) {
			commands.add("reload");
		}
		if (sender.hasPermission("peacekeeper.setconfig")) {
			commands.add("setconfig");
		}
		if (sender.hasPermission("peacekeeper.getconfig")) {
			commands.add("getconfig");
		}

		if (!commands.isEmpty()) {
			StringBuffer commandsToSend = new StringBuffer();
			for (Object command : commands) {
				if (commands.indexOf(command) == 0)
					commandsToSend.append(command);
				else
					commandsToSend.append(", " + command);
			}
			sender.sendMessage(ChatColor.RED + "PeaceKeeper:"
					+ ChatColor.DARK_AQUA + " Accepted commands include:");
			sender.sendMessage(ChatColor.DARK_PURPLE
					+ commandsToSend.toString() + ", about");
		} else {
			sendPluginInfo(sender);
		}
	}

	public void sendPluginInfo(CommandSender sender) {
		PluginDescriptionFile pdfFile = main.getDescription();
		sender.sendMessage("---" + ChatColor.RED + "PeaceKeeper"
				+ ChatColor.WHITE + " version " + ChatColor.AQUA
				+ pdfFile.getVersion() + ChatColor.WHITE + "---");
		sender.sendMessage("Created By: " + ChatColor.GOLD
				+ (String) pdfFile.getAuthors().get(0));
		sender.sendMessage(ChatColor.GREEN + pdfFile.getWebsite());
	}
}