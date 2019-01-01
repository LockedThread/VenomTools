package org.venompvp.trenchtools.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.venompvp.venom.commands.Command;
import org.venompvp.venom.commands.ParentCommand;
import org.venompvp.venom.commands.arguments.Argument;
import org.venompvp.venom.module.Module;

import java.util.Collections;
import java.util.List;

public class TrenchToolCommand extends Command implements ParentCommand {


    public TrenchToolCommand(Module module) {
        super(module, "trenchtools", "Root trenchtool command", Collections.emptyList(), "trenchtools.give", "trenchtool");
    }

    @Override
    public void execute(CommandSender sender, List<Argument> args, String label) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/trenchtool list - &dlists VenomTools"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/trenchtool give {target} {tool} [amount] - &dgives a player a TrenchTool"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[]'s are optional"));
        sender.sendMessage("");
    }

    @Override
    public String getUsage(String label) {
        return "/" + label;
    }

    @Override
    public void setupSubCommands() {
        addSubCommands(new TrenchToolGiveCommand(module), new TrenchToolListCommand(module));
    }
}

