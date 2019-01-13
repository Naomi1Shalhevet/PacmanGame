package Figures;
import Coordss.map;
import Geomm.Point3D;

/**
 * 
 * @author shalhevet and naomi
 * The class represents ghosts points in gps 
 */
public class Ghost {

	//point of ghost
	private Point3D point_Ghost;
	
	
	/**
	 * Gps point get function
	 * @return points_Ghost
	 */
	public Point3D getpoint_Ghost(){
		return this.point_Ghost;
	}


	/**
	 * A constructor that reads an array of string 
	 */
	public Ghost(String [] line) {
		
		double lat = Double.parseDouble(line[2]);
		double lon = Double.parseDouble(line[3]);
		double alt = 0 ;
		
		point_Ghost = new Point3D(lat,lon,alt);
	}


}
