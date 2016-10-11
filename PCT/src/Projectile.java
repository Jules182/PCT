package _2016._09.assessments.assignment01.template;
/**
 * This application allows the user to calculate range, max height and flight time for a projectile 
 * following the entering of mass (currently not used), angle of launch, and initial speed.
 * There is error checking to ensure that suitable values are entered. 
 */



// imports - many are missing

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import java.text.DecimalFormat;

// class definition
public class Projectile extends Application {

	// init method
	public void init() {

		// Projectile Type - ComboBox


		// Inital Speed ToggleGroup
		//use the .setUserData command of the radio button to store speeds
		initial_speed_slow.setUserData("10");

		// Prevent the following TextFields from being editable: angle,intial speed range, height, time 

		// Layout controls as per the diagram, feel free to improve the UI. 
		// How many rows and columns do you want - work this out on paper first 
		// My version has 7 rows, you can look at the JavaFX API to see how to get controls to span more than one column

		// Method call (not declaration!)  to initialize the controls based on the projectile type.

		//  Listener for angle Slider to set angle TextTield and the angle variable 
		angle_slider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue){

			}
		});

		// Listener for inital_speed ToggleGroup to set initital_speed TextField
		this.initial_speed_toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle new_toggle) {

			}
		});

		// Listener to call the fire() method when the fire button is pressed

		// Listener to initialize control values if the projectile type is changed

		// Listener to initialize control values if the erase button is pressed

	}

	// Overridden start method
	public void start(Stage primaryStage) {
		// set a title on the window, set a scene, size, and show the window
	}

	// Overridden stop method add functionality to this if you wish.
	public void stop() {

	}

	// Entry point to our program
	public static void main(String[] args) {

	}

	// Method to harvest values from controls, perform calculation and display the results
	private void fire(){
		//capture the values from the text fields outputting number errors where relevant 

		// don't forget to convert your angle input to radians for use with Math.sin()

		// calculate the range of the projectile	

		// calculate the flight time of the projectile

		// calculate the max height of the projectile

		// display the results in the relevant TextFields
	}

	// Method to initalize the controls based on the selection of the projectile type 
	private void initalizeControlValues(){
		//if the projectile type is Adult Human then 
	
			//inital the mass to 80kg
	
			//Set slider scale 0 to 90, set slider value to 45 and ticks to 10 units
	
			// initalize the intital speed to fast
		}
		// else 
		
			//inital the mass to 400kg
		
			//Set slider scale 0 to 40, set slider value to 20 and ticks to 10 units
		
			// initalize the intial speed to slow
			this.initial_speed_slow.setSelected(true);
			this.intitial_speed_textField.setText((String) this.initial_speed_slow.getUserData());
		}
		// display ticks etc
		

		// clear the results fields and variables
	}

	//The following variables SHOULD be initialized where appropriate as declaring and 
	// initializing separately is very verbose. 

	//Layout
	private GridPane gp;

	//Projectile Type
	private Label projectile_type_label;
	private ComboBox<String> projectile_type_combobox;

	// Mass
	private Label mass_label;
	private TextField mass_textField ;
	private double mass;

	//Angle
	private Label angle_label ;
	private Slider angle_slider;
	private TextField angle_textField ;
	private double angle;
	//Formating the values in the duration box
	DecimalFormat df ;

	//Initial Speed 
	private Label initial_speed_label ;
	private ToggleGroup initial_speed_toggleGroup ;
	private RadioButton initial_speed_slow;
	private RadioButton initial_speed_medium;
	private RadioButton initial_speed_fast;
	private TextField intitial_speed_textField;
	private double initial_speed;

	//Range
	private Label range_label;
	private TextField range_textField;
	private double range;

	//Height
	private Label height_label;
	private TextField height_textField;
	private double height;

	//Time
	private Label time_label;
	private TextField time_textField ;
	private double time; 

	//Gravity 
	private static final double gravitational_accelleration=9.81; // m/s/s

	//Calculate
	private Button fire_button;
	private Button erase_button;
}

