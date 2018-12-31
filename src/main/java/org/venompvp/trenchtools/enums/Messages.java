package org.venompvp.trenchtools.enums;

import org.bukkit.ChatColor;

public enum Messages {

    CANT_EDIT_BLOCK("&c&l(!) &cYou can't break this block!"),
    CANT_EDIT("&c&l(!) &cYou can't edit blocks here!"),
    NOT_INTEGER("&c&l(!) &c{id} isn't an integer!"),
    NO_PERMISSION("&c&l(!) &cYou don't have permission to do this!"),
    TRENCHTOOL_NOT_FOUND("&c&l(!) &c{name} is not a valid Trench Tool, do /trenchtool list for more info."),
    TRENCHTOOL_GIVE("&e&l(!) &eYou've given &d{player} &e{amount} &d{type}");

    private String message;


    Messages(String message) {
        this.message = message;
    }

    public String getVal() {
        return message;
    }

    public String getKey() {
        return name().toLowerCase().replace("_", "-");
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
