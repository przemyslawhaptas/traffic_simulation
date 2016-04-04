package com;

import java.io.Serializable;
import java.util.LinkedList;
        /** 
         * Road processed ways from XML to potential edges of graph.
	 */
public class Road implements Serializable {

	private final String streetName;
	private double weight;
	private final Crossing firstCrossing, secondCrossing;

        /** 
         * Default edge constructor 
         * @param streetName street name
         * @param ref list of way's verticles from XML file
         * @param refHelper variable, which helps with determing verticles of edge
         * @param map reference to main class with verticles and other data
	 */ 
        public Road(String streetName, LinkedList<String> ref, int refHelper, Map map){
            this.firstCrossing =map.getVerticles().get( ref.get(refHelper-1));
            this.secondCrossing =map.getVerticles().get(ref.get(refHelper));
            this.streetName=streetName;
            if(secondCrossing !=null && firstCrossing !=null){
		weight=Math.sqrt(Math.pow(secondCrossing.getLon()- firstCrossing.getLon(),2)+Math.pow(secondCrossing.getLat()- firstCrossing.getLat(),2));
            }
        }
                
        /** 
         * Returning private data with edge's first verticle
         * @return first vertex      
	 */  
	public Crossing getStartVertex(){
            return firstCrossing;
        }
                
        /** 
         * Returning private data with edge's second verticle
         * @return end vertex       
	 */  
        public Crossing getStopVertex(){
            return secondCrossing;
        }
                
        /** 
         * Returning private data with distance between 2 
         * verticles in edge
         * @return distance       
	 */  
        public double getWeight(){
            return weight; 
        }
                
        /** 
         * Returning private data with street name.
         * @return street name.
	 */  
        public String getStreet(){
            return streetName; 
        }
}
