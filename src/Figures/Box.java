package Figures;

import Coords.map;
import Geom.Point3D;

public class Box {
	private Point3D pointTop;
	private Point3D pointDown;
	private Point3D pointStart;
	private Point3D downRight;
	private int limXleft;
	private int limXright;
	private int limYup;
	private int limYdown;
	private double MapWidth = 1433;
	private double MapHeight = 642;

	/**
	 * Constractor of the Box
	 */

	public Box(String []arr)
	{
		pointDown=new Point3D(Double.parseDouble(arr[2]),Double.parseDouble(arr[3]),Double.parseDouble(arr[4]));
		pointTop=new Point3D(Double.parseDouble(arr[5]),Double.parseDouble(arr[6]),Double.parseDouble(arr[7]));
		pointStart=new Point3D(pointTop.x(),pointDown.y());
		downRight=new Point3D (pointDown.x(), pointTop.y());
		limYdown=map.GPStoPixels(MapWidth, MapHeight, pointDown).ix()-1;
		 limYup=map.GPStoPixels(MapWidth, MapHeight, pointTop).ix()+1;
		 limXright=map.GPStoPixels(MapWidth, MapHeight, pointTop).iy()-1;
		limXleft=map.GPStoPixels(MapWidth, MapHeight, pointDown).iy()+1;
		
	}
	public int getLimXleft() {
		return limXleft;
	}
	public int getLimXright() {
		return limXright;
	}
	public int getLimYup() {
		return limYup;
	}
	public int getLimYdown() {
		return limYdown;
	}
	public Box(Box b)
	{
		pointDown=b.getPointDown();
		pointTop=b.getPointTop();
		pointStart=b.getPointStart();
	}
	public Point3D getPointTop()
	{
		return this.pointTop;
	}
	public Point3D getPointDown()
	{
		return this.pointDown;
	}
	public Point3D getPointStart() 
	{
		return this.pointStart;
	}
	public Point3D getDownRight() {
		return downRight;
	}
	
	
	public boolean inTheBox (Point3D pointInGps)
	{
		Point3D TopInp=map.GPStoPixels(MapWidth, MapHeight, pointTop);
		Point3D downInp=map.GPStoPixels(MapWidth, MapHeight, pointDown);
		Point3D P=map.GPStoPixels(MapWidth, MapHeight, pointInGps);
		if (P.iy()<TopInp.iy()&&P.iy()>downInp.iy())
			if (P.ix()<downInp.ix()&&P.ix()>TopInp.ix())
			{

				return false;
			}
		return true;

	}
	public boolean inTheBoxP (Point3D pointInP)
	{
		Point3D TopInp=map.GPStoPixels(MapWidth, MapHeight, pointTop);
		Point3D downInp=map.GPStoPixels(MapWidth, MapHeight, pointDown);
		if (pointInP.iy()<TopInp.iy()&&pointInP.iy()>downInp.iy())
			if (pointInP.ix()<downInp.ix()&&pointInP.ix()>TopInp.ix())
			{

				return false;
			}
		return true;

	}
	/**
	 * return the width that get from this rectangle
	 * @param width
	 * @param height
	 * @return
	 */
	public int getWidth(int width, int height)
	{
		Point3D pixPD=map.GPStoPixels(width, height, pointDown);
		Point3D pixPT=map.GPStoPixels(width, height, pointTop);
		return (int)(pixPD.x()-pixPT.x());
	}
	/**
	 * return the height that get from this rectangle
	 * @param width
	 * @param height
	 * @return
	 */
	public int getHeight(int width, int height)
	{
		Point3D pixPD=map.GPStoPixels(width, height, pointDown);
		Point3D pixPT=map.GPStoPixels(width, height,pointTop);
		return (int)(pixPT.y()-pixPD.y());
	}
	public String toString () {
		return "limXleft "+limXleft+" limXright "+limXright+" limYup "+limYup+" limYdown "+limYdown;	
	}
}
