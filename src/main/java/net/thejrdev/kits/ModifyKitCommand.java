package net.thejrdev.kits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModifyKitCommand implements CommandExecutor {


    private KitPlugin plugin;

    public ModifyKitCommand(KitPlugin plugin){
        plugin.getCommand("kitmodify").setExecutor(this);
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player player){

            if(args.length > 1){

                switch (args[0]){

                    case "set", "s" -> {
                        new Kit(args[1], player.getInventory(), plugin);
                        player.sendMessage(ChatColor.GREEN + "Added new kit as " + args[1]);
                    }
                    case "remove", "r" -> {
                        Kit.remove(args[1]);
                    }

                }


            }else {


            }

        }
        return true;
    }
}
