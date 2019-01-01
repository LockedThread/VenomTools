package org.venompvp.trenchtools.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.venompvp.trenchtools.enums.Messages;
import org.venompvp.trenchtools.enums.TrenchTool;
import org.venompvp.venom.utils.Utils;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            TrenchTool trenchTool = TrenchTool.trenchToolFromItemStack(event.getItem());
            if (trenchTool != null) {
                trenchTool.toggleTrayMode(player);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!event.isCancelled() && player.getItemInHand() != null) {
            TrenchTool trenchTool = TrenchTool.trenchToolFromItemStack(player.getItemInHand());
            if (trenchTool != null) {
                Block block = event.getBlock();
                if (block.getType() == Material.BEACON || block.getType() == Material.MOB_SPAWNER || block.getType() == Material.BEDROCK) {
                    player.sendMessage(Messages.CANT_EDIT_BLOCK.toString());
                    event.setCancelled(true);
                } else if (!Utils.canEdit(player, event.getBlock().getLocation())) {
                    player.sendMessage(Messages.CANT_EDIT.toString());
                    event.setCancelled(true);
                } else {
                    trenchTool.execute(player, event.getBlock().getLocation());
                }
            }
        }
    }
}
