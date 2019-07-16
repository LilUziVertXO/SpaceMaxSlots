package me.mason.slots.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import me.mason.slots.Main;

public class MaxPlayersListener implements Listener {
	private final Main core;

	public MaxPlayersListener(Main core) {
		this.core = core;
	}

	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		event.setMaxPlayers(this.core.getConfig().getInt("SETTINGS.SLOTS"));
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (Bukkit.getServer().getOnlinePlayers().size() >= this.core.getConfig().getInt("SETTINGS.SLOTS")) {
			if (event.getPlayer().hasPermission("space.maxplayers")) {
				event.allow();
			} else {
				event.disallow(PlayerLoginEvent.Result.KICK_FULL,
						this.core.cc(this.core.getConfig().getString("MESSAGES.KICK")));
			}
		} else if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
			event.allow();
		}
	}
}
