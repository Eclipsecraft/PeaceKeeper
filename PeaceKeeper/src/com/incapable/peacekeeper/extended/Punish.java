package com.incapable.peacekeeper.extended;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import com.incapable.peacekeeper.Main;
import com.incapable.peacekeeper.Player;
import com.incapable.peacekeeper.datahandling.PersistantStorage;

public class Punish implements CommandExecutor {
	private PersistantStorage storage;
	private Main main;

	public Punish(PersistantStorage storage, Main main) {
		this.storage = storage;
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender.hasPermission("peacekeeper.punish")) {
			Player player = null;

			player = storage.getPlayer(args[0]);

			if (player != null) {
				Bukkit.getServer().dispatchCommand(sender,
						"rocket -s " + player.GetName());

				main.getServer()
						.getScheduler()
						.scheduleAsyncDelayedTask(main,
								new FinishCommand(sender, player), 30);

				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	private class FinishCommand implements Runnable {
		private CommandSender sender;
		private Player player;

		public FinishCommand(CommandSender sender, Player player) {
			this.sender = sender;
			this.player = player;
		}

		@Override
		public void run() {
			Bukkit.getServer().dispatchCommand(this.sender,
					"slap -v -s  " + this.player.GetName());

			Bukkit.getPlayer(this.player.GetName()).setFireTicks(1800);
			main.getServer().broadcastMessage(
					ChatColor.YELLOW + sender.getName() + " punished "
							+ this.player.GetName() + ".");
		}

	}
}
