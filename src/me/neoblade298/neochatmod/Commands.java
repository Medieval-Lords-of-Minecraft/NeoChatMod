package me.neoblade298.neochatmod;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class Commands implements CommandExecutor{
	
	ChatMod main;
	
	public Commands(ChatMod main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (args.length == 0 && sender.hasPermission("mycommand.staff")) {
			ChatMod.muteGlobal = !ChatMod.muteGlobal;
			if (!ChatMod.muteGlobal) {
				Bukkit.broadcastMessage("§4[§c§lMLMC§4] §7The server chat has been unmuted!");
			}
			else {
				for (int i = 0; i < 15; i++) {
					Bukkit.broadcastMessage("");
				}
				Bukkit.broadcastMessage("§4[§c§lMLMC§4] §7The server chat has been muted!");
			}
			return true;
		}
		return false;
	}
}