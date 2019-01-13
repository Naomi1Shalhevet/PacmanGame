package Coords;
import Geom.Point3D;

/**
 * The class creates converting functions from gps to pixel and 
 * @author Shalhevet Gamliel and Naomi Oyer
 *
 */
public class  map{

	// offsets
	static final double mapLongitudeStart =35.202369;
	static final double mapLatitudeStart = 32.105728;
	static final double mapLongitude = 35.212416-mapLongitudeStart;
	static final double mapLatitude = mapLatitudeStart-32.101898;

	/**
	 * Takes a gps point and converts it into a pixel
	 * @param width
	 * @param height
	 * @param p
	 * @return the converted point
	 */
	public static Point3D GPStoPixels (double width,double height,Point3D p )
	{
		Point3D pnew=new Point3D (p);
		double y = pnew.y()-mapLongitudeStart;
		double x = mapLatitudeStart-pnew.x();

		int py = (int) (width*(y/mapLongitude));
		int px= (int) (height*(x/mapLatitude));

		return new Point3D(px, py);
	}

	/**
	 * Takes a pixel point and converts it into a gps point
	 * @param width
	 * @param height
	 * @param p
	 * @return the converted point
	 */
	public static  Point3D PixelstoGPS(double width,double height,Point3D p)
	{
		double y=((p.x()*mapLongitude)/width)+mapLongitudeStart;
		double x=-((p.y()*mapLatitude)/height)+mapLatitudeStart;

		return new Point3D (x,y);
	}
	
/**
 * Takes two pixel points and calculates the angle between them
 * @param width
 * @param height
 * @param pix1
 * @param pix2
 * @return the angle
 */
	public static double FindAngle(double width,double height, Point3D pix1, Point3D pix2)
	{
		Point3D gps1=new Point3D (map.PixelstoGPS(1443, 642, pix1));
		Point3D gps2=new Point3D (map.PixelstoGPS(1443, 642, pix2));
		Coords c=new Coords();
		double [] b=c.azimuth_elevation_dist(gps1, gps2);
		return b[0];
	}


}