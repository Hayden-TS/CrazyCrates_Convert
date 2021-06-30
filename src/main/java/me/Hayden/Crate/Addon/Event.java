package me.Hayden.Crate.Addon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Event implements Listener {
    @EventHandler
    public void cmd(PlayerCommandPreprocessEvent e) {
        for (String s : Main.plugin.getConfig().getStringList("settings.aliases")) {
            if (e.getMessage().equalsIgnoreCase("/" + s)) {
                e.setCancelled(true);
                e.getPlayer().performCommand("virtualtophysical");
                return;
            }
        }
    }
}
