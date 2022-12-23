package me.neoblade298.neochatmod;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DefaultListener implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		ChatMod.handleChat(e, e.getPlayer(), e.getMessage());
	}
}
