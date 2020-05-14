package pl.ziemniakoss.studentsresourcesmanager.services.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.ziemniakoss.studentsresourcesmanager.models.Class;
import pl.ziemniakoss.studentsresourcesmanager.repositories.classes.IClassesRepository;

import java.util.List;

@Service
public class ClassesManagementService implements IClassesManagementService {
	private final IClassesRepository classesRepository;

	public ClassesManagementService(IClassesRepository classesRepository) {
		this.classesRepository = classesRepository;
	}

	@Override
	public List<Class> geAllCoordinatedByCurrentUser() {
		return null;
	}

	@Override
	public void delete(Class c) {
		Assert.notNull(c, "Klasa nie może być nullem");
		Assert.notNull(classesRepository.get(c.getId()), "Klasa nie istnieje");
//todo
	}

	@Override
	public void update(Class c) {
//todo
	}
}
