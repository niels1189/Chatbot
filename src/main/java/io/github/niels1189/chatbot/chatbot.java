package io.github.niels1189.chatbot;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
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
	protected ListStore VipList;
	
	@Override
	public void onEnable() {
		PluginDescriptionFile p = this.getDescription();
		getLogger().log(Level.INFO, "Chat Bot Plugin Version " + p.getVersion() + " Enabled");
		getServer().getPluginManager().registerEvents(this, this);
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		this.VipList = new ListStore(new File(pluginFolder + File.separator + "Viplist.txt"));
		this.VipList.load();
		
	}
 
	@Override
	public void onDisable() {
		saveDefaultConfig();
		this.VipList.save();
		getLogger().log(Level.INFO, "Chat Bot Plugin Disabled");
	}
	
	
	
	
	public class ListStore {
		private File storageFile;
		private ArrayList<String> values;
		
		public ListStore(File file){
			this.storageFile = file;
			this.values = new ArrayList<String>();
			
			if (this.storageFile.exists() == false) {
				try {
					this.storageFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		public void load() {
			try {
				DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
				BufferedReader reader = new BufferedReader (new InputStreamReader(input));
				String line;
				
				while ((line = reader.readLine()) != null) {
					if (this.values.contains(line) == false) {
						this.values.add(line);
					}
				}
				
				reader.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void save() {
			try {
				FileWriter stream = new FileWriter(this.storageFile);
				BufferedWriter out = new BufferedWriter(stream);
				for (String value : this.values) {
					out.write(value);
					out.newLine();
				}
				
				out.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void add(String value) {
			this.values.add(value);
		}
		
		public boolean contains(String value) {
			return this.values.contains(value);
		}
		
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
			p.sendMessage("[§6ChatBot§f] §a!umad - Feeling a bit mad?");
			p.sendMessage("[§6ChatBot§f] §a!vip - List of online VIP's");
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
		
		
		
		if (message.equalsIgnoreCase("!vip")) {
			String Servername = getConfig().getString("ServerName");
			p.sendMessage("[§6ChatBot§f] §4List of VIP's on " + Servername);
			int VipCount = this.VipList.values.size();
			p.sendMessage("[§6ChatBot§f] §cVIP Count: " + VipCount);
			for (Player p2: Bukkit.getServer().getOnlinePlayers()) {
				if (p2.hasPermission(getConfig().getString("VIPpermission"))) {
					if(!this.VipList.contains(p2.getName())){
						this.VipList.add(p2.getName());
						this.VipList.save();
					}
				}
			}
			for (String value : this.VipList.values) {
				if(getServer().getPlayer(value) != null){
    				p.sendMessage("[§6ChatBot§f] §a- " + ChatColor.GREEN + value);
    			}else{
    				p.sendMessage("[§6ChatBot§f] §a- " + ChatColor.RED + value);
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
		chat.setCancelled(true);
	}
	
	
	
	
	
	
	

public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
	Player p = (Player) s;
	
	
	if (cmd.getName().equalsIgnoreCase("chatbot"))	{
		if (args.length == 1 && args[0].equalsIgnoreCase("reload") && p.hasPermission("chatbot.reload")) {
			this.reloadConfig();
			this.saveConfig();
			String pluginFolder = this.getDataFolder().getAbsolutePath();
			this.VipList = new ListStore(new File(pluginFolder + File.separator + "Viplist.txt"));
			this.VipList.load();
			Bukkit.getLogger().info("[ChatBot] Config Reloaded!");
			p.sendMessage("[§6ChatBot§f] §aConfig Reloaded!");
		}
		else {
			p.sendMessage(ChatColor.GREEN + "Chatbot " + ChatColor.RED + "is up and working!");
			p.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GREEN + "1.2");
			p.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.GREEN + ChatColor.ITALIC + "Niels1189");
			p.sendMessage(ChatColor.GOLD + "Try using !help");
		}
	}
	return false;
}



}