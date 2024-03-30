package ca.mcgill.ecse321.backend.dto;

public class UserDto {

    protected String name;
    protected String username;
    protected String password;
    protected String profile;
    protected int ID;
    protected boolean isBanned=false;
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getID() {
        return ID;
    }
    
    public void setIsBanned(boolean isBanned) {
        this.isBanned=isBanned;
    }
    
    public boolean getIsBanned() {
        return isBanned;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    public String getProfile() {
        return profile;
    }
}
