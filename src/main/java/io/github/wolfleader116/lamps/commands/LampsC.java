package io.github.wolfleader116.lamps.commands;

import io.github.wolfleader116.lamps.Lamps;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LampsC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("lamps")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args.length == 0) {
					sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Lamps Info" + ChatColor.DARK_AQUA + "---===");
					sender.sendMessage(ChatColor.AQUA + "Lamps plugin created by WolfLeader116");
					sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.RED + "/lamps help " + ChatColor.AQUA + "for a list of Lamps commands.");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Lamps Help" + ChatColor.DARK_AQUA + "---===");
						sender.sendMessage(ChatColor.RED + "/lamps " + ChatColor.AQUA + "Shows the info page.");
						sender.sendMessage(ChatColor.RED + "/lamps help " + ChatColor.AQUA + "Shows this message.");
						if (sender.hasPermission("lamps.create")) {
							sender.sendMessage(ChatColor.RED + "/lamps create " + ChatColor.AQUA + "Turns the lamp you are looking at into a permanently on lamp.");
							sender.sendMessage(ChatColor.RED + "/lamps night " + ChatColor.AQUA + "Turns the lamp you are looking at into a nighttime lamp.");
						}
					} else if (args[0].equalsIgnoreCase("create")) {
						try {
							if (Lamps.lamp.get(p.getName())) {
								Lamps.lamp.put(p.getName(), false);
								sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Disabled lamp creation mode");
							} else {
								if (Lamps.night.get(p.getName())) {
									Lamps.night.put(p.getName(), false);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Disabled night lamp creation mode");
									Lamps.lamp.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled lamp creation mode");
								} else {
									Lamps.lamp.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled lamp creation mode");
								}
							}
						} catch (NullPointerException e) {
							try {
								if (Lamps.night.get(p.getName())) {
									Lamps.night.put(p.getName(), false);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Disabled night lamp creation mode");
									Lamps.lamp.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled lamp creation mode");
								} else {
									Lamps.lamp.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled lamp creation mode");
								}
							} catch (NullPointerException ex) {
								Lamps.lamp.put(p.getName(), true);
								sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled lamp creation mode");
							}
						}
					} else if (args[0].equalsIgnoreCase("night")) {
						try {
							if (Lamps.night.get(p.getName())) {
								Lamps.night.put(p.getName(), false);
								sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Disabled night lamp creation mode");
							} else {
								if (Lamps.lamp.get(p.getName())) {
									Lamps.lamp.put(p.getName(), false);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Disabled lamp creation mode");
									Lamps.night.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled night lamp creation mode");
								} else {
									Lamps.night.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled night lamp creation mode.");
								}
							}
						} catch (NullPointerException e) {
							try {
								if (Lamps.lamp.get(p.getName())) {
									Lamps.lamp.put(p.getName(), false);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Disabled lamp creation mode");
									Lamps.night.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled night lamp creation mode");
								} else {
									Lamps.night.put(p.getName(), true);
									sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled night lamp creation mode.");
								}
							} catch (NullPointerException ex) {
								Lamps.night.put(p.getName(), true);
								sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Enabled night lamp creation mode.");
							}
						}
					} else {
						sender.sendMessage(ChatColor.BLUE + "Lamps> " + ChatColor.GREEN + "Unknown sub command! Use /lamps help to see subcommands.");
					}
				}
			} else {
				if (args.length == 0) {
					log.info("Use /lamps help for a list of Lamps commands.");
					log.info("Lamps plugin created by WolfLeader116");
					log.info("===---Lamps Info---===");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("help")) {
						log.info("/lamps help Shows this message.");
						log.info("/lamps Shows the info page.");
						log.info("===---Lamps Help---===");
					} else {
						log.info("Unknown sub command! Use /lamps help to see subcommands.");
					}
				}
			}
		}
		return false;
	}
}
