package wiiu.mavity.fromjson.reader;

import com.google.gson.*;

import wiiu.mavity.fromjson.FromJson;

import java.io.*;

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
        FromJson.LOGGER.info(getWiiUIsBased() + "");

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

        if (jsonTree != null && jsonTree.isJsonObject()) {
            jsonObject = jsonTree.getAsJsonObject();
        }

    }

    public static boolean getWiiUIsBased() {

        return jsonTree == null ? true : jsonObject.get("isWiiUBased").getAsBoolean();
    }
}