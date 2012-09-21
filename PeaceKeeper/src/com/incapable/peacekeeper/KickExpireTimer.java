package com.incapable.peacekeeper;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;

import com.incapable.peacekeeper.datahandling.Config;
import com.incapable.peacekeeper.datahandling.PersistantStorage;


public class KickExpireTimer {
	private Timer timer;
	private Player playerToSet;
	private PersistantStorage storage;

	public KickExpireTimer(Config config, PersistantStorage storage) {
		this.storage = storage;
		this.timer = new Timer();
	}

	public void run(int minutes, Player spamPlayer) {
		this.timer.schedule(new RemindTask(this), minutes * 1000 * 60);
		this.playerToSet = spamPlayer;
	}

	class RemindTask extends TimerTask {
		private KickExpireTimer e;

		public RemindTask(KickExpireTimer e) {
			this.e = e;
		}

		public void run() {
			if(Bukkit.getServer().getOfflinePlayer(e.playerToSet.GetName()).isOnline()) {
				
			}
			try
			{
				storage.resetPlayerData(e.playerToSet);
				Bukkit.broadcastMessage("PeaceKeeper: removed kick warnings from "
						+ e.playerToSet.GetName() + " because they have expired.");
			}
			catch(Exception ex)
			{
				Bukkit.broadcastMessage("Non fatal error in peacekeeper kickexpiretimer: " + ex.getMessage());
			}
		}
	}
}
