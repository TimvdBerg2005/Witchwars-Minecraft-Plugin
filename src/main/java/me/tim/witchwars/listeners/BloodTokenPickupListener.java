package me.tim.witchwars.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static me.tim.witchwars.Witchwars.instance;

public class BloodTokenPickupListener implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        double radius = 1.5;
        Item item = event.getItem();
        if (item.hasMetadata("generator")) {
            List<Entity> nearbyEntities = item.getNearbyEntities(radius, radius, radius);
            for (Entity entities : nearbyEntities) {
                if (entities instanceof Player) {
                    Player player = (Player) entities;
                    event.setCancelled(true);
                    item.remove();
                    ItemStack itemStack = item.getItemStack();
                    player.getInventory().addItem(itemStack);
                }
            }
        }
    }
}

