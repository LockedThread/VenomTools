package org.venompvp.trenchtools.command.arguments;

import org.venompvp.trenchtools.enums.TrenchTool;
import org.venompvp.venom.commands.arguments.Argument;

public class TrenchToolArgument extends Argument<TrenchTool> {

    public TrenchToolArgument(String check) {
        super(check);
    }

    @Override
    public TrenchTool getValue() {
        return TrenchTool.fromString(check);
    }

    @Override
    public boolean isArgumentType() {
        return TrenchTool.fromString(check) != null;
    }

    @Override
    public String unableToParse() {
        return check + " is unable to be parsed as a TrenchTool";
    }
}
