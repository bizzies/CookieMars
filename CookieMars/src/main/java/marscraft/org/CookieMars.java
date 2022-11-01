package marscraft.org;

import marscraft.org.api.API;
import marscraft.org.commands.CommandRegistery;
import marscraft.org.configfiles.CreateNewConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CookieMars extends JavaPlugin {

    private final static Logger logger = Bukkit.getLogger();
    private static CookieMars instance;
    private CreateNewConfig sysConfig;
    private CreateNewConfig langConfig;
    private CreateNewConfig databaseConfig;
    public API api;



    @Override
    public void onEnable() {
        instance = this;
        logger.log(Level.INFO, "  Loading Config Files");
        sysConfig = new CreateNewConfig("config.yml");
        langConfig = new CreateNewConfig("lang.yml");
        databaseConfig = new CreateNewConfig("database.yml");
        new CommandRegistery(this);
        api = new API();
    }


    public static CookieMars getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public CreateNewConfig getSysConfig() {
        return sysConfig;
    }

    public CreateNewConfig getLangConfig() {
        return langConfig;
    }

    public CreateNewConfig getDatabaseConfig() {
        return databaseConfig;
    }
}
