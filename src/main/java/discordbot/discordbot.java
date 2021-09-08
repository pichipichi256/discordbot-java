package discordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class discordbot extends ListenerAdapter {
    private static JDA jda = null;
    private static final String TOKEN = System.getenv("discord_bot_token");
    public static void main(String[] args) {
        try {
            jda = JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_MESSAGES)
                    .setRawEventsEnabled(true)
                    .addEventListeners(new discordbot())
                    .setActivity(Activity.playing("WTF"))
                    .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String msg = e.getMessage().getContentRaw();
        System.out.println("["+e.getGuild().getName()+"]["+e.getTextChannel().getName()+"]["+e.getAuthor().getName()+"]"+msg);
        if (!e.getAuthor().isBot() && msg.startsWith("&")) {
            e.getTextChannel().sendMessage(msg).queue();
        }
    }
}

