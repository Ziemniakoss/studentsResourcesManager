package pl.ziemniakoss.studentsresourcesmanager.services.classes;

import pl.ziemniakoss.studentsresourcesmanager.models.Class;

import java.util.List;

public interface IClassesManagementService {
	List<Class> geAllCoordinatedByCurrentUser();

	void delete(Class c);

	void update(Class c);
}
