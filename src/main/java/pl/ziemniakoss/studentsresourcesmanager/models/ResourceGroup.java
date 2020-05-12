package pl.ziemniakoss.studentsresourcesmanager.models;

import java.nio.file.AccessDeniedException;
import java.util.List;

public class ResourceGroup {
	private int id;
	private String name;
	private String description;
	private List<Resource> resources;
	/**
	 * Czy grupa jest przypisana do klasy czy do kursu
	 */
	private ResourceGroupType type;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public ResourceGroupType getType() {
		return type;
	}

	public void setType(ResourceGroupType type) {
		this.type = type;
	}
}
