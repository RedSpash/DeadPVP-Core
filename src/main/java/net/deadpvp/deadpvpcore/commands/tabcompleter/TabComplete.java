package net.deadpvp.deadpvpcore.commands.tabcompleter;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

import java.security.Permission;
import java.util.List;

public abstract class TabComplete {


    private final String permission;
    private final List<String> elements;

    protected TabComplete(String permission, String... elements) {
        this(permission,List.of(elements));
    }

    protected TabComplete(String permission, List<String> elements) {
        this.permission = permission;
        this.elements = elements;
    }

    public List<String> getElements() {
        return elements;
    }

    public boolean hasPermission(Permissible permissible) {
        return permissible.hasPermission(this.permission) || this.permission.equalsIgnoreCase("");
    }
}
