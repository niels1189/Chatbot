package io.github.niels1189.chatbot;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class chatbot extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		PluginDescriptionFile p = this.getDescription();
		getLogger().log(Level.INFO, "Chat Bot Plugin Version " + p.getVersion() + " Enabled");
		getServer().getPluginManager().registerEvents(this, this);
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
 
	@Override
	public void onDisable() {
		saveDefaultConfig();
		getLogger().log(Level.INFO, "Chat Bot Plugin Disabled");
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat (AsyncPlayerChatEvent chat){
		final Player p = chat.getPlayer();
		String message = chat.getMessage();
		String checkmessage = chat.getMessage().toLowerCase();
		
		
		if (checkmessage.contains("niels") || checkmessage.contains("neils") || checkmessage.contains("niel") || checkmessage.contains("neil"))
		{
			if (checkmessage.contains("why") && checkmessage.contains("admin")) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new BukkitRunnable(){
					public void run(){
		            	p.sendMessage("[§6ChatBot§f]" + ChatColor.DARK_AQUA + " Niels chose to be admin because people");
		            	p.sendMessage("[§6ChatBot§f]" + ChatColor.DARK_AQUA + " didn't always listen to admins.");
		            	p.sendMessage("[§6ChatBot§f]" + ChatColor.DARK_AQUA + " With Niels as admin, people will have to listen");
		            	p.sendMessage("[§6ChatBot§f]" + ChatColor.DARK_AQUA + " to other admins & staff as well.");
					}
				}, 5);
			}
		}
		
		if (checkmessage.contains("how") || checkmessage.contains("can"))
	  	{
			if (checkmessage.contains("purchase") || checkmessage.contains("have") || checkmessage.contains("give") || checkmessage.contains("buy") || checkmessage.contains("get")) {
				if (checkmessage.contains("vip")) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new BukkitRunnable(){
						public void run(){
							p.sendMessage("[§6ChatBot§f]" + ChatColor.DARK_AQUA + " You can purchase VIP at the store (/store)");
						}
					}, 5);
				}
			}
		  
			if (checkmessage.contains("set") && checkmessage.contains("home"))
				{
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new BukkitRunnable(){
						public void run(){
							p.sendMessage("[§6ChatBot§f]" + ChatColor.DARK_AQUA + " You can set your home using /sethome");
						}
					}, 5);
				}
	  	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if (message.equalsIgnoreCase("!help")) {
			p.sendMessage("[§6ChatBot§f] §4Bot Command list");
			p.sendMessage("[§6ChatBot§f] §a!help - List Commands");
			p.sendMessage("[§6ChatBot§f] §a!staff - List of Staff members");
			p.sendMessage("[§6ChatBot§f] §a!btc - Current Value of Bitcoins");
			p.sendMessage("[§6ChatBot§f] §c!vip - List of online VIP's");
		}
		
		
		if (message.equalsIgnoreCase("!staff")) {
			List<String> staffList = getConfig().getStringList("Staff");
			//String[] staffMembers = staffList.toArray(new String[0]);
			String Servername = getConfig().getString("ServerName");
			p.sendMessage("[§6ChatBot§f] §4List of Staff on " + Servername);
			p.sendMessage("[§6ChatBot§f] §cStaff Count: " + staffList.size());
			
	        for (String entry : staffList) {
	        	if (getConfig().getBoolean("CheckOnline")) {
	        		if (getConfig().getBoolean("UseRanks")) {
	        			if (entry.contains(getConfig().getString("SplitRanks"))) {
	        				String[] parts = entry.split("\\" + getConfig().getString("SplitRanks"));
	        				parts[1] = parts[1].replaceAll("\\s+","");
	        				if(getServer().getPlayer(parts[1]) != null){
	        					p.sendMessage("[§6ChatBot§f] §a- " + ChatColor.GOLD + parts[0] + getConfig().getString("SplitRanks") + ChatColor.GREEN + " " + parts[1]);
	        				} else {
	        					p.sendMessage("[§6ChatBot§f] §a- " + ChatColor.GOLD + parts[0] + getConfig().getString("SplitRanks") + ChatColor.RED + " " + parts[1]);
	        				}
	        			}	
	        		} else {
	        		
	        			if(getServer().getPlayer(entry) != null){
	        				p.sendMessage("[§6ChatBot§f] §a- " + ChatColor.GREEN + entry);
	        			}else{
	        				p.sendMessage("[§6ChatBot§f] §a- " + ChatColor.RED + entry);
	        			}
	        		}
	        	}
	        	else {
	        		p.sendMessage("[§6ChatBot§f] §a- " + ChatColor.GOLD + entry);
	        	}
	        }
	        chat.setCancelled(true);
		}
		
		if (message.equalsIgnoreCase("!umad")) {
		 	// Browse a URL, say google.com
		 	p.sendMessage("http://www.williamoconnor.me/umad/");
		 	
		}
		
		if (message.equalsIgnoreCase("!btc")) {
			try {
				URL yahoo = new URL("https://www.bitstamp.net/api/ticker/");
				URLConnection yc = yahoo.openConnection();
				BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
				String inputLine;
	        
				while ((inputLine = in.readLine()) != null) {
					
					String[] parts = inputLine.split(",");
					String[] BTCValue = parts[1].split("\"");
					p.sendMessage(ChatColor.GOLD + "1 BTC is worth " + BTCValue[3] + "$");
					
				}
			} catch (IOException e1) {
		        e1.printStackTrace();
		    }
			
			
		}
	
	
	
	}
	
	
	
	
	
	
	

public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
	if (cmd.getName().equalsIgnoreCase("chatbot"))	{
		
	Player p = (Player) s;
	p.sendMessage(ChatColor.RED + "Chatbot is up and working!");
	p.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GREEN + "1.1");
	p.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.GREEN + ChatColor.ITALIC + "Niels1189");
	p.sendMessage(ChatColor.GOLD + "Try using !help");
	}
	return false;
}



}