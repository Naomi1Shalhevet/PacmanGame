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
	ArrayList <Box> box;
	String source="a";
	String target="b";
	private int counter;
	
	public MyGraph (ArrayList boxlist)
	{
		this.box=boxlist;
	}
	public Point3D [] tachat (Fruit fruit, Me me)
	{
		Point3D [] pp=new Point3D [box.size()*4];
		System.out.println(box.size());
		System.out.println(pp.length);
		int j=0;
		for (int i=0;i<pp.length;i=i+4)
		{
			pp[i]=new Point3D (box.get(j).getDownRight());
			pp[i+1]=new Point3D (box.get(j).getPointDown());
			pp[i+2]=new Point3D (box.get(j).getPointStart());
			pp[i+3]=new Point3D (box.get(j).getPointTop());
			System.out.println(i);
			j++;
		}

		Point3D [] arr=limitOfBOX (this.box,pp);
		Graph G = new Graph(); 
		arr[0]=new Point3D (map.GPStoPixels(1433, 642,me.getMe()));
		arr[arr.length-1]=new Point3D (map.GPStoPixels(1433, 642,fruit.getPoint()));
		System.out.println(Arrays.toString(arr));
		System.out.println("length"+arr.length);
		G.add(new Node(source)); // Node "a" (0)
		for(int i=1;i<arr.length-1;i++) {
			Node d = new Node(""+i);
			G.add(d);

		}
		int count=0;
		G.add(new Node(target)); // Node "b" (15)
		Point3D p1=new Point3D (876.0,507.0,0.0);
		Point3D p2=new Point3D (829.0,25.0,0.0);
		Line line=new Line (p1,p2);
		System.out.println(""+line.TherIsALine(box.get(0)));

		System.out.println("count"+count);
		return arr;
	}
	public Point3D [] limitOfBOX (ArrayList <Box> boxs,Point3D [] arr)
	{int counter=0;
	boolean flag=true;
	for (int i=0; i<arr.length;i++)
	{
		flag=true;
		for (int j=0;j<boxs.size();j++)
		{
			if (boxs.get(j).inTheBox(arr[i])==false)
				flag=false;

		}
		if (flag==true)
			counter++;
	}
	Point3D [] arr2=new Point3D [counter+2];
	counter=1
			;
	for (int i=0; i<arr.length;i++)
	{
		flag=true;
		for (int j=0;j<boxs.size();j++)
		{
			if (boxs.get(j).inTheBox(arr[i])==false)
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
