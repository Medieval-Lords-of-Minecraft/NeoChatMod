package me.neoblade298.neochatmod;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.chatcontrol.api.ChatChannelEvent;

public class TownyListener implements Listener {

	int QUEST_X_BOUND_1 = 11;
	int QUEST_X_BOUND_2 = 41;
	int QUEST_Z_BOUND_1 = 38;
	int QUEST_Z_BOUND_2 = 80;
	@EventHandler(ignoreCancelled=true)
	public void onChat(ChatChannelEvent e) {
		Player sender = (Player) e.getSender();
		String msg = e.getMessage();
		double x = sender.getLocation().getX();
		double z = sender.getLocation().getZ();
		World w = sender.getWorld();
		
		// Check if they're in the tutorial world
		if (w.getName().equalsIgnoreCase("Spawn")) {
			if ((QUEST_X_BOUND_1 <= x && x <= QUEST_X_BOUND_2) &&
			(QUEST_Z_BOUND_1 <= z && z <= QUEST_Z_BOUND_2) &&
			!sender.hasPermission("tutorial.chat.receive")) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("tutorial.chat.receive")) {
						p.sendMessage("§4[§c§lMLMC§4] §c" + sender.getName() + " §7spoke in tutorial: §c" + msg);
					}
				}
				sender.sendMessage("§4[§c§lMLMC§4] §cYou cannot speak in the tutorial, but staff can still hear you!");
				e.setCancelled(true);
			}
		}
		
		ChatMod.handleChat(e, sender, msg);
	}
}
