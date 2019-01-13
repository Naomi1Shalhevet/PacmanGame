package Figures;

import java.util.ArrayList;

import Geomm.Point3D;

/**
 * This class represents a path of points
 * @author naomi
 *
 */
public class Path{
	
	Point3D path_point;
	ArrayList <Point3D> path;
	
	/**
	 * Constructor that gets a point and sets as the path
	 * @param p1
	 */
	public Path(Point3D p)
	{
		path=new ArrayList <Point3D>();
		this.path_point=p;	
	}

	/**
	 * Constructor that gets a point and adds it to the path 
	 * @param p
	 */
	public void add(Point3D p)
	{
		path.add(p);
	}
	
	/**
	 * Get function for a path point
	 * @return
	 */
	public Point3D getpath_point() {
		return path_point;
	}
	
	/**
	 * Get function for a path
	 * @return
	 */
	public ArrayList<Point3D> getPath() {
		return path;
	}
	
}