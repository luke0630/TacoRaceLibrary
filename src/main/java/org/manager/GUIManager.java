package org.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.manager.Type.AbstractGUI;
import org.manager.Type.ListGUIAbstract;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Currency.getInstance;
import static org.manager.UtilityClass.toColor;

public class GUIManager<E extends Enum<E>, L extends Enum<L>> {

    private final Map<E, AbstractGUI<E>> guiMap = new HashMap<>();
    private final Map<L, ListGUIAbstract<L>> listGuiMap = new HashMap<>();

    public void registerGUI(List<? extends AbstractGUI<E>> guis) {
        for(var gui : guis) {
            guiMap.put(gui.getType(), gui);
        }
    }
    public void registerListGUI(List<ListGUIAbstract<L>> listGui) {
        for(var gui : listGui) {
            listGuiMap.put(gui.getType(), gui);
        }
    }

    public void openGUI(Player player, E type) {
        AbstractGUI<E> gui = guiMap.get(type);
        openForPlayer(player, gui);
    }

    public void openListGUI(Player player, L type) {
        ListGUIAbstract<L> listGui = listGuiMap.get(type);
        openForPlayer(player, listGui);
    }

    private void openForPlayer(Player player, AbstractGUI<?> gui) {
        player.sendMessage("あうあうああふぁｄふぁｄふぁｄふぁｄふぁ");
        if (gui != null) {
            var guiData = TacoRaceLibrary.getInstance().getOpenGUIs();

            if (guiData.containsKey(player)) {
                try {
                    gui.getInventory(player);
                } catch(Exception e) {
                    System.out.print(e.toString());
                    player.sendMessage(toColor("&c&lエラーが発生したため開けませんでした。"));
                    return;
                }
            }
            guiData.put(player, gui);
            player.openInventory(gui.getInventory(player));
            guiData.put(player, gui);
        } else {
            player.sendMessage("指定されたGUIは見つかりませんでした。");
        }
    }


    public Collection<AbstractGUI<E>> getAllGUIs() {
        return guiMap.values();
    }

    public Collection<ListGUIAbstract<L>> getAllListGUIs() {
        return listGuiMap.values();
    }
}