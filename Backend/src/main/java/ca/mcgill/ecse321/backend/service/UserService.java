package ca.mcgill.ecse321.backend.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.backend.dao.ArtistRepository;
import ca.mcgill.ecse321.backend.dao.CustomerRepository;
import ca.mcgill.ecse321.backend.dao.ManagerRepository;
import ca.mcgill.ecse321.backend.model.Artist;
import ca.mcgill.ecse321.backend.model.Customer;
import ca.mcgill.ecse321.backend.model.Manager;
import ca.mcgill.ecse321.backend.model.User;

@Service
public class UserService {

    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ManagerRepository managerRepository;

    /**
     * Sets the profile of the user.
     * @param username The username of the user which profile should be changed
     * @param newProfile The new profile of the user
     * @return
     */
    @Transactional
    public String setProfile(String username, String newProfile) {
        User user = null;
        // because java is short circuit, the object user will have the correct value if
        // the user exists
        if (username == null) {
            return "This name is null";
        }
        if (newProfile == null) {
            return "This profile is empty(null)";
        }
        if ((user = artistRepository.findArtistByUsername(username)) != null
                || (user = customerRepository.findCustomerByUsername(username)) != null
                || (user = managerRepository.findManagerByUsername(username)) != null) {
            user.setProfile(newProfile);
            if(user instanceof Artist) {
                artistRepository.save((Artist)user);
            } else if(user instanceof Customer) {
                customerRepository.save((Customer)user);
            } else {
                managerRepository.save((Manager)user);
            }

            return user.getProfile();
        }
        return "This user does not exist";
    }
    
    /**
     * Updates the information of a user.
     * @param name The new name of the user
     * @param username The username of the user which information should be changed
     * @param password The new password of the user
     * @return
     */
    @Transactional
    public User updateUser(String name, String username, String password) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("username cannot be empty!");
        }
        User user = artistRepository.findArtistByUsername(username);
        if(user != null) {
        user.setName(name);
        user.setPassword(password);
        return artistRepository.save((Artist)user);
        }
        user = customerRepository.findCustomerByUsername(username);
        if(user != null) {
        user.setName(name);
        user.setPassword(password);
        return customerRepository.save((Customer)user);
        }
        user = managerRepository.findManagerByUsername(username);
        if(user != null) {
        user.setName(name);
        user.setPassword(password);
        return managerRepository.save((Manager)user);
        }
        return null;
    }

    /**
     * Returns the profile of the user.
     * 
     * @param username The username of the user whose profile should be retrieved
     * @return The profile of the user or the string "This user does not exist" if the user does not exist.
     */
    @Transactional
    public String getProfile(String username) {
        User user = null;
        // because java is short circuit, the object u will have the correct value if
        // the user exists
        if (username == null) {
            return "This name is null";
        }
        if ((user = artistRepository.findArtistByUsername(username)) != null
                || (user = customerRepository.findCustomerByUsername(username)) != null
                || (user = managerRepository.findManagerByUsername(username)) != null) {
            return user.getProfile();
        }
        return "This user does not exist";
    }
    
    /**
     * Returns the username of a user if the parameter password corresponds to the password of the user with the parameter username.
     * Throws an exception if no user with the username exists or if the password is invalid.
     * @param username The username of the user
     * @param password The password of the user
     * @return The username if the password is valid.
     */
    @Transactional
    public String validateUser(String username, String password) {
        User user = null;
        String usern = null;
        // because java is short circuit, the object u will have the correct value if
        // the user exists
        if (username == null) {
            throw new IllegalArgumentException("Username must be specified!");
        }
        if ((user = artistRepository.findArtistByUsername(username)) != null
                || (user = customerRepository.findCustomerByUsername(username)) != null
                || (user = managerRepository.findManagerByUsername(username)) != null) {
            usern = user.getUsername();
        }
        if(usern == null) {
            throw new IllegalArgumentException("No user with username:" + username + " exists!");
        }
        if(!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        return usern;
    }
    
    /**
     * Returns a boolean corresponding to whether the user is banned or not.
     * @param username The username of the user
     * @return true if the user is banned, false otherwise
     */
    @Transactional
    public Boolean getIsBan(String username) {
        User user = null;
        // because java is short circuit, the object u will have the correct value if
        // the user exists
        if (username == null) {
            return null;
        }
        if ((user = artistRepository.findArtistByUsername(username)) != null
                || (user = customerRepository.findCustomerByUsername(username)) != null
                || (user = managerRepository.findManagerByUsername(username)) != null) {
            return user.getIsBanned();
        }
        return null;
    }
    
    /**
     * Bans a user
     * @param username The username of the user to be banned (artist/customer only)
     * @return The user that was banned or null if he does not exist
     */
    @Transactional
    public User banUser(String username) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Artwork name cannot be empty!");
        }
        User toBeBanned = artistRepository.findArtistByUsername(username);
        if(toBeBanned != null) {
        toBeBanned.setIsBanned(true);
        return artistRepository.save((Artist)toBeBanned);
        }
        
        toBeBanned = customerRepository.findCustomerByUsername(username);
        if(toBeBanned != null) {
        toBeBanned.setIsBanned(true);
        return customerRepository.save((Customer)toBeBanned);
        }
        return toBeBanned;
    }
    
    /**
     * Unbans a user
     * @param username The username of the user to be unbanned (artist/customer only)
     * @return The user that was unbanned or null if he does not exist
     */
    @Transactional
    public User unbanUser(String username) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Artwork name cannot be empty!");
        }
        User toBeBanned = artistRepository.findArtistByUsername(username);
        if(toBeBanned != null) {
        toBeBanned.setIsBanned(false);
        return artistRepository.save((Artist)toBeBanned);
        }
        
        toBeBanned = customerRepository.findCustomerByUsername(username);
        if(toBeBanned != null) {
        toBeBanned.setIsBanned(false);
        return customerRepository.save((Customer)toBeBanned);
        }
        return toBeBanned;
    }
    
}
