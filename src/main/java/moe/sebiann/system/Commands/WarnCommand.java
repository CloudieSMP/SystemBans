package moe.sebiann.system.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import moe.sebiann.system.Enums.Severity;
import moe.sebiann.system.Objects.Warning;
import moe.sebiann.system.SystemBans;
import moe.sebiann.system.Util.WarningInventoryHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@CommandAlias("warn")
@CommandPermission("system.admin.warn")
public class WarnCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    void warnPlayer(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage(Component.text("You must be a player to use this command!").color(TextColor.fromHexString("#FF5555")));
            return;
        }

        if(args.length < 1){
            player.sendMessage(Component.text("Usage: /warn <player>").color(TextColor.fromHexString("#FF5555")));
            return;
        }

//        if(args[0].equalsIgnoreCase(player.getName())){
//            player.sendMessage(Component.text("You cannot warn yourself!").color(TextColor.fromHexString("#FF5555")));
//            return;
//        }

        String playerName = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);

        Inventory inv = WarningInventoryHandler.getWarningCategoryInventory(target);
        player.openInventory(inv);

        Bukkit.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event){
                if(event.getClickedInventory() == null) return;
                if(!event.getClickedInventory().equals(inv)) return;
                if(event.getCurrentItem() == null) return;
                if(event.getCurrentItem().getItemMeta() == null) return;

                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();

            }
        }, SystemBans.instance);
    }

    @Subcommand("get")
    void getWarning(CommandSender sender, String[] args){
        if(!(sender instanceof Player player)){
            sender.sendMessage(Component.text("You must be a player to use this command!").color(TextColor.fromHexString("#FF5555")));
            return;
        }

        if(args.length < 1){
            player.sendMessage(Component.text("Usage: /warn get <player>").color(TextColor.fromHexString("#FF5555")));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        List<Warning> warnings = Warning.getWarnings(target);
        for(Warning w : warnings){
            player.sendMessage(Component.text("-" + w.getReason()).color(TextColor.fromHexString("#FF5555")));
        }
    }

}
