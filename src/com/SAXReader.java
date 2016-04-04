package com;

/**
 *
 * @author Albert Podraza
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
         /** 
         * Class using SAX library to read OSM XML and delivering important to Map's instance class.
	 */
public class SAXReader 
{
	private static String street;
	private static LinkedList<String> ref;
	private static boolean highwayCheck,streetCheck;
        
        /** 
         * This method reads XML file using the SAX library and filling map object
         * @param map instance of class with all necessary data
	 */  
	public static void getContent(Map map){
            {
            ref =new LinkedList<String>();
            highwayCheck=false;
            streetCheck=false;
            try {
    	        SAXParserFactory factory = SAXParserFactory.newInstance();
    	        SAXParser saxParser = factory.newSAXParser();
                DefaultHandler handler = new DefaultHandler() {

                  @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {    	       		
                        if(qName.equals("node")){
                            map.getVerticles().put(attributes.getValue(0), new Crossing(attributes.getValue(1),attributes.getValue(2)));
                        }
                        else if(qName.equals("way")){  
                            ref.clear();
                            highwayCheck=false;
                            streetCheck=false; 
                        }
                        else if(qName.equals("nd")){
                            ref.add(attributes.getValue("ref"));
                        }
                        else if(qName.equals("tag")&&((attributes.getValue("k")).equals("highway"))){
                            highwayCheck=true;
                        }
                        else if(qName.equals("tag")&&(attributes.getValue("k").equals("name"))){
                            street =attributes.getValue("v");
                            streetCheck=true;    	       			
                        }
                    }
                        @Override
                    public void endElement(String uri, String localName,String qName) throws SAXException {
                        if(qName.equals("way")){
                            if(highwayCheck && streetCheck  && street !=null && (ref.size()>1)){
                                for(int i=1;i< ref.size();i++){
                                    map.getWays().add(new Road(street, ref,i,map));
                                }
                            }
                        }
                    }

                };
                File file = new File("exampleMap");
                InputStream inputStream= new FileInputStream(file);
                Reader reader = new InputStreamReader(inputStream,"UTF-8");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");
                saxParser.parse(is, handler);
                } catch (ParserConfigurationException | SAXException | IOException e) {
                }
            }
        }
}