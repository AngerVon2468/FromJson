package wiiu.mavity.fromjson.plugin;

import com.google.gson.*;

import org.jetbrains.annotations.NotNull;

import wiiu.mavity.fromjson.FromJson;
import wiiu.mavity.fromjson.reader.FromJsonReader;

import java.io.*;

public class PluginRegistry {

    public File pluginFile;

    public File pluginFilePath;

    public PluginRegistry(@NotNull Plugin plugin, @NotNull FromJsonReader fromJsonReader) {
        String pluginFileName = plugin.getId() + ".json";
        String newPluginPath = fromJsonReader.getPluginPath() + System.getProperty("file.separator") + plugin.getId();
        this.pluginFilePath = new File(newPluginPath);
        this.pluginFile = new File(newPluginPath, pluginFileName);

        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.pluginFile));
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.error(fileNotFoundException.toString());
        }
        if (this.pluginFile.exists()) {

            this.json = this.gson.fromJson(bufferedReader, Object.class);
            if (this.json != null) {

                this.jsonTree = JsonParser.parseString(this.json.toString());

            }

        }
        if (this.jsonTree != null) {
            this.jsonObject = this.jsonTree.getAsJsonObject();
        }
    }

    public BufferedReader bufferedReader;

    public Gson gson = new Gson();

    public Object json;
    public JsonElement jsonTree;
    public JsonObject jsonObject;
}