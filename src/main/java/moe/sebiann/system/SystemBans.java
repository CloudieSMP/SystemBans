package moe.sebiann.system;

import co.aikar.commands.PaperCommandManager;
import moe.sebiann.system.Commands.WarnCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SystemBans extends JavaPlugin {

    public static SystemBans instance;

    @Override
    public void onEnable() {
        if(instance == null){
            instance = this;
        }

        registerCommands();

    }

    public void registerCommands(){
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new WarnCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
