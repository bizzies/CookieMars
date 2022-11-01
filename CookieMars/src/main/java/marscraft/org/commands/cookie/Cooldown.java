package marscraft.org.commands.cookie;

import marscraft.org.CookieMars;

import java.util.HashMap;

public class Cooldown {

    private HashMap<String,Integer> playerCooldown = Cookie.getPlayerCooldown();
    private HashMap<String,Integer> staffCooldown = Cookie.getStaffCooldown();
    private Integer id;

    public Cooldown() {
        id = CookieMars.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(CookieMars.getInstance(), () -> {
            if (playerCooldown != null) {
                for (String i : playerCooldown.keySet()) {
                    if (playerCooldown.get(i) != 0) {
                        playerCooldown.put(i, playerCooldown.get(i) - 1);
                    } else {
                        playerCooldown.remove(i);
                    }
                }
            }
            if (staffCooldown != null) {
                for (String i : staffCooldown.keySet()) {
                    if (staffCooldown.get(i) != 0) {
                        staffCooldown.put(i, staffCooldown.get(i) - 1);
                    }else {
                        staffCooldown.remove(i);
                    }
                }
            }
        }, 0, 20);
    }
}
