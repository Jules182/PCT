
/**
 * This application allows the user to calculate range, max height and flight time for a projectile 
 * following the entering of mass (currently not used), angle of launch, and initial speed.
 * There is error checking to ensure that suitable values are entered. 
 */

import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// class definition
public class Projectile extends Application {

	public void init() {
		// init method, setting default values
		// Projectile Type - ComboBox
		projectile_type_combobox.getItems().addAll("Adult Human", "Piano");
		projectile_type_combobox.getSelectionModel().select(0); // default
																// select

		// initial speed - ToggleGroup
		initial_speed_slow.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_medium.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_fast.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_fast.setSelected(true);

		gp.addRow(0, projectile_type_label, projectile_type_combobox);
		gp.addRow(1, mass_label, mass_textField);
		gp.addRow(2, angle_label, angle_textField, angle_slider);
		GridPane.setConstraints(angle_slider, 2, 2, 3, 1);

		gp.addRow(3, initial_speed_label, intitial_speed_textField, initial_speed_slow, initial_speed_medium,
				initial_speed_fast);
		gp.addRow(4, range_label, range_textField);
		gp.addRow(5, height_label, height_textField);
		gp.addRow(6, time_label, time_textField);
		gp.addRow(7, fire_button, erase_button);
		// fire_button.getStyleClass().add("fire");

		// Initial Speed ToggleGroup
		// use the .setUserData command of the radio button to store speeds
		initial_speed_slow.setUserData("10");
		initial_speed_medium.setUserData("55");
		initial_speed_fast.setUserData("100");

		// Prevent the following TextFields from being editable: angle,initial
		// speed, range, height, time
		angle_textField.setEditable(false);
		intitial_speed_textField.setEditable(false);
		range_textField.setEditable(false);
		height_textField.setEditable(false);
		time_textField.setEditable(false);

		// Layout controls as per the diagram, feel free to improve the UI.
		// How many rows and columns do you want - work this out on paper first
		// My version has 7 rows, you can look at the JavaFX API to see how to
		// get controls to span more than one column

		// Method call (not declaration!) to initialize the controls based on
		// the projectile type.

		// Listener for angle Slider to set angle TextTield and the angle
		// variable
		angle_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			angle = (double) newValue;
			angle_textField.setText(newValue.toString());
			fire(); //update calculation with new angle
		});

		// prevent user from text input into mass field
		mass_textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				mass_textField.setText(newValue.replaceAll("[^\\d]", ""));
			}
			mass = Double.parseDouble(newValue);
			fire(); //update calculation with new mass
		});

		// Listener for inital_speed ToggleGroup to set initital_speed TextField
		initial_speed_toggleGroup.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
			intitial_speed_textField.setText((String) new_toggle.getUserData());
//			initial_speed = Double.parseDouble(intitial_speed_textField.getText());
			fire(); //update calculation with new initial speed
		});

		// Listener to call the fire() method when the fire button is pressed
		fire_button.setOnAction((event) -> {
			fire();
		});

		// Listener to initialize control values if the projectile type is
		// changed
		projectile_type_combobox.setOnAction((event) -> {
			initalizeControlValues();
		});

		// Listener to initialize control values if the erase button is pressed
		erase_button.setOnAction((event) -> {
			initalizeControlValues();
		});
	}

	// Overridden start method
	public void start(Stage primaryStage) {
		// set a title on the window, set a scene, size, and show the window
		primaryStage.setTitle("PCT - Projectile Calculation Tool");
		Scene sc = new Scene(gp, 500, 300);
		sc.getStylesheets().add("style.css");
		primaryStage.setScene(sc);
		primaryStage.show();
		initalizeControlValues();
	}

	// Overridden stop method add functionality to this if you wish.
	public void stop() {

	}

	// Entry point to our program
	public static void main(String[] args) {
		launch(args);
	}

	// Method to harvest values from controls, perform calculation and display
	// the results
	private void fire() {
		// capture the values from the text fields outputting number errors
		// where relevant
				
		// don't forget to convert your angle input to radians for use with
		// Math.sin()

		// calculate the range of the projectile

		// calculate the flight time of the projectile

		// calculate the max height of the projectile

		// display the results in the relevant TextFields
	}

	// Method to initalize the controls based on the selection of the projectile
	// type
	private void initalizeControlValues() {
		// if the projectile type is Adult Human then
		if (projectile_type_combobox.getValue() == "Adult Human") {
			// inital the mass to 80kg
			mass = 80;
			mass_textField.setText(df.format(mass));
					// Set slider scale 0 to 90, set slider value to 45 and ticks to 10
			// units
			angle_slider.setMax(90);
			angle_slider.setValue(45);
			angle_slider.setMajorTickUnit(15);
			// initalize the intital speed to fast
			initial_speed_fast.setSelected(true);
			intitial_speed_textField.setText((String) initial_speed_fast.getUserData());

		} else {
			// inital the mass to 400kg
			mass = 400;
			mass_textField.setText(df.format(mass));
			// Set slider scale 0 to 40, set slider value to 20 and ticks to 10
			// units
			angle_slider.setMax(40);
			angle_slider.setValue(20);
			angle_slider.setMajorTickUnit(10);
			// initalize the intial speed to slow
			initial_speed_slow.setSelected(true);
			intitial_speed_textField.setText((String) initial_speed_slow.getUserData());
		}
		// Customisation of slider
		// display ticks etc
		angle_slider.setShowTickMarks(true);
		angle_slider.setShowTickLabels(true);
		angle_slider.setSnapToTicks(true);
		angle_slider.setBlockIncrement(5);
		
		// clear the results fields and variables
		range_textField.clear();
		height_textField.clear();
		time_textField.clear();
		range = 0;
		height = 0;
		time = 0;
	}

	// Variable Declaration
	// Layout
	private GridPane gp = new GridPane();

	// Projectile Type
	private Label projectile_type_label = new Label("Projectile Type");
	private ComboBox<String> projectile_type_combobox = new ComboBox<String>();

	// Mass
	private Label mass_label = new Label("Mass [kgs]");
	private TextField mass_textField = new TextField();
	private double mass = 0;

	// Angle
	private Label angle_label = new Label("Angle [°]");
	private Slider angle_slider = new Slider();

	private TextField angle_textField = new TextField();
	private double angle = 0;
	// Formating the values in the duration box
	DecimalFormat df = new DecimalFormat("0");

	// Initial Speed
	private Label initial_speed_label = new Label("Initial Speed [m/s]");
	private ToggleGroup initial_speed_toggleGroup = new ToggleGroup();
	private RadioButton initial_speed_slow = new RadioButton("slow");
	private RadioButton initial_speed_medium = new RadioButton("medium");
	private RadioButton initial_speed_fast = new RadioButton("fast");
	private TextField intitial_speed_textField = new TextField();
	private double initial_speed;

	// Range
	private Label range_label = new Label("Range [m]");
	private TextField range_textField = new TextField();
	private double range;

	// Height
	private Label height_label = new Label("Max Height [m]");
	private TextField height_textField = new TextField();
	private double height;

	// Time
	private Label time_label = new Label("Time [s]");
	private TextField time_textField = new TextField();
	private double time;

	// Gravity
	private static final double gravitational_accelleration = 9.81; // m/s/s

	// Calculate
	private Button fire_button = new Button("Fire!");
	private Button erase_button = new Button("Reset");
}

// don't break away form the template
// change slider according to value

// inTitial_speed_textField!! can we change this?
//decimalformat? use for mass or for time?
