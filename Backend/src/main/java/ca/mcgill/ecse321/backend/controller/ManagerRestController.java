package ca.mcgill.ecse321.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.backend.dto.ArtistDto;
import ca.mcgill.ecse321.backend.dto.CustomerDto;
import ca.mcgill.ecse321.backend.dto.ManagerDto;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Manager;
import ca.mcgill.ecse321.backend.service.ManagerService;


@CrossOrigin(origins = "*")
@RestController
public class ManagerRestController {
	@Autowired
	private ManagerService managerService;
	

	/**
     * Creates a manager with the given managerDto.
     * @param managerDto The managerDto to be used to create a manager
     * @return The managerDto of the created manager
     */
	@PostMapping(value = { "/createManager", "/createManager/" })
	public ManagerDto createManager(@RequestBody ManagerDto managerDto) {
		Manager manager = managerService.createManager(
				managerDto.getName(),
				managerDto.getUsername(),
				managerDto.getPassword());
		return ControllerUtils.convertToDto(manager);
	} 	

	/**
	 * Retrieves the manager with the given username.
	 * @param name The username of the manager
	 * @return The managerDto of the manager with the username given or null if it does not exist
	 */
	@GetMapping(value = {"/getManager/{name}", "/getManager/{name}/"})
	public ManagerDto getManager(@PathVariable String name) {
		return ControllerUtils.convertToDto(managerService.getManager(name));
	}

	/**
	 * Updates the name and password fields of a manager.
	 * 
	 * @param username The username of the manager which should receive the new fields values
	 * @param name The new name
	 * @param password The new password
	 * @return The managerDto of the updated manager
	 * @throws IllegalArgumentException If no manager with such username exists
	 */
	@PostMapping(value = {"/updateManager/{username}","/updateManager/{username}/"})
	public ManagerDto updateManager(@PathVariable String username, 
			@RequestParam (name = "name") String name, 
			@RequestParam (name = "password") String password) 
					throws IllegalArgumentException {
		Manager manager = managerService.updateManager(
				name,
				username,
				password);
		return ControllerUtils.convertToDto(manager);
	}
	
	/**
	 * Updates the commission applied on an artist.
	 * @param username The username of the artist
	 * @param commission The new commission to be applied
	 * @return The artistDto of the artist which received the new commission
	 * @throws IllegalArgumentException If no artist with such username exists
	 */
	@PostMapping(value = {"/updateCommissionByManager","/updateCommissionByManager/"})
	public ArtistDto updateCommission( 
			@RequestParam (name = "username") String username, 
			@RequestParam (name = "specificCommissionApplied") double commission) 
					throws IllegalArgumentException {
		Artist artist = managerService.updateCommission(
				username,
				commission);
		return ControllerUtils.convertToDto(artist);
	}

	/**
	 * Deletes a manager.
	 * @param username The username of the manager
	 * @return The managerDto corresponding to the deleted manager (null if the manager did not exist)
	 */
	@PostMapping(value = {"/deleteManager/{username}","/deleteManager/{username}/"})
	public ManagerDto deleteManager(@PathVariable String username) {
		Manager manager =managerService.deleteManager(username);
		if(manager != null) {
			return ControllerUtils.convertToDto(manager);
		}
		return null;
	}
	

	/**
	 * Bans a manager.
	 * @param name Username of the manager to be banned
	 */
	@PostMapping(value = {"/banCustomer/{name}", "/banCustomer/{name}/"})
	public void banCustomer(@PathVariable String name) {
		managerService.banManager(name);		
	}

	/**
	 * Unbans a manager.
	 * @param name Username of the manager to be unban
	 */
	@PostMapping(value = {"/unbanCustomer/{name}", "/unbanCustomer/{name}/"})
	public void unbanCustomer(@PathVariable String name) {
		managerService.unbanManager(name);
	}

	/**
	 * 
	 * @return A list containing all customers
	 */
	@GetMapping(value = {"/AllCustomers", "/AllCustomers/"})
	public List<CustomerDto> getAllCustomers() {
		List<CustomerDto> customerDtos = new ArrayList<>();
		for(Customer customer : managerService.getAllCustomers()) {
			customerDtos.add(ControllerUtils.convertToDto(customer));
		}

		return customerDtos;
	}


	/**
	 * 
	 * @return A list containing all managers
	 */
	@GetMapping(value = { "/AllManagers", "/AllManagers/" })
	public List<ManagerDto> getAllManagers() {
		List<ManagerDto> managerDtos = new ArrayList<>();
		for(Manager manager : managerService.getAllManagers()) {
			managerDtos.add(ControllerUtils.convertToDto(manager));
		}
		return managerDtos;
	}
	

}
