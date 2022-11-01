package marscraft.org.commands;

import marscraft.org.CookieMars;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Vote implements CommandExecutor {

    public Vote(CookieMars cookieMars) {
        cookieMars.getCommand("vote").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("marscraft.vote")) {
            CookieMars.getInstance().getLangConfig().getConfig().getString("InvalidPermission").replaceAll("&", "§");
            return true;
        }
        FileConfiguration langConfig = CookieMars.getInstance().getLangConfig().getConfig();
        for (String i : langConfig.getConfigurationSection("Vote").getKeys(false)) {
            TextComponent message = new TextComponent(langConfig.getString("Vote." + i + ".Text").replaceAll("&", "§"));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, langConfig.getString("Vote." + i + ".URL")));
        }
        return true;
    }
}
