package marscraft.org.commands;

import marscraft.org.CookieMars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

import java.util.concurrent.atomic.AtomicInteger;

public class CookieLeaderboard implements CommandExecutor {

    public CookieLeaderboard(CookieMars cookieMars) {
        cookieMars.getCommand("cookieleaderboard").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("marscraft.cookieleaderboard")) {
            sender.sendMessage(CookieMars.getInstance().getLangConfig().getConfig().getString("InvalidPermission").replaceAll("&", "§"));
            return true;
        }
        ConfigurationSection cf = CookieMars.getInstance().getDatabaseConfig().getConfig().getConfigurationSection("staff");
        cf.getValues(false)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    int points1 = ((MemorySection) a1.getValue()).getInt("cookie");
                    int points2 = ((MemorySection) a2.getValue()).getInt("cookie");
                    return points2 - points1;
                })
                .forEach(f -> {
                    int points = ((MemorySection) f.getValue()).getInt("cookie");
                    sender.sendMessage(("&4&l|&2&l| §f" + f.getKey() + " §f- §a" + points));
                });
        return true;
    }
}
