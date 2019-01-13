package Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Coords.map;
//import org.omg.PortableServer.THREAD_POLICY_ID;
import Geom.Point3D;
import Graph.MyGraph;
import Robot.Play;
import Threads.MyThread;
import Figures.Box;
import Figures.Fruit;
import Figures.Me;
import Figures.Packman;
import Figures.ghost;


public class MainWindow extends JFrame implements MouseListener{

	public BufferedImage myImage;
	ArrayList<Packman> pack_array = new ArrayList<Packman>();
	ArrayList<Fruit> fruit_array = new ArrayList<Fruit>();
	ArrayList<ghost> ghost_array = new ArrayList<ghost>();
	ArrayList<Box> box_array = new ArrayList<Box>();
	Point3D [] BoxCorners;
	Me me=new Me (32.101898,35.202369);
	Play server = new Play();
	double angle=0;
	boolean IsRuning=false;
	boolean IsPlayer=false;
	boolean IsBox=false;

	
	
	public MainWindow() 
	{
		initGUI();	
		this.addMouseListener(this); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private void initGUI() 
	{ 

		MenuBar menuBar = new MenuBar();

		Menu myGame= new Menu("Pacman Game");
		MenuItem csv = new MenuItem("Choose csv");
		MenuItem Meplace = new MenuItem("Place your player");
		MenuItem run = new MenuItem("Run the game");

		
		csv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OpenFile();
			}
		});
		
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IsRuning=true;
				server.start();
				RestartGame();
			}
		});
		
	
	
		Meplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IsPlayer=true;	
			}
		});
		
		
		menuBar.add(myGame);
		myGame.add(csv);
		myGame.add(Meplace);
		myGame.add(run);

		setMenuBar(menuBar);



		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
		

		} catch (IOException e) {
			e.printStackTrace();
		}

	

		this.setSize(myImage.getWidth(),myImage.getHeight());
		
		
	}

	public void paint(Graphics g)
	{
		
		g.drawImage(myImage,0 , 0,getWidth(),getHeight(), this);
		

		
		if (box_array!=null) 
		{ 
			Iterator<Box> itr=box_array.iterator(); 
			while(itr.hasNext()) {
			
				Box box=new Box(itr.next());
				
				double width=box.getWidth(getWidth(), getHeight());
				double height=box.getHeight(getWidth(),getHeight());
				
				Point3D rect=map.GPStoPixels(getWidth(),getHeight(),box.getPointStart());
				
				g.setColor(Color.black); 
				g.fillRect((int) rect.y(), (int)rect.x(), (int)height ,(int)width); 
			}
		}
		

		if(pack_array!=null) 
		{
			for (int i=0;i<pack_array.size();i++)
			{
				Point3D pac=map.GPStoPixels(getWidth(), getHeight(), pack_array.get(i).getPoint());
				g.setColor(Color.YELLOW);
				g.fillOval(pac.iy(), pac.ix(), 25,25);

			}
		}

		
		if(fruit_array!=null) 
		{

			for (int i=0;i<fruit_array.size();i++)
			{
				Point3D fruit=map.GPStoPixels(getWidth(), getHeight(), fruit_array.get(i).getPoint());
				g.setColor(Color.RED);
				g.fillOval(fruit.iy(), fruit.ix(), 15,15);
			}	
		}
		

		if (ghost_array!=null)
		{
			for (int i=0;i<ghost_array.size();i++)
			{

				Point3D ghost=map.GPStoPixels(getWidth(), getHeight(), ghost_array.get(i).getGpsPoint());
				g.setColor(Color.CYAN);
				g.fillOval(ghost.iy(), ghost.ix(), 20,20);

			}
		}
		

		if (BoxCorners!=null)
		{
			for (int i=0;i<BoxCorners.length;i++)
			{
				Point3D corner= BoxCorners[i];
				g.setColor(Color.green);
				g.fillOval(corner.iy(),corner.ix(),3,3);	
			}
		}
		

		/**
		 * 
		 */
		if (IsRuning) 
		{
			String []line=server.getBoard().iterator().next().split(",");//founding the rotate 
			me=new Me(line);
		}	
		
		Point3D p=map.GPStoPixels(getWidth(), getHeight(),me.getMe());
		g.setColor(Color.MAGENTA);
		g.fillOval(p.iy(), p.ix(), 25,25);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (IsRuning)
		{
			server.rotate(angle);
			RestartGame();
		}
		
		if(IsRuning)
		{
			if(server.isRuning()==false)
				IsRuning=false;
		}
	}


	/**
	 * reading from the csv
	 */
	public void OpenFile() {
//		entercsv=true;
		FileDialog file = new FileDialog(this, "Open text file", FileDialog.LOAD);
		file.setFile("*.csv");

		file.setDirectory("data");
		file.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		});
		
		file.setVisible(true);
//		String folder = file.getDirectory();
		String fileName = file.getFile();
		System.out.println(fileName);
		server = new Play("Data/"+fileName);
		server.setIDs(332515972, 316274042);
		RestartGame();

	}

	/**
	 * this function restart every time and restart all the arraylist with the new infomations
	 */
	public void RestartGame()
	{
		pack_array = new ArrayList<Packman>();
		fruit_array= new ArrayList<Fruit>();
		ghost_array= new ArrayList<ghost>();
		box_array= new ArrayList<Box> ();
		ArrayList<String> data = server.getBoard();
		
		for(int i=0;i<data.size();i++) 
		{
			//reading from the csv
			String[] csvLine = data.get(i).split(",");
			String obj=csvLine[0];
			switch(obj)
			{
			case "B": 
			{
				Box box=new Box(csvLine);
				box_array.add(box);
				IsBox=true;
				break;
			}
			case "F": 
			{
				Fruit fruit=new Fruit (csvLine);
				fruit_array.add(fruit);
				break;
			}
			case "G":
			{
				ghost ghost=new ghost (csvLine);
				ghost_array.add(ghost);
				break;
			}
			case "P": 
			{
				Packman packman=new Packman(csvLine);
				pack_array.add(packman);
				break;
			}
			}
		}
		
		if (IsBox) {
			MyGraph graph= new MyGraph(box_array);
		BoxCorners=graph.tachat(fruit_array.get(0), me);
		repaint();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		
		if(IsPlayer)
		{
			Point3D player=map.PixelstoGPS(getWidth(), getHeight(), new Point3D (x,y));
			server.setInitLocation(player.x(),player.y());
			me.setPoint(player);
			IsPlayer=false;
			repaint();
		}
		
		if (IsRuning)
		{
			String []arr=server.getBoard().iterator().next().split(",");//founding the rotate 
			me=new Me(arr);
			Point3D inv=map.GPStoPixels(getWidth(), getHeight(), me.getMe());
			Point3D ang=new Point3D (inv.y(),inv.x());
			angle=map.FindAngle(getWidth(), getHeight(),ang, new Point3D(x,y));
			server.rotate(angle);

		}


	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

//	public static void main(String[] args) {
//		MainWindow Window=  new MainWindow();
//		Window.setVisible(true);
//	}
}