package com.gotunc.permissionPlugin.Listeners;

import com.gotunc.permissionPlugin.PermissionPlugin;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerSendMessage implements Listener {

    @EventHandler
    public void onPlayerSendMessage(AsyncPlayerChatEvent event) // oyuncu mesaj gönderdiğinde adını gruba göre değiştirir
    {
        // Player sent a message
        String playerName = event.getPlayer().getName();
        if (PermissionPlugin.playerPermissions.get(playerName).isLogin)
            event.setFormat(ChatColor.GREEN + "[" + PermissionPlugin.playerPermissions.get(event.getPlayer().getName()).group + "]" + ChatColor.WHITE + event.getFormat().replace("<", " ").replace(">", ":"));
        else
            event.setFormat(ChatColor.GREEN + "[" + PermissionPlugin.defaultGroup + "]" + ChatColor.WHITE + event.getFormat().replace("<", " ").replace(">", ":"));
    }
}