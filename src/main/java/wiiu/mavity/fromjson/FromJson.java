package wiiu.mavity.fromjson;

import net.fabricmc.api.ModInitializer;

import org.slf4j.*;

public class FromJson implements ModInitializer {

	public static final String NAME = "FromJson";

	public static final String MOD_ID = "fromjson";

    public static final Logger LOGGER = LoggerFactory.getLogger(FromJson.NAME);

	@Override
	public void onInitialize() {

		FromJson.LOGGER.info(FromJson.NAME + " has started up!");
	}
}