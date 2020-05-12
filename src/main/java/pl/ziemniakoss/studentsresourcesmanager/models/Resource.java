package pl.ziemniakoss.studentsresourcesmanager.models;

public class Resource {
	private int id;
	private String name;
	private User owner;
	private ResourceType type;
	private boolean availableToAll;

	public boolean isAvailableToAll() {
		return availableToAll;
	}

	public void setAvailableToAll(boolean availableToAll) {
		this.availableToAll = availableToAll;
	}

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}
}
