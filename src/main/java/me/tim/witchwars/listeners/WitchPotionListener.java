package me.tim.witchwars.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class WitchPotionListener implements Listener {

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Witch) {
            event.setCancelled(true);
        }
    }
}
