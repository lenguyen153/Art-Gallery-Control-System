package ca.mcgill.ecse321.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.backend.dto.UserDto;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.User;
import ca.mcgill.ecse321.backend.service.ArtistService;
import ca.mcgill.ecse321.backend.service.CustomerService;
import ca.mcgill.ecse321.backend.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private CustomerService customerService;

	/**
	 * Sets the profile of a user
	 * 
	 * @param username   Username of the user which will received the new profile
	 * @param newProfile The new profile
	 * @return The new profile
	 */
	@PostMapping(value = { "/setProfile", "/setProfile/" })
	public String setProfile(@RequestParam String username, @RequestParam String newProfile) {
		newProfile = newProfile.replace('-', ' ');
		return userService.setProfile(username, newProfile);
	}

	/**
	 * Validates a user with its username and password.
	 * 
	 * @param username The username of the user
	 * @param password The password of the user
	 * @return A string if the username and password are valid
	 */
	@GetMapping(value = { "/user/{username}", "/user/{username}/" })
	public String validateUser(@PathVariable("username") String username, @RequestParam String password)
			throws IllegalArgumentException {
		username = username.replace('-', ' ');
		return userService.validateUser(username, password);// return null if username or password invalid
	}

	/**
	 * Updates a user.
	 * 
	 * @param userDto The userDto of the user to be updated (the new name and new
	 *                password will be used to update, while the username will be
	 *                used to find the user)
	 * @return The UserDto of the user that was updated
	 */
	@PutMapping(value = { "/updateUser", "/updateUser/" })
	public UserDto updateUser(@RequestBody UserDto userDto) {
		User user = userService.updateUser(userDto.getName(), userDto.getUsername(), userDto.getPassword());
		return ControllerUtils.converToDto(user);

	}

	/**
	 * Retrieves the profile of a user.
	 * 
	 * @param username the username of the user whose profile should be retrieved
	 * @return The profile of the user
	 */
	@GetMapping(value = { "/getProfile", "/getProfile/" })
	public String getProfile(@RequestParam String username) {
		return userService.getProfile(username);
	}

	/**
	 * Retrieves the boolean isBan of a user.
	 * 
	 * @param username the username of the user whose boolean isBan should be
	 *                 retrieved
	 * @return true if the user is banned, false otherwise
	 */
	@GetMapping(value = { "/getIsBan", "/getIsBan/" })
	public Boolean getIsBan(@RequestParam String username) {
		return userService.getIsBan(username);
	}

	/**
	 * Bans a user
	 * 
	 * @param username Username of the user to be banned
	 * @return The UserDto of the user that was banned (null if user did not exist)
	 */
	@PostMapping(value = { "/banUser/{username}", "/banUser/{username}/" })
	public UserDto banUser(@PathVariable("username") String username) {
		if (artistService.getArtist(username) != null) {
			return ControllerUtils.convertToDto((Artist) userService.banUser(username));
		}
		if (customerService.getCustomer(username) != null) {
			return ControllerUtils.convertToDto((Customer) userService.banUser(username));
		}
		return null;
	}

	/**
	 * Unbans a user.
	 * 
	 * @param username The username of the user to be unbanned
	 * @return The UserDto of the user that was unbanned (null if user did not
	 *         exist)
	 */
	@PostMapping(value = { "/unbanUser/{username}", "/unbanUser/{username}/" })
	public UserDto unbanUser(@PathVariable("username") String username) {
		if (artistService.getArtist(username) != null) {
			return ControllerUtils.convertToDto((Artist) userService.unbanUser(username));
		}
		if (customerService.getCustomer(username) != null) {
			return ControllerUtils.convertToDto((Customer) userService.unbanUser(username));
		}
		return null;
	}

}
