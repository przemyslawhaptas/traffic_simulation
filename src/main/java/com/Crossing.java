package com;

import java.io.Serializable;

/**
 *
 * @author Albert Podraza
 */
        /** 
         * Crossing processed nodes from XML to potential verticles of graph.
	 */
public class Crossing implements Serializable{
	private final double lat;
	private final double lon;
        
        /** 
         * Default vertex constructor with latitude and longitude 
         * @param _lat latitude
         * @param _lon longitude
	 */  	
	public Crossing(String _lat, String _lon){
		lat=Double.parseDouble(_lat);
		lon=Double.parseDouble(_lon);
	}
        
        /** 
         * Returning private data with node's latitude
         * @return geographical latitude        
	 */  
        public double getLat(){
            return lat;
	}
        
        /** 
         * Returning private data with node's longitude
         * @return geographical lontitude         
	 */  
        public double getLon(){
            return lon;
        }
}

