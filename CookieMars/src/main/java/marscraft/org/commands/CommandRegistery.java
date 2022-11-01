package marscraft.org.commands;

import marscraft.org.CookieMars;
import marscraft.org.commands.cookie.Cookie;

public class CommandRegistery {
    public CommandRegistery(CookieMars cookieMars) {
        new Vote(cookieMars);
        new Cookie(cookieMars);
        new CookieLeaderboard(cookieMars);
        new AlterCookie(cookieMars);
    }
}
