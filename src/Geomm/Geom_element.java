package Geomm;

import Geomm.Point3D;

/**
 * This is an interface that calculates geometric functions in space
 * @author Shalhevet Gamliel and Naomi Oyer
 *
 */

public interface Geom_element {
	public double distance3D(Point3D p) ;
	public double distance2D(Point3D p);
	public Point3D getPoint();
	public double x();
	public double y();
	public double z();
	
	
	
}
