package divingEquip.model;

public class Equipment {
    private int id;           // Unique identifier for the equipment
    private String name;      // Name of the equipment
    private int quantity;     // Quantity of the equipment

    // Constructor
    public Equipment(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
    
    public Equipment() {
		super();
	}

	// Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
