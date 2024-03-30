package ca.mcgill.ecse321.backend.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User{
    protected static Integer lastId = 1;
	protected String name;

	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	@Id
	protected String username;

	public void setUsername(String value) {
		this.username = value;
	}
	public String getUsername() {
		return this.username;
	}
	
	protected String password;

	public void setPassword(String value) {
		this.password = value;
	}
	public String getPassword() {
		return this.password;
	}
	
    //auto generation of id
	protected Integer id = lastId++;

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
        return this.id;
    }
	
	protected String profile;
	
	public void setProfile(String profile){
	    this.profile = profile;
	}
	
	public String getProfile() {
	    return profile;
	}

    protected boolean isBanned=false;
	
	public boolean getIsBanned() {
	    return isBanned;
	}
	
	public void setIsBanned(boolean isBanned) {
	    this.isBanned = isBanned;
	}


}