
/**
 * This application allows the user to calculate range, max height and flight time for a projectile 
 * following the entering of mass (currently not used), angle of launch, and initial speed.
 * There is error checking to ensure that suitable values are entered. 
 */

import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// class definition
public class Projectile extends Application {

	// init method, setting default values
	public void init() {
		// Planet
		planet_combobox.getItems().addAll("Earth", "Moon", "Mars");
		planet_combobox.getSelectionModel().select(0); // default select

		// Projectile Type - ComboBox
		projectile_type_combobox.getItems().addAll("Adult Human", "Piano");
		projectile_type_combobox.getSelectionModel().select(0); // default select

		// Initial Speed ToggleGroup
		initial_speed_slow.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_medium.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_fast.setToggleGroup(initial_speed_toggleGroup);
		initial_speed_fast.setSelected(true);
		// use the .setUserData command of the radio button to store speeds
		initial_speed_slow.setUserData("10");
		initial_speed_medium.setUserData("55");
		initial_speed_fast.setUserData("100");

		// put Radio Buttons in HBox
		HBox hb = new HBox();
		hb.setSpacing(5);
		hb.getChildren().addAll(initial_speed_slow, initial_speed_medium, initial_speed_fast);

		// set up GridPane Layout
		gp.setHgap(5); // horizontal gap in pixels
		gp.setVgap(5); // vertical gap in pixels
		gp.setPadding(new Insets(5, 5, 5, 5)); // margins around the whole GridPane

		// add Rows
		gp.addRow(0, planet_label, planet_combobox);
		gp.addRow(1, projectile_type_label, projectile_type_combobox);
		gp.addRow(2, mass_label, mass_textField, mass_exception_label);
		gp.addRow(3, angle_label, angle_slider, angle_textField);
		gp.addRow(4, initial_speed_label, hb, intitial_speed_textField);
		gp.addRow(5, initial_height_label, initial_height_slider, initial_height_textField);
		gp.addRow(6, results_label, fire_button);
		gp.addRow(7, range_label, range_textField);
		gp.addRow(8, height_label, height_textField);
		gp.addRow(9, time_label, time_textField);
		gp.addRow(10, energy_label, energy_textField, erase_button);

		// set the fire button to expand to fill the available space
		fire_button.setMaxWidth(Integer.MAX_VALUE);
		// set label to span more than 1 column
		GridPane.setConstraints(mass_exception_label, 2, 2, 2, 1);
		// set width of third column controls
		angle_textField.setMaxWidth(50);
		intitial_speed_textField.setMaxWidth(50);
		initial_height_textField.setMaxWidth(50);
		erase_button.setMinWidth(50);
		// set CSS selectors
		mass_exception_label.setId("mass_exception_label");
		results_label.setId("results_label");
		fire_button.setId("fire_button");

		// Prevent the following TextFields from being editable: angle,initial speed, range, height, time
		angle_textField.setEditable(false);
		angle_textField.getStyleClass().add("disabled_field");
		intitial_speed_textField.setEditable(false);
		intitial_speed_textField.getStyleClass().add("disabled_field");
		initial_height_textField.setEditable(false);
		initial_height_textField.getStyleClass().add("disabled_field");
		range_textField.setEditable(false);
		range_textField.getStyleClass().add("disabled_field");
		height_textField.setEditable(false);
		height_textField.getStyleClass().add("disabled_field");
		time_textField.setEditable(false);
		time_textField.getStyleClass().add("disabled_field");
		energy_textField.setEditable(false);
		energy_textField.getStyleClass().add("disabled_field");

		// Method call to initialise the controls based on the projectile type
		initalizeControlValues();

		// Listener for angle Slider to set angle TextTield and the angle variable
		angle_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			angle_textField.setText(newValue.toString());
			fire(); // update calculation with new angle
		});

		initial_height_slider.valueProperty().addListener((observable, oldValue, newValue)-> {
			initial_height_textField.setText(newValue.toString());
			fire();
		});

		mass_textField.textProperty().addListener((observable, oldValue, newValue) -> {
			fire(); // update calculation with new mass
		});

		// Listener for inital_speed ToggleGroup to set initital_speed TextField
		initial_speed_toggleGroup.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
			intitial_speed_textField.setText((String) new_toggle.getUserData());
			fire(); // update calculation with new initial speed
		});

		// Listener to call the fire() method when the fire button is pressed
		fire_button.setOnAction((event) -> {
			fire();
		});

		// Listener to initialise control values if the planet type is changed
		planet_combobox.setOnAction((event) -> {
			initalizeControlValues();
		});

		// Listener to initialise control values if the projectile type is changed
		projectile_type_combobox.setOnAction((event) -> {
			initalizeControlValues();
		});

		// Listener to initialise control values if the erase button is pressed
		erase_button.setOnAction((event) -> {
			initalizeControlValues();
		});
	}

	// Overridden start method
	public void start(Stage primaryStage) {
		// set a title on the window, set a scene, size, and show the window
		primaryStage.setTitle("PCT - Projectile Calculation Tool");
		Scene sc = new Scene(gp, 400, 400);
		sc.getStylesheets().add("style.css");
		primaryStage.setScene(sc);
		primaryStage.show();
	}

	// Overridden stop method add functionality to this if you wish.
	public void stop() {

	}

	// Entry point to our program
	public static void main(String[] args) {
		launch(args);
	}

	// Method to harvest values from controls, perform calculation and display the results
	private void fire() {
		// get value from mass field and display error if it's not a number
		try {
			mass = Double.parseDouble(mass_textField.getText());
			mass_exception_label.setText("");
		} catch (NumberFormatException e) {
			mass_exception_label.setText("No Number");
		}

		// set gravitational acceleration depending on planet
		double gravitation;

		switch (planet_combobox.getValue()) {
		case "Earth":
			gravitation = gravitational_accelleration;
			break;
		case "Moon":
			gravitation = gravitational_accelleration_moon;
			break;
		case "Mars":
			gravitation = gravitational_accelleration_mars;
			break;
		default:
			gravitation = gravitational_accelleration;
			break;
		}

		// get values of input variables from text fields
		initial_speed = Double.parseDouble(intitial_speed_textField.getText());
		angle = Double.parseDouble(angle_textField.getText());
		double angle_rad = Math.toRadians(angle);
		initial_height = Double.parseDouble(initial_height_textField.getText());

		// calculate the range of the projectile
		range = initial_speed * initial_speed * Math.sin(2 * angle_rad) / gravitation;

		// calculate the max height of the projectile
		height = initial_speed * initial_speed * Math.sin(angle_rad) * Math.sin(angle_rad) / (2 * gravitation);

		// calculate the flight time of the projectile
		time = 2 * initial_speed * Math.sin(angle_rad) / gravitation;

		// calculate the energy
		energy = (mass / 2) * (initial_speed * initial_speed) // kinetic energy
				+ mass * gravitation * initial_height; // potential energy
		energy = energy / 1000;

		// display the results in the relevant TextFields
		range_textField.setText(df.format(range));
		height_textField.setText(df.format(height));
		time_textField.setText(df.format(time));
		energy_textField.setText(df.format(energy));
	}

	// Method to initalize the controls based on the selection of the projectile type
	private void initalizeControlValues() {
		// if the projectile type is Adult Human then
		if (projectile_type_combobox.getValue() == "Adult Human") {
			// initialise the mass to 80kg
			mass_textField.setText("80");
			// Set angle slider scale 0 to 90, set slider value to 45 and ticks to 10 units
			angle_slider.setMax(90);
			angle_slider.setValue(45);
			angle_slider.setMajorTickUnit(15);
			// set angle TextField to slider value
			angle_textField.setText(df.format(angle_slider.getValue()));
			// initialise the initial speed to fast
			initial_speed_fast.setSelected(true);
			intitial_speed_textField.setText((String) initial_speed_fast.getUserData());

		} else {
			// initialise the mass to 400kg
			mass_textField.setText("400");
			// Set angle slider scale 0 to 40, set slider value to 20 and ticks to 10 units
			angle_slider.setMax(40);
			angle_slider.setValue(20);
			angle_slider.setMajorTickUnit(10);
			// set angle TextField to slider value
			angle_textField.setText(df.format(angle_slider.getValue()));
			// initialise the initial speed to slow
			initial_speed_slow.setSelected(true);
			intitial_speed_textField.setText((String) initial_speed_slow.getUserData());
		}

		// initial height slider and initialise the height to 0
		initial_height_slider.setMax(500);
		initial_height_slider.setValue(0);
		initial_height_slider.setMajorTickUnit(100);
		// set angle TextField to slider value
		initial_height_textField.setText(df.format(initial_height_slider.getValue()));

		// Customisation of sliders
		// display ticks etc
		angle_slider.setShowTickMarks(true);
		angle_slider.setShowTickLabels(true);
		angle_slider.setBlockIncrement(5);

		initial_height_slider.setShowTickMarks(true);
		initial_height_slider.setShowTickLabels(true);
		initial_height_slider.setBlockIncrement(5);

		// clear the results fields and variables
		range_textField.clear();
		height_textField.clear();
		time_textField.clear();
		energy_textField.clear();
		range = 0;
		height = 0;
		time = 0;
		energy = 0;
	}

	// Variable Declaration
	// Layout
	private GridPane gp = new GridPane();

	// Planet
	private Label planet_label = new Label("Planet");
	private ComboBox<String> planet_combobox = new ComboBox<String>();

	// Projectile Type
	private Label projectile_type_label = new Label("Projectile Type");
	private ComboBox<String> projectile_type_combobox = new ComboBox<String>();

	// Mass
	private Label mass_label = new Label("Mass [kgs]");
	private TextField mass_textField = new TextField();
	private Label mass_exception_label = new Label();
	private double mass = 0;

	// Angle
	private Label angle_label = new Label("Angle [°]");
	private Slider angle_slider = new Slider();
	private TextField angle_textField = new TextField();
	private double angle = 0;
	// Formating the values in the duration box
	DecimalFormat df = new DecimalFormat("#.##");

	// Initial Speed
	private Label initial_speed_label = new Label("Initial Speed [m/s]");
	private ToggleGroup initial_speed_toggleGroup = new ToggleGroup();
	private RadioButton initial_speed_slow = new RadioButton("slow");
	private RadioButton initial_speed_medium = new RadioButton("medium");
	private RadioButton initial_speed_fast = new RadioButton("fast");
	private TextField intitial_speed_textField = new TextField();
	private double initial_speed;

	// Initial Height
	private Label initial_height_label = new Label("Initial Height [m]");
	private Slider initial_height_slider = new Slider();
	private TextField initial_height_textField = new TextField();
	private double initial_height;

	private Label results_label = new Label("Results:");
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

	// Energy
	private Label energy_label = new Label("Energy [kJ]");
	private TextField energy_textField = new TextField();
	private double energy;

	// Gravity
	private static final double gravitational_accelleration = 9.81; // m/s/s
	private static final double gravitational_accelleration_mars = 3.711; // m/s/s
	private static final double gravitational_accelleration_moon = 1.622; // m/s/s

	// Calculate
	private Button fire_button = new Button("FIRE!");
	private Button erase_button = new Button("Erase");
}

// don't break away form the template
// change slider according to value

// inTitial_speed_textField!! can we change this?
// decimalformat? use for mass or for time?
// do I have to cite where I got the formula for energy from?