package Graph;

import java.util.ArrayList;
import java.util.Arrays;

import Coordss.map;
import graph.Graph;
import graph.Node;
import Figures.Box;
import Figures.Fruit;
import Figures.Me;
import Geomm.Point3D;

/**
 * 
 * @author Shalhevet Gamliel and Naomi Oyer
 *
 */
public class MyGraph {

	ArrayList <Box> mybox;
	String source="a";
	String target="b";

	/**
	 * Constructor that gets an array list of boxes points
	 * @param boxlist
	 */
	public MyGraph (ArrayList boxlist)
	{
		this.mybox=boxlist;
	}

	/**
	 * Function that calculates the points on the boxes
	 * @param fruit
	 * @param me
	 * @return
	 */
	public Point3D [] BoxCorners (Fruit fruit, Me me)
	{
		Point3D [] boxCorners=new Point3D [mybox.size()*4];
		int index=0;
		for (int i=0;i<boxCorners.length;i=i+4)
		{
			boxCorners[i]=new Point3D (mybox.get(index).getRightDown());
			boxCorners[i+1]=new Point3D (mybox.get(index).getLeftDown());
			boxCorners[i+2]=new Point3D (mybox.get(index).getRightDown());
			boxCorners[i+3]=new Point3D (mybox.get(index).getRightUp());
			System.out.println(i);
			index++;
		}

		Point3D [] point_arr=BoxLimits (this.mybox,boxCorners);
		//	Graph myGraph = new Graph(); 
		point_arr[0]=new Point3D (map.GPStoPixels(1433, 642,me.getPoint_Me()));
		point_arr[point_arr.length-1]=new Point3D (map.GPStoPixels(1433, 642,fruit.getPoint_Fruit()));
		//		myGraph.add(new Node(source)); // Node "a" (0)
		//		for(int i=1;i<point_arr.length-1;i++) {
		//			Node node = new Node(""+i);
		//			myGraph.add(node);
		//		}
		//		myGraph.add(new Node(target)); // Node "b" (15)
		return point_arr;
	}

	/**
	 * Function that returns if a point is on the limits of a box
	 * @param boxpoints
	 * @param point_arr
	 * @return
	 */
	public Point3D [] BoxLimits (ArrayList <Box> boxpoints, Point3D [] point_arr){

		int counter=0;
		boolean Limit=true;
		boolean gpsPoint=true;

		for (int i=0; i<point_arr.length;i++)
		{
			Limit=true;
			for (int j=0;j<boxpoints.size();j++)
			{
				if (boxpoints.get(j).inTheBox(point_arr[i], gpsPoint)==false)
					Limit=false;
			}
			if (Limit==true)
				counter++;
		}
		Point3D [] point_arr2=new Point3D [counter+2];
		counter=1;

		for (int i=0; i<point_arr.length;i++)
		{
			Limit=true;
			for (int j=0;j<boxpoints.size();j++)
			{
				if (boxpoints.get(j).inTheBox(point_arr[i], gpsPoint)==false)
					Limit=false;
			}
			if (Limit==true){
				point_arr2[counter]=map.GPStoPixels(1433, 642,point_arr[i]);
				counter++; }
		}
		return point_arr2;
	}
}
