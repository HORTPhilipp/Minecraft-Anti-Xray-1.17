/*
 * Made by mfnalex / JEFF Media GbR
 *
 * If you find this helpful or if you're using this project inside your paid plugins,
 * consider leaving a donation :)
 *
 * https://paypal.me/mfnalex
 *
 * If you need help or have any suggestions, just create an issue or join my discord
 * and head to the channel #programming-help
 *
 * https://discord.jeff-media.de
 */

package de.hortphilipp.src.anticheat.xray;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class CustomBlockData implements PersistentDataContainer {

    private final PersistentDataContainer pdc;
    private final Chunk chunk;
    private final NamespacedKey key;

    public CustomBlockData(final @NotNull Block block, final @NotNull Plugin plugin) {
        this.chunk = block.getChunk();
        this.key = new NamespacedKey(plugin, getKey(block));
        this.pdc = getPersistentDataContainer();
    }

    @NotNull
    private static String getKey(@NotNull Block block) {
        final int x = block.getX() & 0x000F;
        final int y = block.getY() & 0x00FF;
        final int z = block.getZ() & 0x000F;
        return String.format("x%dy%dz%d", x, y, z);
    }

    public void clear() {
        pdc.getKeys().forEach(pdc::remove);
        save();
    }

    @NotNull
    private PersistentDataContainer getPersistentDataContainer() {
        final PersistentDataContainer chunkPDC = chunk.getPersistentDataContainer();
        final PersistentDataContainer blockPDC;
        if (chunkPDC.has(key, PersistentDataType.TAG_CONTAINER)) {
            blockPDC = chunkPDC.get(key, PersistentDataType.TAG_CONTAINER);
            assert blockPDC != null;
            return blockPDC;
        }
        blockPDC = chunkPDC.getAdapterContext().newPersistentDataContainer();
        chunkPDC.set(key, PersistentDataType.TAG_CONTAINER, blockPDC);
        return blockPDC;
    }

    private void save() {
        if (pdc.isEmpty()) {
            chunk.getPersistentDataContainer().remove(key);
        } else {
            chunk.getPersistentDataContainer().set(key, PersistentDataType.TAG_CONTAINER, pdc);
        }
    }

    @Override
    public <T, Z> void set(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType, final @NotNull Z z) {
        pdc.set(namespacedKey, persistentDataType, z);
        save();
    }

    @Override
    public <T, Z> boolean has(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType) {
        return pdc.has(namespacedKey, persistentDataType);
    }

    @Nullable
    @Override
    public <T, Z> Z get(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType) {
        return pdc.get(namespacedKey, persistentDataType);
    }

    @NotNull
    @Override
    public <T, Z> Z getOrDefault(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType, final @NotNull Z z) {
        return pdc.getOrDefault(namespacedKey, persistentDataType, z);
    }

    @NotNull
    @Override
    public Set<NamespacedKey> getKeys() {
        return pdc.getKeys();
    }

    @Override
    public void remove(final @NotNull NamespacedKey namespacedKey) {
        pdc.remove(namespacedKey);
        save();
    }

    @Override
    public boolean isEmpty() {
        return pdc.isEmpty();
    }

    @NotNull
    @Override
    public PersistentDataAdapterContext getAdapterContext() {
        return pdc.getAdapterContext();
    }
}