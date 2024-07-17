package wiiu.mavity.fromjson;

import net.fabricmc.api.ModInitializer;

import org.slf4j.*;

import wiiu.mavity.fromjson.reader.FromJsonReader;

import java.io.FileNotFoundException;

public class FromJson implements ModInitializer {

	public static final String NAME = "FromJson";

    public static final Logger LOGGER = LoggerFactory.getLogger(FromJson.NAME);

	@Override
	public void onInitialize() {

		FromJson.LOGGER.info(FromJson.NAME + " has started up!");

		FromJsonReader fromJsonReader = new FromJsonReader();
		fromJsonReader.genPluginFolderAndFile();
		try {
			fromJsonReader.registerPlugins();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}