package org.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.manager.Type.AbstractGUI;

public class GUIListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        handleInventoryClick(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        TacoRaceLibrary.getInstance().getOpenGUIs().remove(player);
    }

    //ListGUIで使用する
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        var clickedInv = event.getClickedInventory();
        var topInv = player.getOpenInventory().getTopInventory();
        if(TacoRaceLibrary.getInstance().getOpenGUIs().containsKey(player)) {
            event.setCancelled(true);
            if(clickedInv == null) return;
            if(!clickedInv.equals(topInv)) return;
            AbstractGUI<?> gui = TacoRaceLibrary.getInstance().getOpenGUIs().get(player);
            if (gui != null) {
                gui.onInventoryClick(event);
            }
        }
    }
}