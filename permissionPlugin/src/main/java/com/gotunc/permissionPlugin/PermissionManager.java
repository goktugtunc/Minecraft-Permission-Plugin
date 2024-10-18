package com.gotunc.permissionPlugin;

import com.gotunc.permissionPlugin.Classes.Groups;
import com.gotunc.permissionPlugin.Classes.PlayerPermissionClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;

public class PermissionManager {

    public static void resetUserPermissions(Player player) // kullanıcının izinlerini sıfırlar
    {
        if (PermissionPlugin.playerPermissions.containsKey(player.getName()))
        {
            String group = PermissionPlugin.playerPermissions.get(player.getName()).group;
            List<Groups> permissions = PermissionPlugin.groups.get(group);
            PermissionAttachment attachment = player.addAttachment(PermissionPlugin.getInstance());
            for (Groups permission : permissions)
            {
                attachment.setPermission(permission.permission, false);
            }
            player.updateCommands();
        }
    }

    public static void loadPermissionsForPlayer(Player player) // oyuncunun grubuna göre izinlerini yükler, eğer grup yoksa default grubu yükler
    {
        if (PermissionPlugin.playerPermissions.get(player.getName()).isLogin)
        {
            resetUserPermissions(player);
            PlayerPermissionClass permissionClass = PermissionPlugin.playerPermissions.get(player.getName());
            List<Groups> permissions = PermissionPlugin.groups.get(permissionClass.group);
            PermissionAttachment attachment = player.addAttachment(PermissionPlugin.getInstance());
            for (Groups permission : permissions)
            {
                attachment.setPermission(permission.permission, true);
            }
            player.updateCommands();
            player.setPlayerListName(ChatColor.GREEN + "[" + permissionClass.group + "] " + ChatColor.WHITE + player.getName());
        }
        else
            setTemporaryGroup(player, PermissionPlugin.defaultGroup);
    }

    public static void setTemporaryGroup(Player player, String group) // oyuncunun geçici olarak grubunu değiştirir
    {
        List<Groups> permissions = PermissionPlugin.groups.get(group);
        PermissionAttachment attachment = player.addAttachment(PermissionPlugin.getInstance());
        for (Groups permission : permissions) {
            attachment.setPermission(permission.permission, true);
        }
        player.updateCommands();
        player.setPlayerListName(ChatColor.GREEN + "[" + group + "] " + ChatColor.WHITE + player.getName());
    }

}