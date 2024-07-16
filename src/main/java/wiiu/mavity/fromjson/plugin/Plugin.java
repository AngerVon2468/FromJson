package wiiu.mavity.fromjson.plugin;

import org.jetbrains.annotations.Nullable;

public class Plugin {

    private String id;

    @Nullable
    private Boolean isStable;

    public Plugin(String id) {
        this.setId(id);
    }

    public Plugin(String id, boolean isStable) {
        this.setId(id);
        this.setIsStable(isStable);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setIsStable(boolean isStable) {
        this.isStable = isStable;
    }

    public @Nullable Boolean getIsStable() {
        return this.isStable;
    }
}