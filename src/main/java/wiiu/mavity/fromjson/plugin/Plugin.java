package wiiu.mavity.fromjson.plugin;

public class Plugin {

    private String id;

    public Plugin(String id) {
        this.setId(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}