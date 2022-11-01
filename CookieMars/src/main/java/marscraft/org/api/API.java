package marscraft.org.api;


import marscraft.org.CookieMars;
import org.bukkit.entity.Player;

public class API {
    public String getCookies(Player player) {
        if (CookieMars.getInstance().getDatabaseConfig().getConfig().get(player.getName()) != null) {
            return String.valueOf(CookieMars.getInstance().getDatabaseConfig().getConfig().getInt(player.getName()));
        }
        return "0";
    }
}
