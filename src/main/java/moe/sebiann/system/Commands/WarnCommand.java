package moe.sebiann.system.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import moe.sebiann.system.Enums.Severity;
import moe.sebiann.system.Objects.Warning;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@CommandAlias("warn")
@CommandPermission("system.admin.warn")
public class WarnCommand extends BaseCommand {

    @Default
    @CommandPermission("@players")
    void warnPlayer(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage(Component.text("You must be a player to use this command!").color(TextColor.fromHexString("#FF5555")));
            return;
        }

        if(args.length < 2){
            player.sendMessage(Component.text("Usage: /warn <player> <reason>").color(TextColor.fromHexString("#FF5555")));
            return;
        }

        String playerName = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        Warning warning = new Warning(target, player, Severity.LOW, reason);
        warning.save();
    }

}
