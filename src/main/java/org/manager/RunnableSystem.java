package org.manager;

import org.bukkit.event.inventory.InventoryClickEvent;

public class RunnableSystem {
    @FunctionalInterface
    public interface InventoryRunnable {
        void run(InventoryClickEvent event);
    }
}
