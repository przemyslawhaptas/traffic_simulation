package com;

/**
 *
 * @author Albert Podraza
 */


        /** 
         * Class contains main() and initialize program         
	 */  
public class Activity  {

	public static void main(String[] args) {
                Map map=  new Map();
                System.out.println("Reading map in progress...");
                SAXReader.getContent(map);
                map.makeEdges();
                System.out.println("Done");
}


}
