package Figures;
import Geomm.Point3D;



/**
 * The class represents the main player  
 * @author shalhevet and Naomi
 *
 */
public class Me {

	Point3D point_Me;

	/**
	 * Constructor that gets two values and sets them as a location for the player
	 * @param d
	 * @param e
	 */
	public Me(double d,double e)
	{
		point_Me=new Point3D ( d,  e);
	}


	/**
	 * function get of point_Me
	 * @return point_Me
	 */
	public Point3D getPoint_Me (){
		return point_Me;
	}

	/**
	 * function set of point_Me
	 * @param point_Me
	 */
	public void setPoint_Me (Point3D point_Me){
		this.point_Me = point_Me;
	}


	/**
	 * A constructor that reads an array of string
	 * @param line
	 */
	public Me(String []line){

		double lat = Double.parseDouble(line[2]);
		double lon = Double.parseDouble(line[3]);
		double alt = 0 ;

		point_Me = new Point3D (lat,lon,alt);
	}



}