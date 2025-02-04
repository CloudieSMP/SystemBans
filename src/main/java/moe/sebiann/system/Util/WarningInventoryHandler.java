package moe.sebiann.system.Util;

import jdk.jfr.Category;
import moe.sebiann.system.Enums.WarningCategory;
import moe.sebiann.system.Objects.Warning;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WarningInventoryHandler {

    public static Inventory getWarningCategoryInventory(@NotNull OfflinePlayer target){

        Inventory inv = Bukkit.createInventory(null, 9, Component.text("Warning Categories - " + target.getName()));
        inv.setItem(0, new ItemStack(Material.BARRIER));
        inv.setItem(1, new ItemStack(Material.BARRIER));

        for(WarningCategory category : WarningCategory.values()){
            inv.addItem(WarningCategoryItem(category, target.getName()));
        }

        inv.setItem(0, new ItemStack(Material.AIR));
        inv.setItem(1, new ItemStack(Material.AIR));
        return inv;
    }

    public static ItemStack WarningCategoryItem(WarningCategory category, String targetName){
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(category.toString()));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Click to warn " + targetName + " for " + category).color(TextColor.fromHexString("#FFAA00")));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
