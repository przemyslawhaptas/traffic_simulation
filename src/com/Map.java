package com;

/**
 *
 * @author Albert Podraza
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import java.io.Serializable;
import java.util.HashMap;

        /** 
         * Map contains data from OSM XML file, which builds graph, and method findShortestWay,
         * that finds the shortest way between 2 points by Djikstra's algorythm from JUNG library.
         * Moreover there is also some help structures and methods.
	 */
public class Map implements Serializable {
	private  Graph<Crossing, Road> g;
	private  LinkedList<Road> ways;
        private final  HashMap<String, Crossing> verticles;
	private Crossing start,stop;
        private final  LinkedList<Crossing> vStartOne;
        private final  LinkedList<Crossing> vStartTwo;
        private final  LinkedList<Crossing> vStopOne;
        private final  LinkedList<Crossing> vStopTwo;
	
	public Map(){
		g = new SparseMultigraph<Crossing, Road>();
		ways=new LinkedList<Road>();
                vStartTwo=new LinkedList<Crossing>();
                vStopOne=new LinkedList<Crossing>();
                vStopTwo=new LinkedList<Crossing>();
                vStartOne=new LinkedList<Crossing>();
                verticles=new HashMap<String, Crossing>();
	}
        
        
        /** 
         * Returning private data with processed ways for external use
         * @return processed LinkedList of ways from XML file.         
	 */
        public LinkedList<Road> getWays(){
            return ways;
        }

        /** 
         * Returning private data with graph for external use
         * @return SparseGraph from Map class.
	 */        
        public Graph<Crossing, Road> getGraph(){
            return  g;
        }
        
        public void setGraph(SparseMultigraph<Crossing, Road> _g){
            System.out.println("Dzialamy");
            System.out.println(g.getEdgeCount());
            System.out.println(_g.getVertexCount());
            g=_g;
            
            
        }
        
        /** 
         * Returning private data with graph's verticles for external use
         * @return processed nodes from XML file.         
	 */        
        public HashMap<String, Crossing> getVerticles(){
            return verticles;
        }
        
        /** 
         * Method making graph's edges by processing ways from LinkedList after checking that list
         * isn't empty.         
	 */
	public void makeEdges(){
            if (!ways.isEmpty()){
                int Size=ways.size();
                for(int i=0;i<Size;i++){              
                    g.addEdge(ways.get(i), ways.get(i).getStartVertex(), ways.get(i).getStopVertex());               
                }
                verticles.clear();
            }	
        }
       
	public void setNewGraph( LinkedList<Road> tmp){
            if(!ways.isEmpty()){
                ways.remove();
            }
            ways=tmp;
            makeEdges();
        }                
        /** 
         * Method finds path between 2 streets by Djikstra algorithm from JUNG library.
         * @param  startOne Name of first start point of path
         * @param  startTwo Name of second start point of path
         * @param  stopOne  Name of first end point of path
         * @param  stopTwo  Name of second end point of path
         * @return text with result.         
	 */

	public String findShortestPathByWeight(String startOne,String startTwo,String stopOne,String stopTwo){            
            int Size=ways.size();
            for(int i=0;i<Size;i++){         //search edges to find desired edges 
                if(ways.get(i).getStreet().equals(startOne)){
                    vStartOne.add(ways.get(i).getStartVertex());
                    vStartOne.add(ways.get(i).getStopVertex());
                }
                if(ways.get(i).getStreet().equals(startTwo)){
                    vStartTwo.add(ways.get(i).getStartVertex());
                    vStartTwo.add(ways.get(i).getStopVertex());
                }
                                
                if(ways.get(i).getStreet().equals(stopOne)){
                    vStopOne.add(ways.get(i).getStartVertex());
                    vStopOne.add(ways.get(i).getStopVertex());
                }
                if(ways.get(i).getStreet().equals(stopTwo)){
                    vStopTwo.add(ways.get(i).getStartVertex());
                    vStopTwo.add(ways.get(i).getStopVertex());
                }
            }
            int i=0;
            for(int j=0;j<vStartOne.size();j++){          //looking for start point
                Crossing x=vStartOne.get(j);
                i=0;
                while(i<vStartTwo.size()){          
                    if( x.getLat()==vStartTwo.get(i).getLat()&& i!=j){
                        start=x;
                    }
                    i++;
                }  
            }            
            for(int j=0;j<vStopOne.size();j++){             //looking for stop point
                Crossing y=vStopOne.get(j);
                i=0;
                while(i<vStopTwo.size()){                
                    if( y.getLat()==vStopTwo.get(i).getLat() && i!=j){
                        stop=y; 
                    }
                    i++;
                }  
            }
	    Transformer<Road, Double> wtTransformer = new Transformer<Road, Double>(){
                @Override
                public Double transform(Road link) {
                return link.getWeight();
                }
	    };
            String output=new String();                         
            DijkstraShortestPath<Crossing, Road> alg = new DijkstraShortestPath<Crossing, Road>(g,wtTransformer);
            if(start==null || stop==null){              //first validator
                output="One of street name isn't valid or junction doesn't exist\n";
            }
            else{
                List<Road> l = alg.getPath(start, stop);
                Number dist = alg.getDistance(start, stop);    
                if(dist==null){                         //second validator
                    output="There isn't path between that points\n";
                }
                else{
                    output=formatOutput(l,stopOne,dist);
                }
            }    
            return output;
	}

        /**
         * Formating text data from findShortestPathByWeight method.
         * @param  l List of streets between two points from algorithm
         * @param  _stop  Name of end point of path
         * @param  dist distance betweent two points
         * @return text with formatted result.         
	 */
        private String formatOutput(List<Road> l,String _stop, Number dist){
            ArrayList<String> list = new ArrayList<String>();
	    list.add(l.get(0).getStreet());
            String print=new String();
            for(int i=0, k=1;i<l.size();i++){
                if(!list.get(k-1).equals(l.get(i).getStreet())){                                            
                    list.add(l.get(i).getStreet());
                    if(k%3==0 ){
                        print+=l.get(i).getStreet()+", \n";
                    }
                    else if(l.get(i).getStreet().equals(_stop)){
                        print+=l.get(i).getStreet()+" ";
                    }
                    else{
                        print+=l.get(i).getStreet()+", ";
                    }
                    k++;
                }		    										
	    }
            java.text.DecimalFormat df=new java.text.DecimalFormat();
            df.setMaximumFractionDigits(2);
            print+=" and the length of the path is: " + df.format(dist.doubleValue()*111.1)+"km.\n";
            return print;
        }
}	
