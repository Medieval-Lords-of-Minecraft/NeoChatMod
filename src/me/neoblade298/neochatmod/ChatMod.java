package me.neoblade298.neochatmod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.neoblade298.neocore.bukkit.InstanceType;
import me.neoblade298.neocore.bukkit.NeoCore;

public class ChatMod extends JavaPlugin implements Listener {
	File file = null;
	FileConfiguration conf = null;
	public static ArrayList<String> bannedWords = null;
	public static boolean muteGlobal = false;
	
	static List<String> punishCmds = null; 
	private static ChatMod inst;


	public void onEnable() {
		Bukkit.getServer().getLogger().info("NeoChatMod Enabled");
		getServer().getPluginManager().registerEvents(new DefaultListener(), this);
		if (NeoCore.getInstanceType() == InstanceType.HUB) getServer().getPluginManager().registerEvents(new HubListener(), this);
	    this.getCommand("servermute").setExecutor(new Commands(this));

		// Save config if doesn't exist
		file = new File(getDataFolder(), "config.yml");
		if (!file.exists()) {
			saveResource("config.yml", false);
		}
		conf = YamlConfiguration.loadConfiguration(file);
		
		// Load settings
		punishCmds = getConfig().getStringList("punish-commands");
		
		// Load banned words
		bannedWords = (ArrayList<String>) getConfig().getStringList("banned-words");
		
		for (int i = 0; i < bannedWords.size(); i++) {
			bannedWords.set(i, bannedWords.get(i).toUpperCase());
		}
		inst = this;
	}

	public void onDisable() {
		Bukkit.getServer().getLogger().info("NeoChatMod Disabled");
	}
	
	
	public void toggleMute() {
		muteGlobal = !muteGlobal;
	}
	
	public static void handleChat(Cancellable e, Player p, String msg) {
		if (ChatMod.muteGlobal && !p.hasPermission("towny.chat.mod")) {
			p.sendMessage("§4[§c§lMLMC§4] §cThere is currently a server mute!");
			e.setCancelled(true);
			return;
		}
		
		// Check if it contained a curse word
		for (String word : bannedWords) {
			if (msg.toUpperCase().contains(word)) {
				e.setCancelled(true);
				for (String cmd : punishCmds) {
					Bukkit.getScheduler().callSyncMethod(inst, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", p.getName())
							.replaceAll("%word%", word).replaceAll("%msg%", msg)));
				}
				
				for (Player online : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("tutorial.staff.receive")) {
						p.sendMessage("§4[§c§lMLMC§4] §c" + online.getName() + " §7was punished for saying: §c" + msg);
					}
				}
				return;
			}
		}
	}
}