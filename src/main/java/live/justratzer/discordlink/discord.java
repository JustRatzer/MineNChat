package live.justratzer.discordlink;

import live.justratzer.discordlink.listeners.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;


public class discord {

    private final ShardManager shardManager;

    public discord(String token){
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(token);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();
        shardManager.addEventListener(new EventListener());

    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args, String token){
        discord bot = new discord(token);

    }

}
