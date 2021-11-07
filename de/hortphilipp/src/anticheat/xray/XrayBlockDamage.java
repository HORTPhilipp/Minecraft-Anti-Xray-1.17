/*
 * Made by HORTPhilipp
 */

package de.hortphilipp.src.anticheat.xray;

import de.serverplugin.devplugin.Main;
import de.serverplugin.devplugin.utils.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class XrayBlockDamage implements Listener {
    XrayUtils xu = new XrayUtils();

    // TODO SETTINGS
    /*
    * Increasing the "oreVicinity" value will cause the blocks around the mined block to
    * reset in a higher radius. This helps, if it bothers you, when sometimes some
    * ores flashes through the mined block. But this also decreases the effectiveness a bit, since
    * the not obfuscated part around the mined block is bigger. So it is recommended to not increase
    * "oreVicinity" by more than 2. Also, it is important that the value is at least 2, in order for
    * the anti-xray-system to work.
    * */
    int oreVicinity = 2;
    /*
    * Changing the value of "replacementRadius" effects the size of the area of blocks around the
    * mined block, which gets obfuscated. Recommended is a value of 6. Decreasing the value
    * can improve the servers performance a bit, but also reduces the effectiveness of the
    * anti-xray-system. Increase "replacementRadius" will raise the effectiveness, but could
    * affect the server performance.
    * */
    int replacementRadius = 6;

    @EventHandler
    public void onBlockDamageEvent(BlockDamageEvent e) {
        Location loc = e.getBlock().getLocation();

        for (Block b : xu.createAbstractBlockSphere(loc, replacementRadius)) {
            PersistentDataContainer bd = new CustomBlockData(b, Main.getInstance());
            Material mat = b.getType();
            if (mat != Material.AIR && mat != Material.VOID_AIR && mat != Material.CAVE_AIR && mat != Material.BEDROCK) {
                if (mat == Material.STONE ||
                        mat == Material.NETHERRACK ||
                        mat == Material.DEEPSLATE ||
                        mat == Material.ANDESITE ||
                        mat == Material.GRANITE ||
                        mat == Material.DIORITE ||
                        mat == Material.COAL_ORE ||
                        mat == Material.IRON_ORE ||
                        mat == Material.BLACKSTONE ||
                        mat == Material.COPPER_ORE ||
                        mat == Material.GOLD_ORE ||
                        mat == Material.REDSTONE_ORE ||
                        mat == Material.LAPIS_ORE ||
                        mat == Material.NETHER_QUARTZ_ORE ||
                        mat == Material.NETHER_GOLD_ORE ||
                        mat == Material.DEEPSLATE_COAL_ORE ||
                        mat == Material.DEEPSLATE_IRON_ORE ||
                        mat == Material.DEEPSLATE_COPPER_ORE ||
                        mat == Material.DEEPSLATE_GOLD_ORE ||
                        mat == Material.DEEPSLATE_REDSTONE_ORE ||
                        mat == Material.DEEPSLATE_LAPIS_ORE ||
                        mat == Material.BASALT ||
                        mat == Material.TUFF ||
                        mat == Material.DIAMOND_ORE ||
                        mat == Material.DEEPSLATE_DIAMOND_ORE ||
                        mat == Material.EMERALD_ORE ||
                        mat == Material.DEEPSLATE_EMERALD_ORE ||
                        mat == Material.ANCIENT_DEBRIS) {
                    if (bd.isEmpty())
                        bd.set(new NamespacedKey(Main.getInstance(), "anti-xr"), PersistentDataType.STRING, mat.name());

                    if (!xu.bordersAirInRadius(b.getLocation(), oreVicinity))
                        xu.placeRandomOreAt(b.getLocation());
                }
            }
        }
            xu.resetToOriginal(xu.createAbstractBlockSphere(loc, oreVicinity));
    }
}
