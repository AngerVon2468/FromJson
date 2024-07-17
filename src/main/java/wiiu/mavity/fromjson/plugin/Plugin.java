package wiiu.mavity.fromjson.plugin;

import org.jetbrains.annotations.Nullable;

public class Plugin {

    @Nullable
    private String name;

    private String id;

    @Nullable
    private Boolean isStable;

    public Plugin(String id) {
        this.setId(id);
    }

    public Plugin(String name, String id) {
        this.setName(name);
        this.setId(id);
    }

    public Plugin(String id, Boolean isStable) {
        this.setId(id);
        this.setIsStable(isStable);
    }

    public Plugin(String name, String id, Boolean isStable) {
        this.setName(name);
        this.setId(id);
        this.setIsStable(isStable);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String getName() {
        return this.name;
    }

    public void setIsStable(boolean isStable) {
        this.isStable = isStable;
    }

    public @Nullable Boolean getIsStable() {
        return this.isStable;
    }
}