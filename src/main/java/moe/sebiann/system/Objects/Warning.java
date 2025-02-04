package moe.sebiann.system.Objects;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import moe.sebiann.system.Enums.Severity;
import moe.sebiann.system.Enums.WarningCategory;
import moe.sebiann.system.SystemBans;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//TODO: Add category (Chat, Gameplay, etc) to warnings
public class Warning {

    static Gson gson = new Gson().newBuilder()
            .setPrettyPrinting()
            .create();
    transient Path path;

    transient OfflinePlayer player;
    transient OfflinePlayer warner;

    UUID playerUUID;
    String reason;
    UUID warnerUUID;
    Severity severity;
    WarningCategory category;
    float warningCreatedUnixTime;

    //<editor-fold desc="Constructors">
    public Warning(OfflinePlayer player, OfflinePlayer warner, Severity severity, WarningCategory category, String reason, float warningCreatedUnixTime) {
        this.player = player;
        this.warner = warner;
        this.playerUUID = player.getUniqueId();
        this.warnerUUID = warner.getUniqueId();
        this.severity = severity;
        this.category = category;
        this.reason = reason;
        this.warningCreatedUnixTime = warningCreatedUnixTime;

        path = Path.of(SystemBans.instance.getDataFolder() + "/warnings/" + playerUUID + ".json");
    }

    public Warning(Player player, Player warner, Severity severity, WarningCategory category, String reason, float warningCreatedUnixTime) {
        this.player = player;
        this.warner = warner;
        this.playerUUID = player.getUniqueId();
        this.warnerUUID = warner.getUniqueId();
        this.severity = severity;
        this.category = category;
        this.reason = reason;
        this.warningCreatedUnixTime = warningCreatedUnixTime;

        path = Path.of(SystemBans.instance.getDataFolder() + "/warnings/" + playerUUID + ".json");
    }

    public Warning(OfflinePlayer player, OfflinePlayer warner, Severity severity, WarningCategory category, String reason) {
        this.player = player;
        this.warner = warner;
        this.playerUUID = player.getUniqueId();
        this.warnerUUID = warner.getUniqueId();
        this.severity = severity;
        this.category = category;
        this.reason = reason;
        this.warningCreatedUnixTime = System.currentTimeMillis();

        path = Path.of(SystemBans.instance.getDataFolder() + "/warnings/" + playerUUID + ".json");
    }

    public Warning(Player player, Player warner, Severity severity, WarningCategory category, String reason) {
        this.player = player;
        this.warner = warner;
        this.playerUUID = player.getUniqueId();
        this.warnerUUID = warner.getUniqueId();
        this.severity = severity;
        this.category = category;
        this.reason = reason;
        this.warningCreatedUnixTime = System.currentTimeMillis();

        path = Path.of(SystemBans.instance.getDataFolder() + "/warnings/" + playerUUID + ".json");
    }

    public Warning(UUID player, UUID warner, Severity severity, WarningCategory category, String reason, float warningCreatedUnixTime) {
        this.player = Bukkit.getOfflinePlayer(player);
        this.warner = Bukkit.getOfflinePlayer(warner);
        this.playerUUID = player;
        this.warnerUUID = warner;
        this.severity = severity;
        this.category = category;
        this.reason = reason;
        this.warningCreatedUnixTime = warningCreatedUnixTime;

        path = Path.of(SystemBans.instance.getDataFolder() + "/warnings/" + playerUUID + ".json");
    }

    public Warning(UUID player, UUID warner, Severity severity, WarningCategory category, String reason) {
        this.player = Bukkit.getOfflinePlayer(player);
        this.warner = Bukkit.getOfflinePlayer(warner);
        this.playerUUID = player;
        this.warnerUUID = warner;
        this.severity = severity;
        this.category = category;
        this.reason = reason;
        this.warningCreatedUnixTime = System.currentTimeMillis();

        path = Path.of(SystemBans.instance.getDataFolder() + "/warnings/" + playerUUID + ".json");
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public OfflinePlayer getPlayer() {
        return player;
    }

    public OfflinePlayer getWarner() {
        return warner;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public UUID getWarnerUUID() {
        return warnerUUID;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getReason() {
        return reason;
    }

    public float getWarningCreatedUnixTime() {
        return warningCreatedUnixTime;
    }

    public Date getWarningCreatedDate() {
        return new Date(Long.parseLong(String.valueOf(warningCreatedUnixTime)));
    }
    //</editor-fold>

    /**
     * Save the warning to a file under the player
     */
    public void save(){
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Warning> warnings = getWarnings(playerUUID);
        warnings.add(this);

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(warnings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Warning> getWarnings(UUID playerUUID){
        Path path = Path.of(SystemBans.instance.getDataFolder() + "/warnings/" + playerUUID + ".json");
        List<Warning> warnings = new ArrayList<>();

        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                Type listType = new TypeToken<List<Warning>>() {}.getType();
                warnings = gson.fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return warnings != null ? warnings : new ArrayList<>();
    }

    public static List<Warning> getWarnings(@NotNull Player player){
        return getWarnings(player.getUniqueId());
    }
    public static List<Warning> getWarnings(@NotNull OfflinePlayer player){
        return getWarnings(player.getUniqueId());
    }

}
