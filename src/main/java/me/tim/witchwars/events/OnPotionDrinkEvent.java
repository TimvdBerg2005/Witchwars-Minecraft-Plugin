package me.tim.witchwars.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

public class OnPotionDrinkEvent implements Listener {

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Witch && event.getNewEffect().getType() == PotionEffectType.HEAL) {
            event.setCancelled(true);
        }
    }
}
