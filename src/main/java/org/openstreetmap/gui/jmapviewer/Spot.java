package org.openstreetmap.gui.jmapviewer;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Nikodem on 17.05.2016.
 */
public class Spot {
    private String lat;
    private String lon;

    public static ArrayList<Spot> allSpots = new ArrayList<>();

    public Spot(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
        allSpots.add(this);
    }

    public double getLat(){
        Scanner s = new Scanner(this.lat);
        s.useLocale(Locale.US);
        double result = 0.0;
        if(s.hasNext())
            result = s.nextDouble();
        return result;
    }

    public double getLon(){
        Scanner s = new Scanner(this.lon);
        s.useLocale(Locale.US);
        double result = 0.0;
        if(s.hasNext())
            result = s.nextDouble();
        return result;
    }

    public static ArrayList getAllSpots(){
        return allSpots;
    }

    public static double distance() {
        String unit = "K";
        ArrayList<Spot> T = getAllSpots();
        double sum = 0;
        for (int i = 0; i < T.size() - 1; i++) {
            double lon1 = T.get(i).getLon();
            double lon2 = T.get(i+1).getLon();
            double lat1 = T.get(i).getLat();
            double lat2 = T.get(i+1).getLat();
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
                sum += dist;
            } else if (unit == "N") {
                dist = dist * 0.8684;
                sum += dist;
            }
        }

/*      double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        }
        else if (unit == "N") {
            dist = dist * 0.8684;
        }
*/

        return (sum);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
