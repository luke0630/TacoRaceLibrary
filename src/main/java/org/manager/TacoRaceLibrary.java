package org.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.manager.Type.AbstractGUI;
import org.manager.Type.ListGUIAbstract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TacoRaceLibrary {
    private static final TacoRaceLibrary INSTANCE = new TacoRaceLibrary();

    public static TacoRaceLibrary getInstance() {
        return TacoRaceLibrary.INSTANCE;
    }

    private TacoRaceLibrary() {
    }

    //OpenGUIData
    public static class OpenGUIData {
        private Integer currentPage = 0;
        private Integer maxPage = 0;
        private Integer minPage = 0;

        public OpenGUIData(Integer currentPage, Integer maxPage) {
            this.currentPage = currentPage;
            this.maxPage = maxPage;
            this.minPage = 0;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }
        public void setCurrentPage(Integer setValue) {
            this.currentPage = setValue;
        }

        public Integer getMaxPage() {
            return maxPage;
        }

        public Integer getMinPage() {
            return minPage;
        }
    }

    private final Map<Player, AbstractGUI<?>> openGUIs = new HashMap<>();

    public Map<Player, AbstractGUI<?>> getOpenGUIs() {
        return openGUIs;
    }

    private final Map<Player, OpenGUIData> playerCurrentPageMap = new HashMap<>();

    public Map<Player, OpenGUIData> getPlayerCurrentPageMap() {
        return playerCurrentPageMap;
    }
}