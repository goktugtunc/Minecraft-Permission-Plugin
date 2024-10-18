package com.gotunc.permissionPlugin;

import com.gotunc.permissionPlugin.Classes.Groups;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class fileProcesses {

    private static final String CONFIG_FILE = "config.json";
    private static final String PLUGIN_FOLDER = "plugins/PermissionPlugin/";

    public static void copyDefaultConfigIfNotExists() {
        try {
            // Plugin klasörünü kontrol et ve oluştur
            File pluginDir = new File(PLUGIN_FOLDER);
            if (!pluginDir.exists()) {
                pluginDir.mkdirs();
            }

            // Config dosyasının varlığını kontrol et
            File configFile = new File(PLUGIN_FOLDER + CONFIG_FILE);
            if (!configFile.exists()) {
                // resources/config.json içeriğini kopyala
                try (InputStream in = PermissionPlugin.getInstance().getResource(CONFIG_FILE)) {
                    if (in == null) {
                        Bukkit.getLogger().warning(CONFIG_FILE + " resources klasöründe bulunamadı.");
                        return;
                    }
                    Files.copy(in, Paths.get(configFile.getPath()));
                    Bukkit.getLogger().info(CONFIG_FILE + " dosyası kopyalandı.");
                }
            } else {
                Bukkit.getLogger().info(CONFIG_FILE + " zaten mevcut.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fillGroups() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PLUGIN_FOLDER + CONFIG_FILE))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            for (String group : jsonObject.keySet()) {
                JSONObject groupPermissions = jsonObject.getJSONObject(group);
                List<Groups> permissions = new ArrayList<>();
                for (String permissionString : groupPermissions.keySet()) {
                    Groups permission = new Groups(permissionString, groupPermissions.getJSONObject(permissionString).getString("command"));
                    permissions.add(permission);
                }
                PermissionPlugin.groups.put(group, permissions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}