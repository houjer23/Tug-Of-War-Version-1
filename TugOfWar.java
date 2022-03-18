//Add Phidgets and doodlepad Library 
// class to play tug of war game

import doodlepad.*;
import com.phidget22.*;

public class TugOfWar {
    
    private String mode = "day";
	private RoundRect change_background;
	private RoundRect change_mode;
	private Rectangle background;
	private Rectangle ground;
	private Oval sun;
	private Text who_win;
	
	public TugOfWar() { // The Constructor: Graphically sets the scence
		
		// Sets the back ground and the grass
		background = new Rectangle(0, 0, 600, 480);
		background.setFillColor(0, 191, 255);
		ground = new Rectangle(0, 480, 600, 120);
		ground.setFillColor(34, 139, 34);
		
		// Sets the physical stand
		int stand_x = 100;
		int stand_width = 400;
		int stand_top_height = 60;
		int stand_top_y = 220;
		Rectangle r1 = new Rectangle(stand_x, stand_top_y, stand_width, stand_top_height);
		Rectangle r2 = new Rectangle(stand_x + 50, stand_top_y + stand_top_height, 60, 150);
		Rectangle r3 = new Rectangle(stand_x + stand_width - 110, stand_top_y + stand_top_height, 60, 150);
		Rectangle r4 = new Rectangle(stand_x, stand_top_y + stand_top_height + 150, stand_width, 130);
		r1.setFillColor(139, 69, 19);
		r2.setFillColor(139, 69, 19);
		r3.setFillColor(139, 69, 19);
		r4.setFillColor(139, 69, 19);
		sun = new Oval(500, -40, 150, 150);
		sun.setFillColor(250,250,0);
		
		// Sets the cup
		double[] cup_x = new double[] {240, 280, 272, 248};
        double[] cup_y = new double[] {375, 375, 430, 430};
        Polygon cup = new Polygon(cup_x, cup_y);
        cup.setFillColor(255, 0, 0);
        
        // Sets the change backgound bottom
        change_background = new RoundRect(120, 80, 170, 80, 30, 30);
        Text corlor_change_1 = new Text("Backgound Color", 128, 95, 20);
        Text corlor_change_2 = new Text("      Change", 135, 125, 20);
        change_background.setMousePressedHandler(this::onPressed); // Call onPressed method if the change backgound bottom is pressed
        
        //Sets the change mode bottom
        change_mode = new RoundRect(300, 80, 80, 80, 30, 30);
        Text change_mode_1 = new Text(" Mode", 310, 95, 20);
        Text change_mode_2 = new Text("Change", 305, 125, 20);
        change_mode.setMousePressedHandler(this::onPressed2); // Call onPressed2 method if the change mode bottom is pressed
        
        // Text to show who win
        RoundRect who_win_box = new RoundRect(235, 450, 100, 60, 30, 30);
        who_win = new Text("Who Win", 245, 465, 20);
	} // end of the Consturctor
	
	private void onPressed(Shape shp, double x, double y, int button) { // Start of the onPressed method
		// generate random colors for background
		background.setFillColor(Math.random() * 255, Math.random() * 255, Math.random() * 255);
		ground.setFillColor(Math.random() * 255, Math.random() * 255, Math.random() * 255);
    } // end of the onPressed method
    
    private void onPressed2(Shape shp, double x, double y, int button) { // Start of the onPressed2
		// This method changes the mode
		if (mode.equals("night")) { // if the current mode is night mode, change to day mode
			background.setFillColor(0, 191, 255);
			ground.setFillColor(34, 139, 34);
			sun.setFillColor(250,250,0);
			mode = "day";
		} else if (mode.equals("day")) { // if the current mode is day mode, change to noon mode
			background.setFillColor(255, 212, 0);
			ground.setFillColor(128, 128, 0);
			sun.setFillColor(250,0,0);
			mode = "noon";
		} else { // if the current mode is day mode, change to night mode
			background.setFillColor(0, 0, 0);
			ground.setFillColor(0, 45, 0);
			sun.setFillColor(255, 255, 255);
			mode = "night";
		}
    } // end of the onPressed2
    
    public void update_winner(String winner) { // this method updates the winner graphically
		who_win.setText(winner);
		if (winner.equals("Red")) {
			sun.setFillColor(250,0,0);
			ground.setFillColor(205, 92, 92);
			background.setFillColor(250, 128, 114);
		} else {
			sun.setFillColor(34, 139, 34);
			ground.setFillColor(50, 205, 50);
			background.setFillColor(0, 250, 154);
		}
	} // end of update winner method
    
    public static void main(String[] args) throws Exception{ // main method
        //Create 
        DigitalInput redButton = new DigitalInput();
        DigitalOutput redLED = new DigitalOutput();
        DigitalInput greenButton = new DigitalInput();
        DigitalOutput greenLED = new DigitalOutput();

        //Address 
        redButton.setHubPort(0);
        redButton.setIsHubPortDevice(true);
        redLED.setHubPort(1);
        redLED.setIsHubPortDevice(true);
        greenButton.setHubPort(5);
        greenButton.setIsHubPortDevice(true);
        greenLED.setHubPort(4);
        greenLED.setIsHubPortDevice(true);

        //Open 
        redButton.open(1000);
        redLED.open(1000);
        greenButton.open(1000);
        greenLED.open(1000);
        
        TugOfWar lemonade_stand = new TugOfWar();
        
        int num_red = 0;
        int num_green = 0;
        //Use your Phidgets, playing tug of war
        System.out.println(num_red + " " + num_green);
        while(true){
			System.out.println(num_red + " " + num_green);
			if (redButton.getState()) { // if red bottom is pressed, update number of press
				num_red ++;
			}
			if (greenButton.getState()) { // if green bottom is pressed, update number of press
				num_green ++;
			}
			System.out.println(num_red + " " + num_green);
			Thread.sleep(150);
			if (Math.abs(num_red - num_green) >= 10) { // if 10 more than the other person, stop the game
				break;
			}
        }
        // check who won the game, update status of phidgets and doodlepad
        if (num_red > num_green) {
			redLED.setState(true);
			lemonade_stand.update_winner("Red");
		} else {
			greenLED.setState(true);
			lemonade_stand.update_winner("Green");
		}
    } // end of main
} // end of the class
