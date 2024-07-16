package wiiu.mavity.fromjson.plugin;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class PluginRegistry {

    public static void registerPluginFile(@NotNull Plugin plugin, String pluginPath) {

        String pluginFileName = plugin.getId() + ".json";
        String newPluginPath = pluginPath + System.getProperty("file.separator") + plugin.getId();
        File pluginFile = new File(newPluginPath, pluginFileName);

    }
}