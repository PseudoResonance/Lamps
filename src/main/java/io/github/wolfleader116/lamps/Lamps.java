package io.github.wolfleader116.lamps;

import java.util.HashMap;

import io.github.wolfleader116.lamps.commands.LampsC;
import io.github.wolfleader116.lamps.tabcompleters.LampsTC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Lamps extends JavaPlugin implements Listener {

	public static HashMap<String, Boolean> night = new HashMap<String, Boolean>();
	public static HashMap<String, Boolean> lamp = new HashMap<String, Boolean>();

	@Override
	public void onEnable() {
		getCommand("lamps").setExecutor(new LampsC());
		getCommand("lamps").setTabCompleter(new LampsTC());
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		plugin = this;
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Config c = new Config("lamps", Lamps.plugin);
				for (String s : c.getConfig().getConfigurationSection("night").getKeys(false)) {
					if (c.getConfig().getBoolean("night." + s)) {
						String[] data = s.split(",");
						try {
							Location l = new Location(Bukkit.getServer().getWorld(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3]));
							if (l.getWorld().getTime() >= 12000 && l.getWorld().getTime() < 24000) {
								l.getBlock().setType(Material.REDSTONE_LAMP_ON);
							} else {
								l.getBlock().setType(Material.REDSTONE_LAMP_OFF);
							}
						} catch (NullPointerException e) {
							c.getConfig().set("night." + s, null);
							c.save();
						}
					}
				}
			}
		}, 300, 300);
	}

	@Override
	public void onDisable() {
		plugin = null;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Config c = new Config("lamps", Lamps.plugin);
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.REDSTONE_LAMP_OFF || e.getClickedBlock().getType() == Material.REDSTONE_LAMP_ON) {
			Location l = e.getClickedBlock().getLocation();
			try {
				if (lamp.get(p.getName())) {
					if (c.getConfig().getBoolean("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
						p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "This lamp is already a registered lamp!");
					} else {
						if (c.getConfig().getBoolean("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
							c.getConfig().set("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), false);
							c.getConfig().set("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), true);
							c.save();
							p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Set lamp to always on.");
							e.getClickedBlock().setType(Material.REDSTONE_LAMP_ON);
						} else {
							c.getConfig().set("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), true);
							c.save();
							p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Set lamp to always on.");
							e.getClickedBlock().setType(Material.REDSTONE_LAMP_ON);
						}
					}
				} else if (night.get(p.getName())) {
					if (c.getConfig().getBoolean("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
						p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "This lamp is already a registered night lamp!");
					} else {
						if (c.getConfig().getBoolean("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
							c.getConfig().set("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), false);
							c.getConfig().set("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), true);
							c.save();
							p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Set lamp to on at night.");
						} else {
							c.getConfig().set("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), true);
							c.save();
							p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Set lamp to on at night.");
						}
					}
				}
			} catch (NullPointerException ex) {
				try {
					if (night.get(p.getName())) {
						if (c.getConfig().getBoolean("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
							p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "This lamp is already a registered night lamp!");
						} else {
							if (c.getConfig().getBoolean("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
								c.getConfig().set("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), false);
								c.getConfig().set("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), true);
								c.save();
								p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Set lamp to on at night.");
							} else {
								c.getConfig().set("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), true);
								c.save();
								p.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Set lamp to on at night.");
							}
						}
					}
				} catch (NullPointerException exx) {}
			}
		}
	}

	@EventHandler
	public void onLamp(BlockRedstoneEvent e) {
		if (e.getBlock().getType() == Material.REDSTONE_LAMP_OFF || e.getBlock().getType() == Material.REDSTONE_LAMP_ON) {
			Config c = new Config("lamps", Lamps.plugin);
			Location l = e.getBlock().getLocation();
			if (c.getConfig().getBoolean("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
				e.setNewCurrent(100);
				e.getBlock().setType(Material.REDSTONE_LAMP_ON);
			} else if (c.getConfig().getBoolean("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
				if (l.getWorld().getTime() >= 12000 && l.getWorld().getTime() < 24000) {
					e.setNewCurrent(100);
					e.getBlock().setType(Material.REDSTONE_LAMP_ON);
				} else {
					e.setNewCurrent(0);
					e.getBlock().setType(Material.REDSTONE_LAMP_OFF);
				}
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Location l = e.getBlock().getLocation();
		Config c = new Config("lamps", Lamps.plugin);
		if (c.getConfig().contains("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ()) || c.getConfig().contains("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ())) {
			c.getConfig().set("lamps." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), null);
			c.getConfig().set("night." + l.getWorld() + "," + l.getX() + "," + l.getY() + "," + l.getZ(), null);
			c.save();
		}
	}

	public static Lamps plugin;

}
