package com.gotunc.permissionPlugin.Listeners;

import com.gotunc.permissionPlugin.PermissionManager;
import com.gotunc.permissionPlugin.PermissionPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.permissions.PermissionAttachment;

import javax.xml.crypto.Data;
import java.util.List;

public class PlayerChangeGamemode implements Listener {

    @EventHandler
    public void onPlayerChangeGamemode(PlayerGameModeChangeEvent event) {
        // Player changed gamemode
        if (event.getNewGameMode() == GameMode.SURVIVAL)
            PermissionPlugin.playerPermissions.get(event.getPlayer().getName()).isLogin = true;
        else
            PermissionPlugin.playerPermissions.get(event.getPlayer().getName()).isLogin = false;
        PermissionManager.loadPermissionsForPlayer(event.getPlayer());
    }
}