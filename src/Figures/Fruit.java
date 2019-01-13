package Figures;
import Coordss.map;
import Geomm.Point3D;

/**
 * The class represents the fruit
 * @author shalhevet and Naomi
 *
 */
public class Fruit {
	
// point of fruit
	private Point3D point_Fruit;
	
	/**
	 * function get of fruit
	 * @return point_Fruit
	 */
	public Point3D getPoint_Fruit() {
		
		return this.point_Fruit;
	}

	/**
	 * A constructor reads accepts an array of string and creates a gps point
	 * @param line
	 */
	public Fruit(String [] line) {
		
		double lat = Double.parseDouble(line[2]);
		double lon = Double.parseDouble(line[3]);
		double alt = 0 ;

		point_Fruit = new Point3D(lat,lon,alt);
	}


}


