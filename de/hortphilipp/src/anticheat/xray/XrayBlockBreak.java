/*
 * Made by HORTPhilipp
 * https://github.com/HORTPhilipp/Minecraft-Anti-Xray-1.17/
 */

package de.hortphilipp.src.anticheat.xray;

import de.hortphilipp.src.Main;
import de.hortphilipp.src.anticheat.xray.CustomBlockData;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class XrayBlockExplode implements Listener {
    XrayUtils xu = new XrayUtils();

    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent e) {
        xu.resetToOriginal(xu.createAbstractBlockSphere(e.getEntity().getLocation(), 8));
    }

    @EventHandler
    void onEntityExplodeEvent(EntityExplodeEvent e) {
        for (Block b : e.blockList())
            new CustomBlockData(b, Main.getInstance()).clear();
    }

    @EventHandler
    void onBlockExplodeEvent(BlockExplodeEvent e) {
        xu.resetToOriginal(xu.createAbstractBlockSphere(e.getBlock().getLocation(), 8));

        for (Block b : e.blockList())
            new CustomBlockData(b, Main.getInstance()).clear();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        new CustomBlockData(e.getBlock(), Main.getInstance()).clear();
    }
}
