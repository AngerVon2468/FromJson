package wiiu.mavity.fromjson.reader;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import org.jetbrains.annotations.NotNull;

import wiiu.mavity.fromjson.FromJson;
import wiiu.mavity.fromjson.plugin.Plugin;
import wiiu.mavity.fromjson.plugin.PluginRegistry;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class FromJsonReader {

    public String pluginPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "fromjson" + System.getProperty("file.separator") + "plugin";

    public String getPluginPath() {
        return this.pluginPath;
    }

    public File pluginRegistry = new File(this.getPluginPath(), "plugins.json");

    public File getPluginRegistry() {
        return this.pluginRegistry;
    }

    public File pluginFolder = new File(this.getPluginPath());

    public File getPluginFolder() {
        return this.pluginFolder;
    }

    public void genPluginFolderAndFile() {

        if (!pluginFolder.exists()){
            pluginFolder.mkdirs();
        }
        try {
            if (this.getPluginRegistry().createNewFile()) {
                FromJson.LOGGER.info("File created: " + pluginRegistry.getName());
            } else {
                FromJson.LOGGER.info("File already exists.");
            }
        } catch (IOException ioException) {
            FromJson.LOGGER.info(ioException.toString());
        }
        FromJson.LOGGER.info("experimental_features: " + this.getExperimentalFeatures());

    }

    public BufferedReader bufferedReader;

    public Gson gson = new Gson();

    public FromJsonReader() {
        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.getPluginRegistry()));
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.error(fileNotFoundException.toString());
        }
        if (this.pluginRegistry.exists()) {

            this.json = this.gson.fromJson(bufferedReader, Object.class);
            if (this.json != null) {

                this.jsonTree = JsonParser.parseString(this.json.toString());

            }

        }
        if (this.jsonTree != null) {
            this.jsonObject = this.jsonTree.getAsJsonObject();
        }
    }

    public Object json;
    public JsonElement jsonTree;
    public JsonObject jsonObject;

    public void registerPlugins() throws FileNotFoundException {

        if (this.jsonObject != null) {

            Type listPluginTypes = new TypeToken<List<Plugin>>() {}.getType();
            List<Plugin> plugins = this.gson.fromJson(this.jsonObject.getAsJsonArray("plugins"), listPluginTypes);

            Scanner myReader = new Scanner(this.pluginRegistry);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                FromJson.LOGGER.info(data);
            }
            myReader.close();

            FromJson.LOGGER.info("Plugins:");
            for (Plugin plugin : plugins) {

                FromJson.LOGGER.info(plugin.getId() + stability(plugin));
                PluginRegistry pluginRegistry = new PluginRegistry(plugin, this);

            }

        }

    }

    public static @NotNull String stability(@NotNull Plugin plugin) {
        return plugin.getIsStable() != null && plugin.getIsStable() ? ", Stable: " + plugin.getIsStable() : "";
    }

    public boolean getExperimentalFeatures() {

        return this.jsonTree == null || this.jsonObject.get("experimental_features") == null ? false : this.jsonObject.get("experimental_features").getAsBoolean();
    }
}