package org.venompvp.trenchtools.enums;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.venompvp.trenchtools.TrenchTools;
import org.venompvp.venom.Venom;
import org.venompvp.venom.utils.Utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum TrenchTool {

    _11X11, _5X5, _7X7, _9X9;

    private ItemStack itemStack;

    public static TrenchTool fromString(String s) {
        return Arrays.stream(values()).filter(trenchTool -> trenchTool.getConfigKey().equalsIgnoreCase(s) || trenchTool.name().equalsIgnoreCase(s)).findFirst().orElse(null);
    }

    public static TrenchTool trenchToolFromItemStack(ItemStack itemStack) {
        return Arrays.stream(values()).filter(trenchTool -> Utils.isItem(itemStack, trenchTool.getItemStack())).findFirst().orElse(null);
    }

    public String getConfigKey() {
        return name().substring(1).toLowerCase();
    }

    public int getRadius() {
        return Integer.parseInt(name().substring(1).split("X")[0]) / 2;
    }

    public void toggleTrayMode(Player player) {
        ItemStack item = player.getItemInHand();

        boolean trayMode = !new NBTItem(item.clone()).getBoolean("tray-mode");

        ItemMeta meta = item.getItemMeta();
        meta.setLore(TrenchTools.getInstance().getConfig().getStringList("trench-tools." + getConfigKey() + ".lore").stream().map(s -> ChatColor.translateAlternateColorCodes('&', s.replace("{tray-mode}", trayMode ? "&aENABLED" : "&cDISABLED"))).collect(Collectors.toList()));
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("tray-mode", !nbtItem.getBoolean("tray-mode"));
        player.setItemInHand(nbtItem.getItem());
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void execute(Player player, Location location) {
        Bukkit.getScheduler().runTaskAsynchronously(Venom.getInstance(), () -> {

            boolean trenchMode = new NBTItem(player.getItemInHand()).getBoolean("tray-mode");
            final int radius = getRadius();
            for (int x = location.getBlockX() - radius; x <= radius + location.getBlockX(); x++) {
                for (int y = location.getBlockY() - radius; y <= radius + location.getBlockY(); y++) {
                    for (int z = location.getBlockZ() - radius; z <= radius + location.getBlockZ(); z++) {
                        Block block = location.getWorld().getBlockAt(x, y, z);
                        if (!Utils.canEdit(player, location)) continue;
                        if (block.getType() != Material.BEACON &&
                                block.getType() != Material.MOB_SPAWNER &&
                                block.getType() != Material.AIR &&
                                block.getType() != Material.BEDROCK &&
                                Utils.canEdit(player, block.getLocation())) {
                            if (trenchMode) {
                                if (block.getType() == Material.NETHERRACK || block.getType() == Material.DIRT || block.getType() == Material.GRASS) {
                                    Utils.editBlockType(block.getLocation(), Material.AIR, true);
                                }
                            } else {
                                Utils.editBlockType(block.getLocation(), Material.AIR);
                            }
                        }
                    }
                }
            }
        });
    }

}
