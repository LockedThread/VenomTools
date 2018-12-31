package org.venompvp.trenchtools.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.venompvp.trenchtools.command.arguments.TrenchToolArgument;
import org.venompvp.trenchtools.enums.Messages;
import org.venompvp.trenchtools.enums.TrenchTool;
import org.venompvp.venom.commands.Command;
import org.venompvp.venom.commands.arguments.Argument;
import org.venompvp.venom.commands.arguments.OptionalIntegerArgument;
import org.venompvp.venom.commands.arguments.PlayerArgument;
import org.venompvp.venom.module.Module;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class TrenchToolGiveCommand extends Command {

    public TrenchToolGiveCommand(Module module) {
        super(module, module.getCommandHandler().getCommand(TrenchToolCommand.class), "give", "Gives trenchtools to players", Arrays.asList(PlayerArgument.class, TrenchToolArgument.class, OptionalIntegerArgument.class), "trenchtools.give");
    }

    @Override
    public void execute(CommandSender sender, List<Argument> args, String label) {
        Player target = (Player) args.get(0).getValue();
        TrenchTool trenchTool = (TrenchTool) args.get(1).getValue();
        OptionalIntegerArgument optionalIntegerArgument = (OptionalIntegerArgument) args.get(2);
        int amount = optionalIntegerArgument.isPresent() ? optionalIntegerArgument.getValue() : 1;
        IntStream.range(0, amount).forEach(i -> target.getInventory().addItem(trenchTool.getItemStack()));
        sender.sendMessage(Messages.TRENCHTOOL_GIVE.toString().replace("{player}", target.getName()).replace("{type}", trenchTool.getConfigKey()).replace("{amount}", String.valueOf(amount)));
    }

    @Override
    public String getUsage(String label) {
        return "/trenchtools " + label + " [player] [trenchtool] [amount]";
    }
}
