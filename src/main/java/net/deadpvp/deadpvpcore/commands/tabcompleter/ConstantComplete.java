package net.deadpvp.deadpvpcore.commands.tabcompleter;

import java.util.List;

public class ConstantComplete extends TabComplete{
    public ConstantComplete(String permission, String... elements) {
        super(permission, elements);
    }

    public ConstantComplete(String permission, List<String> elements) {
        super(permission, elements);
    }
}
