package ca.mcgill.ecse321.backend.dto;

public class ManagerDto extends UserDto {

    public ManagerDto() {

    }

    public ManagerDto(String name, String username, String password, int id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.ID = id;
    }

}
