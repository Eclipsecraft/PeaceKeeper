package com.incapable.peacekeeper.extended;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import com.incapable.peacekeeper.Main;
import com.incapable.peacekeeper.datahandling.Config;

public class BadWord implements CommandExecutor {
	private Config config;
	private Main main;

	public BadWord(Config config, Main main) {
		this.config = config;
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender.hasPermission("peacekeeper.addbadword")) {
			if (args[0] == "-d") {
				for (String arg : args) {
					config.FilterList.remove(arg);
				}
			} else {
				for (String arg : args) {
					config.FilterList.add(arg);
				}
			}

			main.getConfig().set("Word Filter.Filtered Words",
					config.FilterList);
			main.saveConfig();

			return true;
		} else
			return false;
	}
}
