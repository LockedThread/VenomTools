package org.venompvp.trenchtools.command;

import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.venompvp.trenchtools.enums.TrenchTool;
import org.venompvp.venom.commands.Command;
import org.venompvp.venom.commands.arguments.Argument;
import org.venompvp.venom.module.Module;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrenchToolListCommand extends Command {

    public TrenchToolListCommand(Module module) {
        super(module, module.getCommandHandler().getCommand(TrenchToolCommand.class), "list", "Lists all known trenchtools", Collections.emptyList(), "trenchtools.give");
    }

    @Override
    public void execute(CommandSender sender, List<Argument> args, String label) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eTrenchTools: &d" + Joiner.on(", ").skipNulls().join(Arrays.stream(TrenchTool.values()).map(TrenchTool::getConfigKey).collect(Collectors.toList()))));
        sender.sendMessage("");
    }

    @Override
    public String getUsage(String label) {
        return "/trenchtools " + label;
    }
}