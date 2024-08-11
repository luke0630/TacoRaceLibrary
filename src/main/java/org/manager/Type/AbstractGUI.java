package org.manager.Type;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class AbstractGUI<E extends Enum<E>> {
    public final Inventory getInventory(Player player) {
        return createInventory(player);
    }

    protected abstract Inventory createInventory(Player player);
    public abstract E getType();
    public abstract void onInventoryClick(InventoryClickEvent event);
}