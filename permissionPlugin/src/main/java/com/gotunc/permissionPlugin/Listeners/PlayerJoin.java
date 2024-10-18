package com.gotunc.permissionPlugin.Listeners;

import com.gotunc.permissionPlugin.DatabaseProcesses;
import com.gotunc.permissionPlugin.PermissionManager;
import com.gotunc.permissionPlugin.PermissionPlugin;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoin implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) // oyuncu sunucuya girince eğer oyuncunun grubu yoksa default grubu atıyoruz
    {
        Player player = event.getPlayer();
        if (PermissionPlugin.playerPermissions.containsKey(player.getName()))
            PermissionPlugin.playerPermissions.get(player.getName()).isLogin = false;
        else
            DatabaseProcesses.setNewUsersGroup(player.getName());
        if (player.getGameMode() != GameMode.SPECTATOR)
            player.setGameMode(GameMode.SPECTATOR);
        PermissionManager.loadPermissionsForPlayer(player);
    }
}