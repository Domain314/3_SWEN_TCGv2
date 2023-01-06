package at.domain314.models.cards;

public class Card {
    String id;
    String name;
    String description;
    int damage;
    Type type;
    Element element;

    public String getID() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getDamage() { return damage; }
    public Type getType() { return type; }
    public Element getElement() { return element; }

    public void setID(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setType(Type type) { this.type = type; }
    public void setElement(Element element) { this.element = element; }


    public Card() {}

    public Card(String id, String name, String description, int damage, Type type, Element element) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.type = type;
        this.element = element;
    }

    public Card(String id, String name, String description, int damage, String type, String element) {
        try {
            this.id = id;
            this.name = name;
            this.description = description;
            this.damage = damage;
            this.type = convertStringToType(type);
            this.element = convertStringToElement(element);
        } catch (Exception e) { System.out.println(e.getMessage()); }

    }

    private Type convertStringToType(String type) {
        switch (type) {
            case "GOBLIN": return Type.GOBLIN;
            case "WIZARD": return Type.WIZARD;
            case "DRAGON": return Type.DRAGON;
            case "KNIGHT": return Type.KNIGHT;
            case "KRAKEN": return Type.KRAKEN;
            case "ELV": return Type.ELV;
            case "ORK": return Type.ORK;
            case "SPELL": return Type.SPELL;
            default: return Type.SPELL;
        }
    }

    private Element convertStringToElement(String element) {
        switch (element) {
            case "WATER": return Element.WATER;
            case "FIRE": return Element.FIRE;
            case "ICE": return Element.ICE;
            case "WIND": return Element.WIND;
            default: return Element.NORMAL;
        }
    }
}

