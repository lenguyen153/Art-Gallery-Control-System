package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.backend.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	
	Transaction findTransactionById(Integer id);
	
	Transaction removeTransactionById(Integer id);
	
	void deleteAll();
}
