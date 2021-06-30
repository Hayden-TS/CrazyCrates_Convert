package me.Hayden.Crate.Addon;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.badbones69.crazycrates.api.CrazyCrates;
import me.badbones69.crazycrates.api.enums.KeyType;
import me.badbones69.crazycrates.api.objects.Crate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GUI implements InventoryProvider {
    static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    CrazyCrates api = CrazyCrates.getInstance();



    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("GUI")
            .provider(new GUI())
            .size(Main.plugin.getConfig().getInt("settings.rows"), 9)
            .title(chat(Main.plugin.getConfig().getString("settings.title")))
            .manager(Main.plugin.getInvManager())
            .build();

    private final Random random = new Random();

    public void init(Player player, InventoryContents contents) {}

    public void update(Player player, InventoryContents contents) {
        for (Iterator<String> iterator = Main.plugin.getConfig().getConfigurationSection("keys").getKeys(false).iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            Crate crate = this.api.getCrateFromName(key);
            ItemStack stack = new ItemStack(this.api.getCrateFromName(key).getKey().getType());
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(chat(Main.plugin.getConfig().getString("keys." + key + ".displayname")));
            List<String> loreasList = Main.plugin.getConfig().getStringList("settings.lore");
            loreasList.replaceAll(e -> e.replace("%key_name%", key));
            loreasList.replaceAll(e -> e.replace("%key_displayname%", crate.getKey().getItemMeta().getDisplayName()));
            loreasList.replaceAll(e -> e.replace("%amount%", String.valueOf(this.api.getVirtualKeys(player, crate))));
            List<String> loreList = new ArrayList<>();
            for (String s : loreasList)
                loreList.add(chat(s));
            meta.setLore(loreList);
            stack.setItemMeta(meta);
            contents.set(Main.plugin.getConfig().getInt("keys." + key + ".row"), Main.plugin.getConfig().getInt("keys." + key + ".column"), ClickableItem.of(stack, e -> {
                String nokeys = Main.plugin.getConfig().getString("messages.nokeys").replace("%key_name%", key).replace("%key_displayname%", crate.getKey().getItemMeta().getDisplayName());
                Integer keys = Integer.valueOf(this.api.getVirtualKeys(player, crate));
                if (Main.plugin.getConfig().getBoolean("settings.redeemall") == true && e.getClick() == ClickType.valueOf(Main.plugin.getConfig().getString("settings.redeemall_button"))) {
                    if (keys.intValue() < 1) {
                        player.sendMessage(chat(nokeys));
                        return;
                    }
                    String redeemallkey = Main.plugin.getConfig().getString("messages.allkeysredeemed").replace("%key_name%", key).replace("%key_displayname%", crate.getKey().getItemMeta().getDisplayName()).replace("%amount%", String.valueOf(this.api.getVirtualKeys(player, crate)));
                    int allkeys = this.api.getVirtualKeys(player, crate);
                    this.api.takeKeys(allkeys, player, crate, KeyType.VIRTUAL_KEY, false);
                    this.api.addKeys(allkeys, player, crate, KeyType.PHYSICAL_KEY);
                    player.sendMessage(chat(redeemallkey));
                    return;
                }
                if (keys.intValue() >= 1) {
                    String yeskeys = Main.plugin.getConfig().getString("messages.keyredeemed").replace("%key_name%", key).replace("%key_displayname%", crate.getKey().getItemMeta().getDisplayName());
                    this.api.takeKeys(1, player, crate, KeyType.VIRTUAL_KEY, false);
                    this.api.addKeys(1, player, crate, KeyType.PHYSICAL_KEY);
                    player.sendMessage(chat(yeskeys));
                } else {
                    player.sendMessage(chat(nokeys));
                }
            }));
        }
    }
}
