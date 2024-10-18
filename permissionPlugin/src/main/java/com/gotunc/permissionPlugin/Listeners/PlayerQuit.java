package com.gotunc.permissionPlugin.Listeners;

import com.gotunc.permissionPlugin.PermissionPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) // oyuncu sunucudan çıkınca oyuncunun verilerini siliyoruz
    {
        PermissionPlugin.playerPermissions.get(event.getPlayer().getName()).isLogin = false;
    }

}