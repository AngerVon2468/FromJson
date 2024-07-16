package wiiu.mavity.fromjson.plugin;

import org.jetbrains.annotations.NotNull;

import wiiu.mavity.fromjson.reader.FromJsonReader;

import java.io.File;

public class PluginRegistry {

    public File pluginFile;

    public File pluginFilePath;

    public PluginRegistry(@NotNull Plugin plugin, @NotNull FromJsonReader fromJsonReader) {
        String pluginFileName = plugin.getId() + ".json";
        String newPluginPath = fromJsonReader.getPluginPath() + System.getProperty("file.separator") + plugin.getId();
        this.pluginFilePath = new File(newPluginPath);
        if (!this.pluginFilePath.exists()) {

            this.pluginFilePath.mkdirs();

        }
        this.pluginFile = new File(newPluginPath, pluginFileName);
    }
}