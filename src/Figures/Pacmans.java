package Figures;
import Coordss.map;
import Geomm.Point3D;

/**
 * The class represents the pacmans points 
 * @author shalhevet and Naomi
 *
 */
public class Pacmans {

	private Point3D point_pacman;

	/**
	 * The function of point_packman
	 * @return point_packman
	 */
	public Point3D getPoint_pacman(){
		return this.point_pacman;
	}


	/**
	 * A constructor that receives an array of string and creates a gps point
	 * @param line
	 */
	public Pacmans(String [] line){

		double lat = Double.parseDouble(line[2]);
		double lon = Double.parseDouble(line[3]);
		double alt = 0 ;

		point_pacman = new Point3D(lat,lon,alt);
	}

}
