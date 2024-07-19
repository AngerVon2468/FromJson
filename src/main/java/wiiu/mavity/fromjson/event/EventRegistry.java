package wiiu.mavity.fromjson.event;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import org.jetbrains.annotations.NotNull;

import wiiu.mavity.fromjson.FromJson;
import wiiu.mavity.fromjson.event.useItem.UseItemEvent;
import wiiu.mavity.fromjson.plugin.PluginRegistry;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class EventRegistry {

    public PluginRegistry pluginRegistry;
    public File eventFilePath;
    public File eventFile;

    public EventRegistry(@NotNull PluginRegistry pluginRegistry) {
        this.pluginRegistry = pluginRegistry;
        this.eventFilePath = new File(this.pluginRegistry.pluginFilePath + System.getProperty("file.separator") + "event");
        this.eventFile = new File(this.eventFilePath, this.pluginRegistry.getEventFile() + ".json");

        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.eventFile));
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.error(fileNotFoundException.toString());
        }
        if (this.eventFile.exists()) {
            this.json = this.gson.fromJson(this.bufferedReader, Object.class);
            if (this.json != null) {
                this.jsonTree = JsonParser.parseString(this.json.toString());
            }
        }
        if (this.jsonTree != null) {
            this.jsonObject = this.jsonTree.getAsJsonObject();
        }
        try {
            this.registerEvents();
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.info(fileNotFoundException.toString());
        }
    }

    public BufferedReader bufferedReader;
    public Gson gson = new Gson();
    public Object json;
    public JsonElement jsonTree;
    public JsonObject jsonObject;

    public void registerEvents() throws FileNotFoundException {

        if (this.jsonObject != null) {

            Type listEventTypes = new TypeToken<List<Event>>() {}.getType();
            List<Event> events = this.gson.fromJson(this.jsonObject.getAsJsonArray("events"), listEventTypes);

            for (Event event : events) {

                if (event.name.equals("useItem")) {

                    UseItemEvent useItemEvent = new UseItemEvent(this);

                }

            }

        }

    }
}