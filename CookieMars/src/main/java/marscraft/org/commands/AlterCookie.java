package marscraft.org.commands;

import marscraft.org.CookieMars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AlterCookie implements CommandExecutor, TabCompleter {

    public AlterCookie(CookieMars cookieMars) {
        cookieMars.getCommand("altercookie").setExecutor(this);
        cookieMars.getCommand("altercookie").setTabCompleter(this);
    }

    private Integer changeAmount;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("marscraft.cookie.alter")) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("InvalidPermission").replaceAll("&", "§"));
            return true;
        }
        if (args.length != 3) {
sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("AlterCookie.MissingArgument").replaceAll("&", "§"));
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Alter.Cookie.Example").replaceAll("&", "§"));
            return true;
        }
        try {
            changeAmount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("AlterCookie.InvalidNumber").replaceAll("&", "§"));
            return true;
        }
        FileConfiguration databaseConfig = CookieMars.getInstance().getDatabaseConfig().getConfig();
        switch (args[1]) {
            case "add":
                databaseConfig.set("staff." + args[0] + ".cookie", databaseConfig.getInt("staff." + args[0] + ".cookie") + changeAmount);
                CookieMars.getInstance().getDatabaseConfig().saveConfig();
                sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("AlterCookie.SuccessAdded").replaceAll("&", "§")
                        .replaceAll("%player%", args[1])
                        .replaceAll("%amount%", changeAmount.toString()));
                return true;
            case "remove":
                databaseConfig.set("staff." + args[0] + ".cookie", databaseConfig.getInt("staff." + args[0] + ".cookie") - changeAmount);
                CookieMars.getInstance().getDatabaseConfig().saveConfig();
                sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("AlterCookie.SuccessRemoved").replaceAll("&", "§")
                        .replaceAll("%player%", args[1])
                        .replaceAll("%amount%", changeAmount.toString()));
                return true;
            case "set":
                databaseConfig.set("staff." + args[0] + ".cookie", changeAmount);
                CookieMars.getInstance().getDatabaseConfig().saveConfig();
                sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("AlterCookie.SuccessSet").replaceAll("&", "§")
                        .replaceAll("%player%", args[1])
                        .replaceAll("%amount%", changeAmount.toString()));
                return true;
            default:
                sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("AlterCookie.InvalidFunction").replaceAll("&", "§")
                        .replaceAll("%player%", args[1])
                        .replaceAll("%amount%", changeAmount.toString()));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> tabComplete = new ArrayList<String>();
        if (sender.hasPermission("marscraft.cookie.alter")) {
            switch (args.length) {
                case 2:
                    tabComplete.clear();
                    if ("add".toLowerCase().contains(args[1].toLowerCase())) {
                        tabComplete.add("add");
                    }
                    if ("set".toLowerCase().contains(args[1].toLowerCase())) {
                        tabComplete.add("set");
                    }
                    if ("remove".toLowerCase().contains(args[1].toLowerCase())) {
                        tabComplete.add("remove");
                    }
                    return tabComplete;
                case 3:
                    tabComplete.clear();
                    if ("1".contains(args[2])) {
                        tabComplete.add("1");
                    }
                    if ("2".contains(args[2])) {
                        tabComplete.add("2");
                    }
                    if ("5".contains(args[2])) {
                        tabComplete.add("5");
                    }
                    if ("10".contains(args[2])) {
                        tabComplete.add("10");
                    }
                    if ("20".contains(args[2])) {
                        tabComplete.add("20");
                    }
                    return tabComplete;
            }
        }
        return null;
    }
}
