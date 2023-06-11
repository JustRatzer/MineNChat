package live.justratzer.minenchat;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class main extends JavaPlugin implements Listener {


    public HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public static List<UUID> DiscordToggle = new ArrayList<UUID>();


    @Override
    public void onEnable() {

        getCommand("togglediscord").setExecutor(new TogglDiscordCommand());

        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        discord.main(null, this.getConfig().getString("token"));
        try {
            sendStart();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        channelId = this.getConfig().getString("channel_id");
    }

    public static String channelId;

    @Override
    public void onDisable() {
        try {
            sendClose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void sendDiscMessage(String name, String denom,String message){
        World world = Bukkit.getWorld("world");

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(DiscordToggle.contains(player.getUniqueId())) {
                player.sendMessage("<" + ChatColor.AQUA + "DISCORD" + " " + ChatColor.RESET + name + "#" + denom + "> " + message);
            }
        }
    }
    private DiscordWebhook webhook = new DiscordWebhook(this.getConfig().getString("webhook"));

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        Player player = e.getPlayer();



        DiscordToggle.add(player.getUniqueId());

        webhook.setAvatarUrl("https://visage.surgeplay.com/bust/" + player.getUniqueId());
        webhook.setUsername(player.getName());
        webhook.setContent(player.getName() + " Has joined the server");
        webhook.execute();
    }

    public void sendStart() throws IOException{
        webhook.setAvatarUrl("https://theme.zdassets.com/theme_assets/2155033/bc270c23058d513de5124ffea6bf9199af7a2370.png");
        webhook.setUsername("Console");
        webhook.setContent("The server is up!");
        webhook.execute();
    }

    public void sendClose() throws IOException{
        webhook.setAvatarUrl("https://theme.zdassets.com/theme_assets/2155033/bc270c23058d513de5124ffea6bf9199af7a2370.png");
        webhook.setUsername("Console");
        webhook.setContent("The server is down!");
        webhook.execute();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) throws IOException {
        Player player = e.getPlayer();


        if(DiscordToggle.contains(player.getUniqueId())) {
            DiscordToggle.remove(player.getUniqueId());
        }

        webhook.setAvatarUrl("https://visage.surgeplay.com/bust/" + player.getUniqueId());
        webhook.setUsername(player.getName());
        webhook.setContent(player.getName() + " Has left the server");
        webhook.execute();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) throws IOException {
        Player player = e.getEntity();
        if(DiscordToggle.contains(player.getUniqueId())) {
            webhook.setAvatarUrl("https://visage.surgeplay.com/bust/" + player.getUniqueId());
            webhook.setUsername(player.getName());
            webhook.setContent(e.getDeathMessage());
            webhook.execute();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) throws IOException{
        Player player = e.getPlayer();
        if(DiscordToggle.contains(player.getUniqueId())) {
            webhook.setAvatarUrl("https://visage.surgeplay.com/bust/" + player.getUniqueId());
            webhook.setUsername(player.getName());
            webhook.setContent(e.getMessage());
            webhook.execute();
        }
    }
    
}
