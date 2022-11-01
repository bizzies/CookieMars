package marscraft.org.commands.cookie;

import marscraft.org.CookieMars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Cookie implements CommandExecutor, TabCompleter {

    public Cookie(CookieMars cookieMars) {
        cookieMars.getCommand("cookie").setExecutor(this);
        cookieMars.getCommand("cookie").setTabCompleter(this);
    }

    private static HashMap<String, Integer> playerCooldown = new HashMap<String, Integer>();
    private static HashMap<String, Integer> staffCooldown = new HashMap<String, Integer>();
    private HashMap<String, Integer> cooldowns = new HashMap<String, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("marscraft.cookie")) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("InvalidPermission").replaceAll("&", "§"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.MissingArgument").replaceAll("&", "§"));
            return true;
        }
        Player staffMember = Bukkit.getPlayer(args[0]);
        if (staffMember == null) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.StaffOffline").replaceAll("&", "§"));
            return true;
        }
        if (!staffMember.hasPermission("marscraft.staff")) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.MissingArgument").replaceAll("&", "§"));
            return true;
        }
        if (sender.hasPermission("marscraft.staff")) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.StaffGiveCookie").replaceAll("&", "§"));
            return true;
        }
        FileConfiguration sysConfig = CookieMars.getInstance().getSysConfig().getConfig();
        if (playerCooldown.containsKey(sender.getName())) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.CooldownMessage").replaceAll("&", "§")
                    .replaceAll("%remaining time%", playerCooldown.get(sender.getName()) / 60 + " minutes"));
            return true;
        }
        if (staffCooldown.containsKey(staffMember.getName())) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.StaffCooldown").replaceAll("&", "§")
                    .replaceAll("%staff%", staffMember.getName()));
            return true;
        }
        playerCooldown.put(sender.getName(), sysConfig.getInt("CookiePlayerTimerInSeconds"));
        staffCooldown.put(staffMember.getName(), sysConfig.getInt("CookieStaffTimerInSeconds"));
        new Cooldown();
        addCookie(staffMember.getName());
        sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.PlayerGiveCookie").replaceAll("&", "§")
                .replaceAll("%staff%", staffMember.getName())
                .replaceAll("%total_cookie%", String.valueOf(CookieMars.getInstance().getDatabaseConfig().getConfig().getInt("staff." + staffMember.getName() + ".cookie"))));
        staffMember.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("Cookie.StaffGetCookie").replaceAll("&", "§")
                .replaceAll("%player%", sender.getName())
                .replaceAll("%total_cookie%", String.valueOf(CookieMars.getInstance().getDatabaseConfig().getConfig().getInt("staff." + staffMember.getName() + ".cookie"))));
        return true;
    }

    private void addCookie(String staffName) {
        FileConfiguration config = CookieMars.getInstance().getDatabaseConfig().getConfig();
        if (config.get(staffName) == null) {
            config.set("staff." + staffName + ".cookie", 1);
            CookieMars.getInstance().getDatabaseConfig().saveConfig();
            return;
        }
        config.set("staff." + staffName + ".cookie", config.getInt("staff." + staffName + ".cookie") + 1);
        CookieMars.getInstance().getDatabaseConfig().saveConfig();
    }

    public static HashMap<String, Integer> getPlayerCooldown() {
        return playerCooldown;
    }

    public static HashMap<String, Integer> getStaffCooldown() {
        return staffCooldown;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> tabComplete = new ArrayList<String>();
        if (args.length == 1) {
            tabComplete.clear();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("marscraft.staff")){
                    if (player.getName().toLowerCase().contains(args[0].toLowerCase())) {
                        tabComplete.add(player.getName());
                    }
                }
            }
            return tabComplete;
        }
        return null;
    }
}
