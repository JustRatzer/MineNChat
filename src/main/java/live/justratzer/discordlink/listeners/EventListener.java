package live.justratzer.discordlink.listeners;

import live.justratzer.discordlink.main;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String name = event.getMessage().getAuthor().getName();
        String descrim = event.getMessage().getAuthor().getDiscriminator();
        String message = event.getMessage().getContentRaw();
        User user = event.getAuthor();
        Channel Channel = event.getMessage().getChannel();
        Channel linkChannel = event.getGuild().getTextChannelById(main.channelId);



        System.out.println();

        if(!user.isBot() && !event.getMessage().isWebhookMessage()) {
            if (Channel == linkChannel) {
                main.sendDiscMessage(name, descrim, message);
            }
        }
    }
}
