package net.thejrdev.kits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class KitCommand implements CommandExecutor {

    private KitPlugin plugin;

    public KitCommand(KitPlugin plugin){
        plugin.getCommand("kit").setExecutor(this);
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){

        if (sender instanceof Player player) {
            if (args.length > 0) {
                Kit kit = Kit.getKit(args[0]);
                if (kit == null) {
                    player.sendMessage(ChatColor.RED + "Not a valid kit name");
                } else {
                    kit.apply(player);
                }
            }else {
                player.sendMessage(ChatColor.RED + "You must provide a kit name");
            }
        }
        return true;
    }
}