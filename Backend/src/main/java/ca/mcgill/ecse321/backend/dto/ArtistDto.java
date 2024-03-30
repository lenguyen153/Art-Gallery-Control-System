package ca.mcgill.ecse321.backend.dto;

import java.util.List;

public class ArtistDto extends UserDto {

    private List<ArtworkDto> artworks;
    private List<TransactionDto> transactions;
    private Double specificCommisionApplied;
    private static Double defaultCommisionApplied = 0.0;
    private Boolean shipToGallery = false;
    public ArtistDto() {

    }

    public ArtistDto(String name, String username, String password, int id, List<ArtworkDto> artworks) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.ID = id;
        this.artworks = artworks;
    }

    public List<ArtworkDto> getArtworks() {
        return this.artworks;
    }

    public void setArtworks(List<ArtworkDto> artworks) {
        this.artworks = artworks;
    }
    
    public List<TransactionDto> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }
    
    public void setSpecificCommisionApplied(Double commision) {
        this.specificCommisionApplied = commision;
    }
    
    public Double getSpecificCommisionApplied() {
        return specificCommisionApplied;
    }
    
    public void setShipToGallery(Boolean shipToGallery) {
        this.shipToGallery = shipToGallery;
    }
    
    public Boolean getShipToGallery() {
        return shipToGallery;
    }
    
    public static void setCommisionApplied(Double commision) {
        defaultCommisionApplied = commision;
     }
     
    public static Double getDefaultCommisionApplied() {
         return defaultCommisionApplied;
    }

}
