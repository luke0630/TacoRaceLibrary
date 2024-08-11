package org.manager.Type;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.manager.GUIListener;
import org.manager.GUIManager;
import org.manager.RunnableSystem;
import org.manager.TacoRaceLibrary;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;
import static org.manager.UtilityClass.getItem;
import static org.manager.UtilityClass.toColor;

public abstract class ListGUIAbstract<L extends Enum<L>> extends AbstractGUI<L> {

    static final Integer GUI_SIZE = 9*6;
    static final Integer CLICK_CENTER = 9*5+4;
    static final Integer CLICK_BACK = 9*5;
    static final Integer START_BAR_INDEX = 9*5;
    static final Integer GUI_ITEM_SIZE = 9*5;


    public Player player;
    public abstract String guiName();
    public abstract List<ItemStack> listItems();

    public abstract ItemStack setCenterInteractButton();
    public abstract RunnableSystem.InventoryRunnable whenClickListContent();
    public abstract RunnableSystem.InventoryRunnable whenClickListBack();
    public abstract RunnableSystem.InventoryRunnable whenClickListCenterInteract();

    @Override
    protected Inventory createInventory(Player player) {
        this.player = player;
        var pageMap = TacoRaceLibrary.getInstance().getPlayerCurrentPageMap();
        Inventory inventory = Bukkit.createInventory(null, GUI_SIZE, toColor(guiName()));

        var items = listItems();
        if(items == null) return inventory;

        if(items.size() > START_BAR_INDEX) {
            //ページが必要な場合
            int maxPage = getMaxPage(items.size());
            if(!pageMap.containsKey(player) || maxPage < pageMap.get(player).getCurrentPage()) {
                //1--Mapに存在しない場合
                //2--現在開いているページや次のページや前のページが削除されて、戻るボタンや次のボタンを押されたときにNULLが出るため0ページ目に戻ることでエラーを回避する
                pageMap.put(player, new TacoRaceLibrary.OpenGUIData(0, maxPage));
            }

            int openPage = pageMap.get(player).getCurrentPage();
            int minIndex = openPage * 45;

            var currentOpenPage = pageMap.get(player).getCurrentPage();
            inventory = Bukkit.createInventory(null, GUI_SIZE, toColor(guiName() + " &8&lページ" + (currentOpenPage + 1) + "/" + (maxPage + 1) ));

            //戻るボタンの表示
            if(currentOpenPage > 0) {
                inventory.setItem(START_BAR_INDEX+7, getItem(Material.FEATHER, "&c&l戻る"));
            }
            //次へボタンの表示
            if(currentOpenPage < maxPage) {
                inventory.setItem(START_BAR_INDEX+8, getItem(Material.ARROW, "&b&l次のページ"));
            }
            for(int i=0;i < START_BAR_INDEX;i++) {
                try {
                    inventory.setItem(i, items.get(minIndex + i));
                } catch(Exception e) {
                    break;
                }
            }
        }
        else {
            for(int i=0;i < items.size();i++) {
                inventory.setItem(i, items.get(i) );
            }
        }
        return inventory;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        // 共通のクリックイベント処理
        Player player = (Player) event.getWhoClicked();

        var slot = event.getSlot();
        event.setCancelled(true);
        RunnableSystem.InventoryRunnable inventoryRunnable = null;

        //------------下のバーの動作・コンテンツのクリック------------
        if(CLICK_CENTER == slot) {
            inventoryRunnable = whenClickListCenterInteract();
        } else if(CLICK_BACK == slot) {
            inventoryRunnable = whenClickListBack();
        } else if(slot < 5*9) {
            if(event.getCurrentItem() != null) {
                inventoryRunnable = whenClickListContent();
            }
        }
        if(inventoryRunnable != null) {
            inventoryRunnable.run(event);
        }
        //------------下のバーの動作・コンテンツのクリック------------

        //------------下のバーの戻る次へボタン-----------
        var pageMap = TacoRaceLibrary.getInstance().getPlayerCurrentPageMap();
        var openData = pageMap.get(player);
        if(openData == null) return;
        var currentOpenPage = openData.getCurrentPage();
        var maxPage = openData.getMaxPage();
        if(currentOpenPage > 0 && slot == START_BAR_INDEX + 7) {
            //戻る
            openData.setCurrentPage( openData.getCurrentPage()-1 );
            player.openInventory(getInventory(player));
            TacoRaceLibrary.getInstance().getOpenGUIs().put(player, this);
        } else if(currentOpenPage < maxPage && slot == START_BAR_INDEX+8) {
            //次へ
            openData.setCurrentPage( openData.getCurrentPage()+1 );
            player.openInventory(getInventory(player));
            TacoRaceLibrary.getInstance().getOpenGUIs().put(player, this);
        }
        //------------下のバーの戻る次へボタン-----------
    }

    private Integer getMaxPage(Integer size) {
        var result = (int) size / GUI_ITEM_SIZE;
        if(size % GUI_ITEM_SIZE == 0) result -= 1; //あまりがない時、何もないページができてしまうためそれを消す
        return result;
    }

}