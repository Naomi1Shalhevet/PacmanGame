package Figures;

import Coordss.map;
import Geomm.Point3D;

/**
 * This class represents a box in the game
 * @author Shalhevet Gamliel and Naomi Oyer
 *
 */
public class Box {
	private Point3D RightUp;
	private Point3D LeftDown;
	private Point3D RightDown; 
	private Point3D LeftUp;
	private double MapWidth = 1433;
	private double MapHeight = 642;

	/**
	 * Constructor that gets a string and uses the data
	 * 
	 */

	public Box(String []arr)
	{
		LeftDown=new Point3D(Double.parseDouble(arr[2]),Double.parseDouble(arr[3]),Double.parseDouble(arr[4]));
		RightUp=new Point3D(Double.parseDouble(arr[5]),Double.parseDouble(arr[6]),Double.parseDouble(arr[7]));
		RightDown=new Point3D(RightUp.x(),LeftDown.y());
		LeftUp=new Point3D (LeftDown.x(), RightUp.y());


	}

	/**
	 * Constructor that gets three points and creates a box
	 * @param b
	 */
	public Box(Box b)
	{
		LeftDown=b.getLeftDown();
		RightUp=b.getRightUp();
		RightDown=b.getRightDown();
	}

	/**
	 * Get function for right up corner of the box
	 * @return
	 */
	public Point3D getRightUp()
	{
		return this.RightUp;
	}

	/**
	 * Get function for left down corner of the box
	 * @return the point
	 */
	public Point3D getLeftDown()
	{
		return this.LeftDown;
	}

	/**
	 * Get function for right down corner of the box
	 * @return the point
	 */
	public Point3D getRightDown() 
	{
		return this.RightDown;
	}

	/**
	 * Get function for left up corner of the box
	 * @return the point
	 */
	public Point3D getLeftUp() {
		return LeftUp;
	}

	/**
	 * Function that gets a point and verifies if it is inside one of the boxes
	 * @param point
	 * @param IsGPS
	 * @return
	 */
	public boolean inTheBox (Point3D point, boolean IsGPS)
	{
		Point3D TopInp=map.GPStoPixels(MapWidth, MapHeight, RightUp);
		Point3D downInp=map.GPStoPixels(MapWidth, MapHeight, LeftDown);
		if (IsGPS) {
			point=map.GPStoPixels(MapWidth, MapHeight, point);
		}
		if (point.iy()<TopInp.iy()&&point.iy()>downInp.iy())
			if (point.ix()<downInp.ix()&&point.ix()>TopInp.ix())
			{

				return false;
			}
		return true;

	}

	/**
	 * Function that calculates and returns the width of the box (rectangle)
	 * @param width
	 * @param height
	 * @return
	 */
	public int getWidth(int width, int height)
	{
		Point3D pixPD=map.GPStoPixels(width, height, LeftDown);
		Point3D pixPT=map.GPStoPixels(width, height, RightUp);
		return (int)(pixPD.x()-pixPT.x());
	}
	/**
	 * Function that calculates and returns the height of the box (rectangle)
	 * @param width
	 * @param height
	 * @return
	 */
	public int getHeight(int width, int height)
	{
		Point3D pixPD=map.GPStoPixels(width, height, LeftDown);
		Point3D pixPT=map.GPStoPixels(width, height,RightUp);
		return (int)(pixPT.y()-pixPD.y());
	}

}
