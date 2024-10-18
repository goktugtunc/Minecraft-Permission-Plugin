package com.gotunc.permissionPlugin;

import com.gotunc.permissionPlugin.Classes.PlayerPermissionClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseProcesses
{

    public static void fillPlayerGroupMaps()
    {
        String query = "SELECT * FROM PlayerPermissions";
        try
        {
            PreparedStatement statement = PermissionPlugin.connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                PlayerPermissionClass permissionClass = new PlayerPermissionClass(false, rs.getString("GroupName"));
                PermissionPlugin.playerPermissions.put(rs.getString("GameName"), permissionClass);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNewUsersGroup(String playerName) {
        String query = "INSERT INTO PlayerPermissions (GameName, GroupName) VALUES (?, ?)";
        try {
            PreparedStatement statement = PermissionPlugin.connection.prepareStatement(query);
            statement.setString(1, playerName);
            statement.setString(2, PermissionPlugin.defaultGamerGroup);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PermissionPlugin.playerPermissions.put(playerName, new PlayerPermissionClass(false, PermissionPlugin.defaultGroup));
    }

    public static void changePlayerGroup(String playerName, String group) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null)
            return;
        String query = "UPDATE PlayerPermissions SET GroupName = ? WHERE GameName = ?";
        try {
            PreparedStatement statement = PermissionPlugin.connection.prepareStatement(query);
            statement.setString(1, group);
            statement.setString(2, playerName);
            statement.executeUpdate();
            PermissionPlugin.playerPermissions.get(playerName).group = group;
            PermissionManager.loadPermissionsForPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}