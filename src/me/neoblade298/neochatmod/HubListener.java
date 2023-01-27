package me.neoblade298.neochatmod;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.neoblade298.neocore.bukkit.util.Util;


public class HubListener implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPlayedBefore()) {
			Util.msg(p, "You are muted until choosing a server! Use your compass or enter a portal!");
			e.setCancelled(true);
			return;
		}
	}
}
