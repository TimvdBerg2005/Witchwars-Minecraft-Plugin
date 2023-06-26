package me.tim.witchwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class OnCraftEvent implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        event.setCancelled(true);
    }
}
