package ca.mcgill.ecse321.backend.service;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.backend.model.ArtStyle;

public class ServiceUtils {
    
    /**
     * Returns a list from the given parameter
     * @param <T> The type of the objects inside the iterable
     * @param iterable An iterable object
     * @return A list which contains all the object that were in the iterable
     */
	public static <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T varT : iterable) {
			resultList.add(varT);
		}
		return resultList;
	}
	
	/**
	 * Converts a string to an artstyle.
	 * @param artstyle The string to be converted to an artstyle
	 * @return The artstyle that corresponds to the string (default value is abstract)
	 */
	public static ArtStyle toArtStyle(String artstyle) {
	    artstyle = artstyle.toUpperCase();
	    switch(artstyle) {
	    case "ABSTRACT":  return ArtStyle.ABSTRACT;
	    case "REALISM":return ArtStyle.REALISM;
	    case "PHOTOREALISM":return ArtStyle.PHOTOREALISM;
        case "IMPRESSIONISM":return ArtStyle.IMPRESSIONISM;
        case "SURREALISM":return ArtStyle.SURREALISM;
        case "POP":return ArtStyle.POP;
        case "ART_NOUVEAU":return ArtStyle.ART_NOUVEAU;
        case "MODERNISM":return ArtStyle.MODERNISM;
        case "SCULPTURE":return ArtStyle.SCULPTURE;
        case "MODELING":return ArtStyle.MODELING;        
	    default: return ArtStyle.ABSTRACT;   
	    }
	}

}
