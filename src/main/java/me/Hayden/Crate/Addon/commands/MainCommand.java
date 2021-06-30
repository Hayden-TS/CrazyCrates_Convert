package me.Hayden.Crate.Addon.commands;

import me.Hayden.Crate.Addon.GUI;
import me.Hayden.Crate.Addon.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player))
            return true;
        if (args.length == 1 && args[0].equalsIgnoreCase("about")) {
            sender.sendMessage(chat("&c&lVirtual2Physical"));
            sender.sendMessage(chat("&7CrazyCrates addon developed by &cHayden"));
            sender.sendMessage(chat("&7Version &4" + Main.plugin.getDescription().getVersion()));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("virtualconvert.reload")) {

            Main.plugin.reloadConfig();
            sender.sendMessage(chat("&c&lConfig reloaded"));
            sender.sendMessage(chat("Note: Some changes require restart"));
            sender.sendMessage(chat("&c&lPlugin version " + Main.plugin.getDescription().getVersion()));
            return true;
        }

        if (!sender.hasPermission("virtualconvert.convert")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }
        Player p = (Player)sender;
        GUI.INVENTORY.open(p);
        return false;
    }
}
