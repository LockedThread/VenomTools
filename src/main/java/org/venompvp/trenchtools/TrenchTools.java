package org.venompvp.trenchtools;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.venompvp.trenchtools.command.TrenchToolCommand;
import org.venompvp.trenchtools.enums.Messages;
import org.venompvp.trenchtools.enums.TrenchTool;
import org.venompvp.trenchtools.listeners.PlayerListener;
import org.venompvp.venom.module.Module;
import org.venompvp.venom.module.ModuleInfo;

import java.util.Arrays;
import java.util.stream.Collectors;

@ModuleInfo(name = "TrenchTools", version = "1.0", author = "Simpleness", description = "TrenchTools plugin for Venom")
public class TrenchTools extends Module {

    private static TrenchTools instance;

    public static TrenchTools getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        setupModule(this);
        /*
         * Config Shit
         */
        getConfig().options().copyDefaults(true);
        saveConfig();

        getCommandHandler().register(this, new TrenchToolCommand(this));

        Arrays.stream(TrenchTool.values()).forEach(trenchTool -> {
            ItemStack itemStack = configSectionToItemStack("trench-tools." + trenchTool.getConfigKey(), true, false);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(itemMeta.getLore().stream().map(s -> s.replace("{tray-mode}", ChatColor.translateAlternateColorCodes('&', "&cDISABLED"))).collect(Collectors.toList()));
            itemStack.setItemMeta(itemMeta);
            NBTItem nbtItem = new NBTItem(itemStack);
            nbtItem.setBoolean("tray-mode", false);
            trenchTool.setItemStack(nbtItem.getItem());
        });

        ConfigurationSection messageSection = getConfig().getConfigurationSection("messages");
        if (messageSection == null) messageSection = getConfig().createSection("messages");

        for (Messages messages : Messages.values()) {
            if (messageSection.isSet(messages.getKey())) {
                messages.setMessage(messageSection.getString(messages.getKey()));
            } else {
                messageSection.set(messages.getKey(), messages.getVal());
            }
        }

        saveConfig();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
        disableCommands(this);
    }

    private ItemStack configSectionToItemStack(String where, boolean enchants, boolean dye) {
        ItemStack itemStack = dye ? new ItemStack(Material.matchMaterial(getConfig().getString(where + ".material")), 1, DyeColor.valueOf(getConfig().getString(where + ".dye.color")).getDyeData()) : new ItemStack(Material.matchMaterial(getConfig().getString(where + ".material")));
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString(where + ".name")));
        meta.setLore(getConfig().getStringList(where + ".lore").stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
        if (enchants) {
            if (getConfig().isSet(where + ".enchanted")) {
                if (getConfig().getBoolean(where + ".enchants")) {
                    meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
            } else for (String s : getConfig().getStringList(where + ".enchants")) {
                String[] split = s.split(":");
                meta.addEnchant(Enchantment.getByName(split[0]), Integer.parseInt(split[1]), true);
            }
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
