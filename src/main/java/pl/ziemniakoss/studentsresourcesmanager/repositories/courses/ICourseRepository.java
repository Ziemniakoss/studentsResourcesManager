package pl.ziemniakoss.studentsresourcesmanager.repositories.courses;

import pl.ziemniakoss.studentsresourcesmanager.models.Course;

import java.util.List;

/**
 * Pozwala na tworzenie, usuwanie i modyfikację kursów
 */
public interface ICourseRepository {
	/**
	 * Tworzy kurs w bazie danych.
	 * @param course kurs do utworzenia
	 */
	void create(Course course);

	void delete(Course course);

	void update(Course course);

	Course get(int id);

	boolean exists(int id);

	List<Course> getAll();

	List<Course> getAllCoordinatedBy(String email);

	List<Course> getAllWithNameLike(String like);
}
