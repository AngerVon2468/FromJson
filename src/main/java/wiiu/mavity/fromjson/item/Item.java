package wiiu.mavity.fromjson.item;

public class Item {

    public String id;

    public Integer maxCount;

    public Integer maxDamage;

    public Boolean fireProof;

    public Item(String id) {
        this.id = id;
    }

    public Item(String id, Integer maxCount, Integer maxDamage) {
        this.id = id;
        this.maxCount = maxCount;
        this.maxDamage = maxDamage;
    }

    public Item(String id, Integer maxCount, Integer maxDamage, Boolean fireProof) {
        this.id = id;
        this.maxCount = maxCount;
        this.maxDamage = maxDamage;
        this.fireProof = fireProof;
    }
}