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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XrayUtils {
    public void resetToOriginal(Block block) {
        PersistentDataContainer bd = new CustomBlockData(block, Main.getInstance());
        String mat = bd.get(new NamespacedKey(Main.getInstance(), "anti-xr"), PersistentDataType.STRING);
        if (mat != null) block.setType(stringToMaterial(mat));
    }

    public void resetToOriginal(List<Block> blocks) {
        for (Block b : blocks)
            resetToOriginal(b);
    }

    public Material stringToMaterial(String material) {
        Material res = null;
        switch (material) {
            case "STONE":
                res = Material.STONE;
                break;
            case "NETHERRACK":
                res = Material.NETHERRACK;
                break;
            case "DEEPSLATE":
                res = Material.DEEPSLATE;
                break;
            case "ANDESITE":
                res = Material.ANDESITE;
                break;
            case "GRANITE":
                res = Material.GRANITE;
                break;
            case "DIORITE":
                res = Material.DIORITE;
                break;
            case "COAL_ORE":
                res = Material.COAL_ORE;
                break;
            case "IRON_ORE":
                res = Material.IRON_ORE;
                break;
            case "BLACKSTONE":
                res = Material.BLACKSTONE;
                break;
            case "COPPER_ORE":
                res = Material.COPPER_ORE;
                break;
            case "GOLD_ORE":
                res = Material.GOLD_ORE;
                break;
            case "REDSTONE_ORE":
                res = Material.REDSTONE_ORE;
                break;
            case "LAPIS_ORE":
                res = Material.LAPIS_ORE;
                break;
            case "NETHER_QUARTZ_ORE":
                res = Material.NETHER_QUARTZ_ORE;
                break;
            case "NETHER_GOLD_ORE":
                res = Material.NETHER_GOLD_ORE;
                break;
            case "DEEPSLATE_COAL_ORE":
                res = Material.DEEPSLATE_COAL_ORE;
                break;
            case "DEEPSLATE_IRON_ORE":
                res = Material.DEEPSLATE_IRON_ORE;
                break;
            case "DEEPSLATE_COPPER_ORE":
                res = Material.DEEPSLATE_COPPER_ORE;
                break;
            case "DEEPSLATE_GOLD_ORE":
                res = Material.DEEPSLATE_GOLD_ORE;
                break;
            case "DEEPSLATE_REDSTONE_ORE":
                res = Material.DEEPSLATE_REDSTONE_ORE;
                break;
            case "DEEPSLATE_LAPIS_ORE":
                res = Material.DEEPSLATE_LAPIS_ORE;
                break;
            case "BASALT":
                res = Material.BASALT;
                break;
            case "TUFF":
                res = Material.TUFF;
                break;
            case "DIAMOND_ORE":
                res = Material.DIAMOND_ORE;
                break;
            case "DEEPSLATE_DIAMOND_ORE":
                res = Material.DEEPSLATE_DIAMOND_ORE;
                break;
            case "EMERALD_ORE":
                res = Material.EMERALD_ORE;
                break;
            case "DEEPSLATE_EMERALD_ORE":
                res = Material.DEEPSLATE_EMERALD_ORE;
                break;
            case "ANCIENT_DEBRIS":
                res = Material.ANCIENT_DEBRIS;
                break;
        }
        return res;
    }

    public void placeRandomOreAt(Location blockLocation) {
        Material res = null;
        switch (new Random().nextInt(19)) {
            case 0:
                res = Material.COAL_ORE;
                break;
            case 1:
                res = Material.IRON_ORE;
                break;
            case 2:
                res = Material.GOLD_ORE;
                break;
            case 3:
                res = Material.COPPER_ORE;
                break;
            case 4:
                res = Material.LAPIS_ORE;
                break;
            case 5:
                res = Material.REDSTONE_ORE;
                break;
            case 6:
                res = Material.DIAMOND_ORE;
                break;
            case 7:
                res = Material.EMERALD_ORE;
                break;
            case 8:
                res = Material.NETHER_QUARTZ_ORE;
                break;
            case 9:
                res = Material.NETHER_GOLD_ORE;
                break;
            case 10:
                res = Material.ANCIENT_DEBRIS;
                break;
            case 11:
                res = Material.DEEPSLATE_COAL_ORE;
                break;
            case 12:
                res = Material.DEEPSLATE_IRON_ORE;
                break;
            case 13:
                res = Material.DEEPSLATE_GOLD_ORE;
                break;
            case 14:
                res = Material.DEEPSLATE_COPPER_ORE;
                break;
            case 15:
                res = Material.DEEPSLATE_LAPIS_ORE;
                break;
            case 16:
                res = Material.DEEPSLATE_REDSTONE_ORE;
                break;
            case 17:
                res = Material.DEEPSLATE_DIAMOND_ORE;
                break;
            case 18:
                res = Material.DEEPSLATE_EMERALD_ORE;
                break;
        }
        blockLocation.getWorld().getBlockAt(blockLocation).setType(res);
    }

    public List<Block> createAbstractBlockSphere(Location sourceBlock, int radius) {
        List<Block> res = new ArrayList<>();
        int bx = sourceBlock.getBlockX();
        int by = sourceBlock.getBlockY();
        int bz = sourceBlock.getBlockZ();

        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    if (((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y))) < radius * radius)
                        res.add(sourceBlock.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return res;
    }

    public boolean bordersAirInRadius(Location sourceBlock, int radius) {
        for (Block b : createAbstractBlockSphere(sourceBlock, radius)) {
            if (!b.getType().isOccluding())
                return true;
        }
        return false;
    }
}