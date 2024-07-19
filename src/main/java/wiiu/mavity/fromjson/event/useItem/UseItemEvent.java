package wiiu.mavity.fromjson.event.useItem;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.*;

import org.jetbrains.annotations.NotNull;

import wiiu.mavity.fromjson.FromJson;
import wiiu.mavity.fromjson.event.EventRegistry;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class UseItemEvent {

    public EventRegistry eventRegistry;
    public File useItemFilePath;
    public File useItemFile;

    public UseItemEvent(@NotNull EventRegistry eventRegistry) {
        this.eventRegistry = eventRegistry;
        this.useItemFilePath = new File(this.eventRegistry.eventFilePath + System.getProperty("file.separator") + "use_item");
        this.useItemFile = new File(this.useItemFilePath, "use_item.json");

        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.useItemFile));
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.error(fileNotFoundException.toString());
        }
        if (this.useItemFile.exists()) {
            this.json = this.gson.fromJson(this.bufferedReader, Object.class);
            if (this.json != null) {
                this.jsonTree = JsonParser.parseString(this.json.toString());
            }
        }
        if (this.jsonTree != null) {
            this.jsonObject = this.jsonTree.getAsJsonObject();
        }
        try {
            this.registerUseItemEvent();
        } catch (FileNotFoundException fileNotFoundException) {
            FromJson.LOGGER.info(fileNotFoundException.toString());
        }
    }

    public BufferedReader bufferedReader;
    public Gson gson = new Gson();
    public Object json;
    public JsonElement jsonTree;
    public JsonObject jsonObject;

    public void registerUseItemEvent() throws FileNotFoundException {

        if (this.jsonObject != null) {

            UseItemCallback.EVENT.register((player, world, hand) -> {
                ItemStack stack = player.getStackInHand(hand);

                Type listDropItems = new TypeToken<List<DropItem>>() {}.getType();
                List<DropItem> dropItems = this.gson.fromJson(this.jsonObject.getAsJsonArray("drop_items"), listDropItems);
                for (DropItem dropItem : dropItems) {

                    if (dropItem != null && dropItem.id != null) {

                        player.dropItem(new ItemStack(Registries.ITEM.get(new Identifier(dropItem.id))).getItem());

                    } else if (dropItem != null && dropItem.id != null && dropItem.mod_id != null) {

                        player.dropItem(new ItemStack(Registries.ITEM.get(new Identifier(dropItem.mod_id, dropItem.id))).getItem());

                    }

                }

                return TypedActionResult.success(stack);
            });

        }

    }
}