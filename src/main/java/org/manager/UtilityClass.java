package org.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class UtilityClass {
    private UtilityClass() {

    }

    public static String toColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static ItemStack getItem(Material material, String name) {
        var item = new ItemStack(material);
        var meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory getInitInventory(Integer size, String invName) {
        return Bukkit.createInventory(null, size, invName);
    }


}
