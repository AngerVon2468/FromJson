package wiiu.mavity.fromjson.event.useItem;

public class DropItem {

    public String id;

    public String mod_id;

    public DropItem(String id) {
        this.id = id;
    }

    public DropItem(String mod_id, String id) {
        this.mod_id = mod_id;
        this.id = id;
    }
}