package wiiu.mavity.fromjson.block;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import org.jetbrains.annotations.NotNull;

import wiiu.mavity.fromjson.FromJson;
import wiiu.mavity.fromjson.plugin.PluginRegistry;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class BlockRegistry {

    public PluginRegistry pluginRegistry;
    public File itemFilePath;
    public File itemFile;

    public BlockRegistry(@NotNull PluginRegistry pluginRegistry) {
        this.pluginRegistry = pluginRegistry;
        this.itemFilePath = new File(this.pluginRegistry.pluginFilePath + System.getProperty("file.separator") + "block");
        this.itemFile = new File(this.itemFilePath, this.pluginRegistry.getBlockFile() + ".json");

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

    public Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(this.getModId(), name), block);
    }

    public net.minecraft.item.Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(this.getModId(), name),
                new BlockItem(block, new FabricItemSettings().maxCount(64)));
    }

    public void registerItems() throws FileNotFoundException {

        if (this.jsonObject != null) {

            Type listBlockTypes = new TypeToken<List<wiiu.mavity.fromjson.block.Block>>() {}.getType();
            List<wiiu.mavity.fromjson.block.Block> blocks = this.gson.fromJson(this.jsonObject.getAsJsonArray("blocks"), listBlockTypes);

            Scanner myReader = new Scanner(this.itemFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                FromJson.LOGGER.info(data);
            }
            myReader.close();

            FromJson.LOGGER.info("Blocks:");
            for (wiiu.mavity.fromjson.block.Block block : blocks) {

                FromJson.LOGGER.info(block.id);
                Block pluginBlock = registerBlock(block.id,
                        new PluginBlock(FabricBlockSettings.create()) {
                        }
                );

            }

        }

    }
}