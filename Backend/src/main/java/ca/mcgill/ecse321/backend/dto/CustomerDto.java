package ca.mcgill.ecse321.backend.dto;

import java.util.List;

public class CustomerDto extends UserDto {

    private List<TransactionDto> transactions;
    private String address;
    private CartDto cart;
    public CustomerDto() {

    }

    public CustomerDto(String name, String username, String password, String address, int id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.ID = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public CartDto getCartDto() {
        return cart;
    }
    
    public void setCartDto(CartDto cart) {
        this.cart = cart;
    }
    
    public List<TransactionDto> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }

}
