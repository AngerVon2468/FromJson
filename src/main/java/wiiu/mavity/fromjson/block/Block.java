package wiiu.mavity.fromjson.block;

public class Block {

    public String id;

    public Boolean nonOpaque;

    public Block(String id) {
        this.id = id;
    }

    public Block(String id, Boolean nonOpaque) {
        this.id = id;
        this.nonOpaque = nonOpaque;
    }
}