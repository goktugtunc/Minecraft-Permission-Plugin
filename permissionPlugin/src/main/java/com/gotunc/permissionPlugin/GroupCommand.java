package com.gotunc.permissionPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GroupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getLogger().info("Bu komut sadece oyuncular tarafından kullanılabilir.");
        }
        else {
            Player player = (Player) commandSender;
            if (strings.length == 1 && strings[0].equalsIgnoreCase("listele")) {
                if (PermissionPlugin.getPlayerLanguage(player.getName()).equalsIgnoreCase("TR"))
                    player.sendMessage(ChatColor.GREEN + "Yetki Listesi: ");
                else
                    player.sendMessage(ChatColor.GREEN + "Group List: ");
                for (String group : PermissionPlugin.groups.keySet()) {
                    player.sendMessage(ChatColor.YELLOW + group);
                }
            }
            else if (strings.length == 3 && strings[0].equalsIgnoreCase("ver")) {
                String playerName = player.getName();
                String playerLang = PermissionPlugin.getPlayerLanguage(playerName);
                if (PermissionPlugin.groups.containsKey(strings[2]))
                {
                    if (Bukkit.getPlayer(strings[1]) != null)
                    {
                        Player target = Bukkit.getPlayer(strings[1]);
                        if (target.getGameMode() != GameMode.SURVIVAL)
                        {
                            if (playerLang.equalsIgnoreCase("TR"))
                                player.sendMessage(ChatColor.RED + "Oyuncu hayatta kalma modunda değilken yetki veremezsiniz!.");
                            else
                                player.sendMessage(ChatColor.RED + "Player is not in survival mode.");
                            return true;
                        }
                        String targetName = target.getName();
                        String targetLang = PermissionPlugin.getPlayerLanguage(targetName);
                        DatabaseProcesses.changePlayerGroup(targetName, strings[2]);
                        if (playerLang.equalsIgnoreCase("TR"))
                            player.sendMessage(ChatColor.GREEN + "[SUNUCU] " + ChatColor.AQUA + strings[2] + ChatColor.GREEN + " grubu " + ChatColor.AQUA + strings[1] + ChatColor.GREEN + " adlı oyuncuya verildi.");
                        else
                            player.sendMessage(ChatColor.GREEN + "[SERVER] " + ChatColor.AQUA + strings[2] + ChatColor.GREEN + " group was given to " + ChatColor.AQUA + strings[1] + ChatColor.GREEN + " player.");
                        if (targetLang.equalsIgnoreCase("TR"))
                            target.sendMessage(ChatColor.GREEN + "[SUNUCU] " + ChatColor.AQUA + playerName + ChatColor.GREEN + " adlı kullanıcı size " + ChatColor.AQUA + strings[2] + ChatColor.GREEN + " grubunu verdi.");
                        else
                            target.sendMessage(ChatColor.GREEN + "[SERVER] " + ChatColor.AQUA + playerName + ChatColor.GREEN + " user gave you the " + ChatColor.AQUA + strings[2] + ChatColor.GREEN + " group.");
                    }
                    else
                    {
                        if (playerLang.equalsIgnoreCase("TR"))
                            player.sendMessage(ChatColor.RED + "Oyuncu bulunamadı.");
                        else
                            player.sendMessage(ChatColor.RED + "Player not found.");
                    }
                }
                else
                {
                    if (playerLang.equalsIgnoreCase("TR"))
                        player.sendMessage(ChatColor.RED + "Grup bulunamadı.");
                    else
                        player.sendMessage(ChatColor.RED + "Group not found.");
                }
            }
            else
            {
                if (PermissionPlugin.getPlayerLanguage(player.getName()).equalsIgnoreCase("TR"))
                    player.sendMessage(ChatColor.RED + "Hatalı kullanım. Doğru kullanım: /grup listele, /grup ver <oyuncu> <grup>, /grup al <oyuncu>");
                else
                    player.sendMessage(ChatColor.RED + "Invalid usage. Correct usage: /grup listele, /grup ver <player> <group>, /grup al <player>");
            }
        }
        return true;
    }
}