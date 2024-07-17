package wiiu.mavity.fromjson.item;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import wiiu.mavity.fromjson.FromJson;
import wiiu.mavity.fromjson.plugin.PluginRegistry;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ItemRegistry {

    public PluginRegistry pluginRegistry;
    public File itemFilePath;
    public File itemFile;

    public ItemRegistry(@NotNull PluginRegistry pluginRegistry) {
        this.pluginRegistry = pluginRegistry;
        this.itemFilePath = new File(this.pluginRegistry.pluginFilePath + System.getProperty("file.separator") + "item");
        this.itemFile = new File(this.itemFilePath, this.pluginRegistry.getItemFile() + ".json");

        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.itemFile));
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.error(fileNotFoundException.toString());
        }
        if (this.itemFile.exists()) {
            this.json = this.gson.fromJson(this.bufferedReader, Object.class);
            if (this.json != null) {
                this.jsonTree = JsonParser.parseString(this.json.toString());
            }
        }
        if (this.jsonTree != null) {
            this.jsonObject = this.jsonTree.getAsJsonObject();
        }
        try {
            this.registerItems();
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.info(fileNotFoundException.toString());
        }
    }

    public BufferedReader bufferedReader;
    public Gson gson = new Gson();
    public Object json;
    public JsonElement jsonTree;
    public JsonObject jsonObject;

    public String getModId() {
        return this.jsonTree == null || this.jsonObject.get("mod_id") == null ? "minecraft" : this.jsonObject.get("mod_id").getAsString();
    }

    private PluginItem registerItem(String name, PluginItem item) {
        return Registry.register(Registries.ITEM, new Identifier(this.getModId(), name), item);
    }

    public void registerItems() throws FileNotFoundException {

        if (this.jsonObject != null) {

            Type listItemTypes = new TypeToken<List<Item>>() {}.getType();
            List<Item> items = this.gson.fromJson(this.jsonObject.getAsJsonArray("items"), listItemTypes);

            for (Item item : items) {

                PluginItem pluginItem = this.registerItem(item.id, new PluginItem(new FabricItemSettings()) {

                    @Override
                    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
                        if (!world.isClient()) {
                            user.sendMessage(Text.literal("Test"));
                        }
                        return super.use(world, user, hand);
                    }
                });

            }

        }

    }
}