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

import Coordss.map;
import Graph.MyGraph;
import Robot.Play;
import Figures.Box;
import Figures.Fruit;
import Figures.Ghost;
import Figures.Me;
import Figures.Pacmans;
import Geomm.Point3D;


/**
 * This class represents a gui class of a pacman game on a map
 * @author Shalhevet and Naomi
 *
 */
public class MainWindow extends JFrame implements MouseListener {

	ArrayList<Pacmans> pacman_array = null;
	ArrayList<Fruit> fruit_array = null;
	ArrayList<Ghost> ghost_array = null;
	ArrayList<Box> box_array = null;

	Me me = new Me (32.101898,35.202369);
	public BufferedImage myImage;
	Play server = new Play();
	double angle = 0;
	boolean Runing = false;
	boolean Player = false;
	Point3D [] BoxCorners = null;


	/**
	 * open the Window
	 */
	public MainWindow() {
		initGUI();	
		this.addMouseListener( this ); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	/**
	 * Creates the buttons and links functions to buttons
	 */
	private void initGUI() { 

		MenuBar menuBar = new MenuBar();

		Menu myGame= new Menu("Pacman Game");
		MenuItem csv = new MenuItem("Choose csv");
		MenuItem Meplace = new MenuItem("Place your player");
		MenuItem run = new MenuItem("Run the game");

		menuBar.add( myGame );
		myGame.add( csv )    ;
		myGame.add( Meplace );
		myGame.add( run )    ;

		setMenuBar(menuBar);

		/*run*/
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runing = true;
				server.start();
				RestartGame();
			}
		});


		/*csv*/
		csv.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				OpenFile();
			}
		});



		/*Meplace*/
		Meplace.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Player = true;	
			}
		});



		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		

	}

	/**
	 * The function is responsible for drawing the image
	 */
	public void paint(Graphics g) {

		g.drawImage(myImage,0 , 0,getWidth(),getHeight(), this);



		if (box_array!=null) { 
			Iterator<Box> itr = box_array.iterator(); 

			while(itr.hasNext()) {

				Box box = new Box(itr.next());

				double width = box.getWidth( getWidth(), getHeight() );
				double height = box.getHeight( getWidth() , getHeight() );

				Point3D rect = map.GPStoPixels( getWidth() , getHeight() , box.getRightDown());

				g.setColor(Color.black); 
				g.fillRect((int) rect.y(), (int)rect.x(), (int)height ,(int)width); 
			}
		}


		if(pacman_array!=null) {

			for (int i = 0 ; i<pacman_array.size() ; i++){

				g.setColor(Color.YELLOW);
				Point3D pac = map.GPStoPixels(getWidth(), getHeight(), pacman_array.get(i).getPoint_pacman());
				g.fillOval(pac.iy(), pac.ix(), 25,25);

			}
		}


		if(fruit_array!=null){

			for (int i = 0 ; i<fruit_array.size() ; i++) {

				g.setColor(Color.RED);
				Point3D fruit = map.GPStoPixels(getWidth(), getHeight(), fruit_array.get(i).getPoint_Fruit());
				g.fillOval(fruit.iy(), fruit.ix(), 15,15);
			}	
		}
		if (ghost_array!= null) {

			for (int i = 0 ; i<ghost_array.size(); i++) {

				g.setColor(Color.CYAN);
				Point3D ghost=map.GPStoPixels(getWidth(), getHeight(), ghost_array.get(i).getpoint_Ghost());
				g.fillOval(ghost.iy(), ghost.ix(), 20,20);

			}
		}

		if (BoxCorners!=null) {

			for (int i=0;i<BoxCorners.length;i++) {

				g.setColor(Color.green);
				Point3D corner= BoxCorners[i];
				g.fillOval(corner.iy(),corner.ix(),3,3);	
			}
		}

		if (Runing == true) {

			String []line = server.getBoard().iterator().next().split(","); 
			me = new Me(line);
		}	


		Point3D p = map.GPStoPixels(getWidth(), getHeight(),me.getPoint_Me());
		g.setColor(Color.MAGENTA);
		g.fillOval(p.iy(), p.ix(), 30,30);

		try {
			Thread.sleep(20);
			String info = server.getStatistics();
			System.out.println(info);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (Runing == true) {

			server.rotate(angle);
			RestartGame();
		}

		if(Runing == true) {

			if(server.isRuning()==false)
				Runing = false;
		}
	}


	/**
	 * The function read from the file csv
	 */
	public void OpenFile() {

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
		String fileName = file.getFile();
		System.out.println(fileName);
		server = new Play("Data/"+fileName);
		server.setIDs(332515972, 316274042);
		RestartGame();

	}

	/**
	 * The function accepts the arraylist of strings and inserts the appropriate arraylist
	 */
	public void RestartGame()
	{
		pacman_array = new ArrayList<Pacmans>();
		fruit_array = new ArrayList<Fruit>();
		ghost_array= new ArrayList<Ghost>();
		box_array = new ArrayList<Box> ();
		ArrayList<String> data = server.getBoard();

		for(int i=0;i<data.size();i++) {


			String[] csvLine = data.get(i).split(",");
			//String obj = csvLine[0];


			if (csvLine[0].equals("B")){
				Box box_new =new Box(csvLine);
				box_array.add(box_new);
				break;
			}

			if (csvLine[0].equals("F")){
				Fruit fruit_new =new Fruit (csvLine);
				fruit_array.add(fruit_new);
				break;
			}

			if (csvLine[0].equals("G")){
				Ghost ghost_new = new Ghost (csvLine);
				ghost_array.add(ghost_new);
				break;
			}
			if (csvLine[0].equals("P")) {
				Pacmans Pac_new =new Pacmans(csvLine);
				pacman_array.add(Pac_new);
				break;
			}

		}


		if (box_array != null) {
			MyGraph graph= new MyGraph(box_array);
			BoxCorners = graph.BoxCorners(fruit_array.get(0), me);



			repaint();
		}
	}

	/**
	 * setInitLocation to the player on the map and calculates the angle between the player the point clicked on the map 
	 *   
	 */

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();

		if(Player == true) {
			Point3D player = map.PixelstoGPS(getWidth(), getHeight(), new Point3D (x,y));
			server.setInitLocation(player.x(),player.y());
			me.setPoint_Me(player);
			Player = false;
			repaint();
		}

		if (Runing == true ){
			String []arr = server.getBoard().iterator().next().split(",");//founding the rotate 
			me = new Me(arr);
			Point3D inv = map.GPStoPixels(getWidth(), getHeight(), me.getPoint_Me());
			Point3D ang=new Point3D (inv.y(),inv.x());
			angle = map.FindAngle(getWidth(), getHeight(),ang, new Point3D(x,y));
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


}