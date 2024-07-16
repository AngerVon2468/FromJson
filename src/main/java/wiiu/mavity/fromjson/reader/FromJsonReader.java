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

    public static String pluginPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "fromjson" + System.getProperty("file.separator") + "plugin";

    public static File pluginRegistry = new File(pluginPath, "plugins.json");

    public static File pluginFolder = new File(pluginPath);

    public static void genPluginFolderAndFile() {

        if (!pluginFolder.exists()){
            pluginFolder.mkdirs();
        }
        try {
            if (pluginRegistry.createNewFile()) {
                FromJson.LOGGER.info("File created: " + pluginRegistry.getName());
            } else {
                FromJson.LOGGER.info("File already exists.");
            }
        } catch (IOException ioException) {
            FromJson.LOGGER.info(ioException.toString());
        }
        FromJson.LOGGER.info("experimental_features: " + getExperimentalFeatures());

    }

    public static BufferedReader bufferedReader;

    public static Gson gson = new Gson();

    static {
        try {
            bufferedReader = new BufferedReader(new FileReader(pluginRegistry));
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.error(fileNotFoundException.toString());
        }

    }

    public static Object json;
    public static JsonElement jsonTree;

    static {

        if (pluginRegistry.exists()) {

            json = gson.fromJson(bufferedReader, Object.class);
            if (json != null) {

                jsonTree = JsonParser.parseString(json.toString());

            }

        }

    }

    public static JsonObject jsonObject;

    static {

        if (jsonTree != null) {
            jsonObject = jsonTree.getAsJsonObject();
        }

    }

    public static void registerPlugins() throws FileNotFoundException {

        if (jsonObject != null) {

            Type listPluginTypes = new TypeToken<List<Plugin>>() {}.getType();
            List<Plugin> plugins = gson.fromJson(jsonObject.getAsJsonArray("plugins"), listPluginTypes);

            Scanner myReader = new Scanner(pluginRegistry);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                FromJson.LOGGER.info(data);
            }
            myReader.close();

            FromJson.LOGGER.info("Plugins:");
            for (Plugin plugin : plugins) {

                FromJson.LOGGER.info(plugin.getId() + stability(plugin));
                PluginRegistry.registerPluginFile(plugin, pluginPath);

            }

        }

    }

    public static @NotNull String stability(@NotNull Plugin plugin) {
        return plugin.getIsStable() != null && plugin.getIsStable() ? ", Stable: " + plugin.getIsStable() : "";
    }

    public static boolean getExperimentalFeatures() {

        return jsonTree == null || jsonObject.get("experimental_features") == null ? false : jsonObject.get("experimental_features").getAsBoolean();
    }
}