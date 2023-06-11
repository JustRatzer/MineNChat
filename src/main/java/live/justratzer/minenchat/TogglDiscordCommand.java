package live.justratzer.minenchat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TogglDiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.DiscordToggle.contains(player.getUniqueId())){
                main.DiscordToggle.remove(player.getUniqueId());
                player.sendMessage(ChatColor.RED + "Toggled discord linked chat off");
            }else{
                main.DiscordToggle.add(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Toggled discord linked chat on");
            }
        }

        return false;
    }
}
