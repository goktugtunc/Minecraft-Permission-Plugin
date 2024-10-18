package com.gotunc.permissionPlugin;

import com.gotunc.permissionPlugin.Classes.Groups;
import com.gotunc.permissionPlugin.Classes.PlayerPermissionClass;
import com.gotunc.permissionPlugin.Listeners.PlayerChangeGamemode;
import com.gotunc.permissionPlugin.Listeners.PlayerJoin;
import com.gotunc.permissionPlugin.Listeners.PlayerQuit;
import com.gotunc.permissionPlugin.Listeners.PlayerSendMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public final class PermissionPlugin extends JavaPlugin {
// plugin testi yapılacak
    public static Connection connection;
    public static JavaPlugin instance;
    public static HashMap<String, PlayerPermissionClass> playerPermissions = new HashMap<>();
    public static HashMap<String, List<Groups>> groups = new HashMap<>();
    public static String defaultGroup = "Ziyaretci";
    public static String defaultGamerGroup = "Oyuncu";

    private void setCommandColor() {
        for (String group : groups.keySet())
        {
            List<Groups> permissions = groups.get(group);
            for (Groups permission : permissions)
            {
                if (getCommand(permission.command) != null)
                    getCommand(permission.command).setPermission(permission.permission);
            }
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        fileProcesses.copyDefaultConfigIfNotExists();
        fileProcesses.fillGroups();
        setCommandColor();
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://xxx.xxx:xxx/xx", "xxx", "xxx");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseProcesses.fillPlayerGroupMaps();

        Bukkit.getPluginManager().registerEvents(new PlayerChangeGamemode(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSendMessage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        getCommand("yetki").setExecutor(new GroupCommand());

        getLogger().info("Permission Plugin Enabled");
    }

    @Override
    public void onDisable() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static String getPlayerLanguage(String GameName) {
        String query = "SELECT Language FROM PlayerInformations WHERE GameName = ?";
        try {
            PreparedStatement statement = PermissionPlugin.connection.prepareStatement(query);
            statement.setString(1, GameName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getString("Language");
            else
                return "EN";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "EN";
    }
}
