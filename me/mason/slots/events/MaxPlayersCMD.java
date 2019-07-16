package me.mason.slots.events;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mason.slots.Main;

public class MaxPlayersCMD implements CommandExecutor {
	private final Main core;

	public MaxPlayersCMD(Main core) {
		this.core = core;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("space.maxplayers")) {
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(this.core.cc("&cUsage: /" + label + " <amount> &7(" + Bukkit.getOnlinePlayers().size()
					+ " / " + this.core.getConfig().getInt("SETTINGS.SLOTS") + " players)"));
			return true;
		}
		Player player = (Player) sender;
		try {
			Integer number = Integer.valueOf(Integer.parseInt(args[0]));
			this.core.getConfig().set("SETTINGS.SLOTS", number);
			this.core.saveConfig();
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("space.maxplayers")) {
					players.sendMessage(this.core.cc(this.core.getConfig().getString("MESSAGES.SET-MAX")
							.replace("{sender}", player.getName()).replace("{amount}", String.valueOf(number))));
				}
			}
		} catch (NumberFormatException e) {
			player.sendMessage(
					this.core.cc(this.core.getConfig().getString("MESSAGES.INVALID-INT").replace("{amount}", args[0])));
		}
		return true;
	}
}
