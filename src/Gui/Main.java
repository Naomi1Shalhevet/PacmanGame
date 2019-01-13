package Gui;
import javax.swing.JFrame;

/**
 * Main class to run the gui 
 * @author Shalhevet Gamliel and Naomi Oyer
 * 
 */
public class Main 
{
	public static void main(String[] args)
	{
		MainWindow window = new MainWindow();
		window.setSize(window.myImage.getWidth(),window.myImage.getHeight());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		
	}
}