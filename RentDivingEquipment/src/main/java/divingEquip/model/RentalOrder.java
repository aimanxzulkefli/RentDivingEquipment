package divingEquip.model;

import java.sql.Date;

public class RentalOrder {
    private int equipmentId;
    private String equipmentName;
    private int quantity;
    private String startDate;
    private String endDate;
    private String fullName;       // New field
    private String icNumber;       // New field
    private String phoneNumber;    // New field

    
    public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getIcNumber() {
		return icNumber;
	}


	public void setIcNumber(String icNumber) {
		this.icNumber = icNumber;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public RentalOrder(int equipmentId, String equipmentName, int quantity, String startDate, String endDate, String fullName, String icNumber, String phoneNumber) {
		super();
		this.equipmentId = equipmentId;
		this.equipmentName = equipmentName;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.fullName =fullName;
		this.icNumber =icNumber;
		this.phoneNumber = phoneNumber;
	}

    
	public RentalOrder() {
		super();
	}


	// Getters and Setters
    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    
}
