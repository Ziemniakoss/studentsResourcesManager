package pl.ziemniakoss.studentsresourcesmanager.repositories.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;

import java.util.List;

@Repository
public class ClassesResourcesRepository implements IClassesResourcesRepository {
	private final IResourceRepository resourceRepository;
	private final JdbcTemplate jdbcTemplate;

	public ClassesResourcesRepository(IResourceRepository resourceRepository, JdbcTemplate jdbcTemplate) {
		this.resourceRepository = resourceRepository;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Resource> getAll(Class c) {
		Assert.notNull(c, "Klasa nie może być nullem");
		return getAll(c.getId());
	}

	@Override
	public List<Resource> getAll(int classId) {
		return jdbcTemplate.query("SELECT resource_id as r_id FROM classes_resources WHERE class_id = ?",
				new Object[]{classId}, (rs,rn)->resourceRepository.get(rs.getInt("r_id")));
	}

	@Override
	public void linkToClass(Class c, Resource resource) {//todo
	}

	@Override
	public void linkToClass(int classId, int resourceId) {//todo
	}

	@Override
	public void deleteFromClass(Class c, Resource resource) {//todo
	}

	@Override
	public void deleteFromClass(int classId, int resourceId) {//todo
	}
}
