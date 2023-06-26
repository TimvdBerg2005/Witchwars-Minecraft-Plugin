package me.tim.witchwars.world;

import me.tim.witchwars.Witchwars;
import me.tim.witchwars.teams.TeamColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Witch;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import static me.tim.witchwars.Witchwars.instance;

public class Generator {

    private Location location;
    private GeneratorType type;
    private TeamColor teamColor;

    private boolean activated = false;

    public Generator(Location location, GeneratorType type, TeamColor teamColor) {
        this.location = location;
        this.type = type;
        this.teamColor = teamColor;
    }
    public Generator(Location location, GeneratorType type) {
        this.location = location;
        this.type = type;
    }

    public void activateGenerator() {
        activated = true;
    }

    public void disableGenerator() {
        activated = false;
    }

    public void spawn() {
        if (activated) {
            ItemStack itemStack = null;
            switch (type) {
                case BLOOD_TOKEN:
                    // Check nearby blood tokens
                    int maxBloodTokens = 64;
                    int numBloodTokens = 0;
                    for (Entity nearbyEntity : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
                        if (nearbyEntity instanceof Item) {
                            ItemStack nearbyItemStack = ((Item) nearbyEntity).getItemStack();
                            if (nearbyItemStack.getType() == Material.NETHER_WART &&
                                    nearbyItemStack.hasItemMeta() &&
                                    nearbyItemStack.getItemMeta().hasDisplayName() &&
                                    ChatColor.stripColor(nearbyItemStack.getItemMeta().getDisplayName()).equals("Blood Token")) {
                                numBloodTokens += nearbyItemStack.getAmount();
                            }
                        }
                    }
                    if (numBloodTokens < maxBloodTokens) {
                        itemStack = new ItemStack(Material.NETHER_WART);
                        ItemMeta bloodTokenMeta = itemStack.getItemMeta();
                        bloodTokenMeta.setDisplayName(ChatColor.RED + "Blood Token");
                        itemStack.setItemMeta(bloodTokenMeta);
                    } else {
                        return;
                    }
                    break;
                case BROKEN_SHARD:
                    // Check nearby broken shards
                    int maxBrokenShards = 10;
                    int numBrokenShards = 0;
                    for (Entity nearbyEntity : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
                        if (nearbyEntity instanceof Item) {
                            ItemStack nearbyItemStack = ((Item) nearbyEntity).getItemStack();
                            if (nearbyItemStack.getType() == Material.PRISMARINE_SHARD &&
                                    nearbyItemStack.hasItemMeta() &&
                                    nearbyItemStack.getItemMeta().hasDisplayName() &&
                                    ChatColor.stripColor(nearbyItemStack.getItemMeta().getDisplayName()).equals("Broken Shard")) {
                                numBrokenShards += nearbyItemStack.getAmount();
                            }
                        }
                    }
                    if (numBrokenShards < maxBrokenShards) {
                        itemStack = new ItemStack(Material.PRISMARINE_SHARD);
                        ItemMeta brokenShardMeta = itemStack.getItemMeta();
                        brokenShardMeta.setDisplayName(ChatColor.DARK_AQUA + "Broken Shard");
                        itemStack.setItemMeta(brokenShardMeta);
                    } else {
                        return;
                    }
                    break;
                default:
                    return;
            }
            Item item = location.getWorld().dropItem(location, itemStack);
            item.setMetadata("generator", new FixedMetadataValue(instance, true));
            Vector velocity = item.getVelocity();
            velocity.setX(0);
            velocity.setZ(0);
            velocity.setY(-0.1);
            item.setVelocity(velocity);
            item.setPersistent(true);
            item.setUnlimitedLifetime(true);
        }
    }
}
