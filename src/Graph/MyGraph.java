package Graph;

import java.util.ArrayList;
import java.util.Arrays;

import Coords.map;
import graph.Graph;
import graph.Node;
import Geom.Point3D;
import Figures.Box;
import Figures.Fruit;
import Figures.Me;

public class MyGraph {
	
	ArrayList <Box> mybox;
	String source="a";
	String target="b";

	public MyGraph (ArrayList boxlist)
	{
		this.mybox=boxlist;
	}

	public Point3D [] PlayBox (Fruit fruit, Me me)
	{
		Point3D [] boxCorners=new Point3D [mybox.size()*4];
		int index=0;
		for (int i=0;i<boxCorners.length;i=i+4)
		{
			boxCorners[i]=new Point3D (mybox.get(index).getDownRight());
			boxCorners[i+1]=new Point3D (mybox.get(index).getPointDown());
			boxCorners[i+2]=new Point3D (mybox.get(index).getPointStart());
			boxCorners[i+3]=new Point3D (mybox.get(index).getPointTop());
			System.out.println(i);
			index++;
		}

		Point3D [] arr=BoxLimits (this.mybox,boxCorners);
		Graph myGraph = new Graph(); 
		arr[0]=new Point3D (map.GPStoPixels(1433, 642,me.getMe()));
		arr[arr.length-1]=new Point3D (map.GPStoPixels(1433, 642,fruit.getPoint()));
		System.out.println(Arrays.toString(arr));
		System.out.println("length"+arr.length);
		myGraph.add(new Node(source)); // Node "a" (0)
		for(int i=1;i<arr.length-1;i++) {
			Node node = new Node(""+i);
			myGraph.add(node);

		}
		int count=0;
		myGraph.add(new Node(target)); // Node "b" (15)
		return arr;
	}


	public Point3D [] BoxLimits (ArrayList <Box> theboxs,Point3D [] arr){

		int counter=0;
		boolean flag=true;
		boolean gpsPoint=true;

		for (int i=0; i<arr.length;i++)
		{
			flag=true;
			for (int j=0;j<theboxs.size();j++)
			{
				
				if (theboxs.get(j).inTheBox(arr[i], gpsPoint)==false)
					flag=false;

			}
			if (flag==true)
				counter++;
		}
		Point3D [] arr2=new Point3D [counter+2];
		counter=1;
		
		for (int i=0; i<arr.length;i++)
		{
			flag=true;
			for (int j=0;j<theboxs.size();j++)
			{
				if (theboxs.get(j).inTheBox(arr[i], gpsPoint)==false)
					flag=false;

			}
			if (flag==true)
			{
				arr2[counter]=map.GPStoPixels(1433, 642,arr[i]);
				counter++;
			}
		}
		return arr2;
	}
}
