
package ca.mcgill.ecse321.backend.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;


@Entity
public class Artist extends User{
   private boolean shipToGallery;
   private static Double defaultCommisionApplied = 0.0;

public void setShipToGallery(boolean value) {
    this.shipToGallery = value;
}
public boolean isShipToGallery() {
    return this.shipToGallery;
}
   @OneToMany(mappedBy="artist" , cascade={CascadeType.ALL}, orphanRemoval = true)
   private List<Artwork> artwork = new ArrayList<Artwork>();
   
   public List<Artwork> getArtwork() {
      return this.artwork;
   }
   
   public void setArtwork(List<Artwork> artworks) {
      this.artwork = artworks;
   }
   
   public void addArtwork(Artwork artwork) {
       artwork.setArtist(this);
       this.artwork.add(artwork);
   }
   
   
   @OneToMany(mappedBy="artist", fetch=FetchType.EAGER)
   private List<Transaction> transaction = new ArrayList<Transaction>();
   
   public List<Transaction> getTransaction() {
      return this.transaction;
   }
   
   public void addTransaction(Transaction transaction) {
       if(transaction!=null) {
           transaction.setArtist(this);
           this.transaction.add(transaction);
       }
   }
   
   public void setTransaction(List<Transaction> transactions) {
      this.transaction = transactions;
   }
   
   private Double specificCommisionApplied;
   
   public void setSpecificCommisionApplied(Double commision) {
       this.specificCommisionApplied = commision;
   }
   
   public Double getSpecificCommisionApplied() {
       return specificCommisionApplied;
   }
   
   public static void setCommisionApplied(Double commision) {
      defaultCommisionApplied = commision;
   }
   
   public static Double getDefaultCommisionApplied() {
       return defaultCommisionApplied;
   }
   
   
   
}
