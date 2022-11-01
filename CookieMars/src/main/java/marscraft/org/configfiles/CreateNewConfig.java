package marscraft.org.configfiles;

import marscraft.org.CookieMars;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateNewConfig {
    private File configFile;
    private FileConfiguration config;

    public CreateNewConfig(String configFilePath) {
        configFile = new File(CookieMars.getInstance().getDataFolder(), configFilePath);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            CookieMars.getInstance().saveResource(configFilePath, false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public File getConfigFile() {
        return configFile;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        try {
            config.save(configFile);
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
