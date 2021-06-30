package me.Hayden.Crate.Addon;

import fr.minuskube.inv.InventoryManager;
import me.Hayden.Crate.Addon.commands.MainCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private InventoryManager invManager;

    public static Main plugin;

    public void onEnable() {
        int pluginId = 11530; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        this.invManager = new InventoryManager(this);
        this.invManager.init();
        saveDefaultConfig();
        reloadConfig();
        getCommand("virtualtophysical").setExecutor((CommandExecutor)new MainCommand());
        Bukkit.getPluginManager().registerEvents(new Event(), (Plugin)this);
        plugin = this;
    }

    public InventoryManager getInvManager() {
        return this.invManager;
    }
}
