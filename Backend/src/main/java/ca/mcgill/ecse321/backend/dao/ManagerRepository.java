package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.backend.model.Manager;

public interface ManagerRepository extends CrudRepository<Manager, String> {
	
	Manager findManagerByName(String name);
	
	Manager findManagerByUsername(String username);
	
	Manager removeManagerByUsername(String username);
	
	Manager findManagerById(Integer id);
	
	Manager removeManagerById(Integer id);
	
	
	void deleteAll();
}
