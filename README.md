# Minecraft-Anti-Xray-1.17
 
``Minecraft-Anti-Xray-1.17`` is an Anti-Cheat, which is easy to implement in your plugin. It places ores all around the mining player and so blocks the vision to "real" ores. Thus it is impossible to use Xray-Texturepacks.

It is based on the `CustomBlockData` libary from [JEFF-Media-GbR](https://github.com/JEFF-Media-GbR/CustomBlockData).

It doesn't need any extra files or something similar and resists server restarts.
If you want, you can adjust your settings in the [`XrayBlockDamage.java`](https://github.com/HORTPhilipp/Minecraft-Anti-Xray-1.17/blob/main/de/hortphilipp/src/anticheat/xray/XrayBlockDamage.java) class.

``Minecraft-Anti-Xray-1.17`` is compatible with all Bukkit versions 1.16.3+.

## Advantages

- It does not need any files or databases
- It does not impact the minecraft gameplay
- It is server restart resistant
- It works in the [Overworld](https://minecraft.fandom.com/wiki/Overworld) and the [Nether](https://minecraft.fandom.com/wiki/The_Nether)

## Usage

First you have to an create a function in your main class, which returns the main instance:

```java
private static Main instance;

    public static Main getInstance() {
        return instance;
    }
```

Secondly you have to copy all the ``Minecraft-Anti-Xray-1.17`` files in your plugin (the path is irrelavant).

Finally you must not forget to register the listener and adjust the settings for your server in [`XrayBlockDamage.java`](https://github.com/HORTPhilipp/Minecraft-Anti-Xray-1.17/blob/main/de/hortphilipp/src/anticheat/xray/XrayBlockDamage.java):

```java
@Override
public void onEnable() {
    instance = this;
    registerListener();
 }
    
public void registerListener() {
    PluginManager pluginManager = Bukkit.getPluginManager();
    
    pluginManager.registerEvents(new XrayBlockDamage(), this);
    pluginManager.registerEvents(new XrayBlockBreak(), this);
}
```

### Caution

If you want to disable the ``Minecraft-Anti-Xray-1.17`` you only must delete the [`XrayBlockDamage.java`](https://github.com/HORTPhilipp/Minecraft-Anti-Xray-1.17/blob/main/de/hortphilipp/src/anticheat/xray/XrayBlockDamage.java) file and adjust your `registerListener()` function. Also you have to insert this line in the `onBlockBreakEvent(BlockBreakEvent e)` function in the [`XrayBlockBreak.java`](https://github.com/HORTPhilipp/Minecraft-Anti-Xray-1.17/blob/main/de/hortphilipp/src/anticheat/xray/XrayBlockBreak.java) file:

```java
xu.resetToOriginal(xu.createAbstractBlockSphere(e.getBlock().getLocation(), 3));
```

If you do it different, and delete all files, the ores from the orbfuscated blocks will stay and will be mineable.


## Others

### Thanks

I want to thank [JEFF-Media-GbR](https://github.com/JEFF-Media-GbR/) for the creation of his public [`CustomBlockData`](https://github.com/JEFF-Media-GbR/CustomBlockData) libary.

### Legal

All rights from [`CustomBlockData`](https://github.com/JEFF-Media-GbR/CustomBlockData) libary go to [JEFF-Media-GbR](https://github.com/JEFF-Media-GbR/).

All other rights reserved to the creator [HORTPhilipp](https://github.com/HORTPhilipp/) (me).
