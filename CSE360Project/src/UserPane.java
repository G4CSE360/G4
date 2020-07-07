//Group 4
//import java.util.*; 

import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.lang.Math;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class UserPane extends BorderPane {
	private Button manualButton, fileButton, deleteButton, displayStats, outputFile, sortAscend, sortDescend,
			distribution;
	// addNewButton;
	private ComboBox<String> displayFeatures;

	private TextArea dataEntry;
	private Label label1;
	public Label medianLbl;
	private Label percentData;
	private VBox stats;
	private HBox statsLayout;
	private VBox percentPane;
	private HBox allDisplays;
	private VBox display;
	private HBox displayH;
	private boolean isHorizOn = false;
	private List<Float> data = new ArrayList<>();
	private int rows, columns;
	public int percent;
	public float avg, distavg;
	public Object median;
	public Object median2;
	private TabPane tabPane = new TabPane();

	FileWriter fw;
	BufferedWriter bw;
	PrintWriter outFile;
	FileChooser fileChooser;
	Stage newStage;
	// Pop Ups:
	Alert messageFrame;
	TextInputDialog displayOptions;

	ChoiceDialog<String> deleteOptions;
	List<String> choices = new ArrayList<>();

	private static DecimalFormat df = new DecimalFormat("0.00");

	// Constructor
	public UserPane() {
		// Step #1: initialize instance variable and set up layout
		// Initialize instance variable and set up layout

		// Buttons:
		manualButton = new Button("add data");
		manualButton.setFont(Font.font("Times New Roman", 15));
		fileButton = new Button("add data from file");
		fileButton.setFont(Font.font("Times New Roman", 15));
		deleteButton = new Button("delete data");
		deleteButton.setFont(Font.font("Times New Roman", 15));
		choices.add("Delete All");
		choices.add("Delete Data Value");
		displayStats = new Button("display statistics");
		displayStats.setFont(Font.font("Times New Roman", 15));
		outputFile = new Button("output file");
		outputFile.setFont(Font.font("Times New Roman", 15));
		sortAscend = new Button("Ascending Order");
		sortAscend.setFont(Font.font("Times New Roman", 15));
		sortDescend = new Button("Descending Order");
		sortDescend.setFont(Font.font("Times New Roman", 15));
		distribution = new Button("Determine % Distribution");
		distribution.setFont(Font.font("Times New Roman", 15));
		// addNewButton = new Button("add new data");

		// addNewButton.setFont(Font.font("Times New Roman", 15));

		// Buttons that require data will be disabled until data is entered to avoid
		// errors
		manualButton.setDisable(false);
		fileButton.setDisable(false);
		deleteButton.setDisable(true);
		displayStats.setDisable(true);
		outputFile.setDisable(true);
		sortAscend.setDisable(true);
		sortDescend.setDisable(true);
		distribution.setDisable(true);
		// addNewButton.setDisable(true);

		// Instructions and Text Area for manual entry:
		dataEntry = new TextArea();
		dataEntry.setText("34 6 8 20 9 28");
		dataEntry.setFont(Font.font("Times New Roman", 15));
		dataEntry.setMaxSize(800, 100);
		label1 = new Label();
		label1.setText("Enter data manually below OR press 'Add data from file' to upload a file");
		label1.setFont(Font.font("Times New Roman", 15));
		label1.setTextFill(Color.web("#000000"));

		// Create the display comboBox,
		// ----
		displayFeatures = new ComboBox<String>();
		displayFeatures.getItems().addAll("display vertically", "display horizontally");
		displayFeatures.setDisable(true);

		// topPane should contain two combo boxes and two buttons
		HBox topPane = new HBox();
		topPane.setSpacing(40);
		topPane.setPadding(new Insets(10, 10, 10, 10));
		topPane.setStyle("-fx-border-color: white");

		topPane.setBackground(
				new Background(new BackgroundFill(Color.rgb(66, 135, 245), CornerRadii.EMPTY, Insets.EMPTY)));
		topPane.getChildren().addAll(manualButton, fileButton, displayFeatures);

		HBox midOptions = new HBox();
		midOptions.setSpacing(40);
		midOptions.setPadding(new Insets(10, 10, 10, 10));
		midOptions.setStyle("-fx-border-color: white");
		midOptions.setBackground(
				new Background(new BackgroundFill(Color.rgb(66, 135, 245), CornerRadii.EMPTY, Insets.EMPTY)));
		midOptions.getChildren().addAll(deleteButton);

		// Pane where all of the data and stats will be displayed
		// stats = new TilePane();
		// stats.setHgap(10); // 150 = 3
		// stats.setVgap(10);
		display = new VBox();
		display.setPadding(new Insets(10, 0, 10, 0));
		display.setSpacing(10);

		displayH = new HBox();
		displayH.setPadding(new Insets(10, 0, 10, 0));
		displayH.setSpacing(20);

		allDisplays = new HBox();
		allDisplays.setPadding(new Insets(10, 0, 10, 0));
		allDisplays.setSpacing(100);
		allDisplays.getChildren().addAll(display, displayH);

		Tab tab1 = new Tab();
		tab1.setText("Data");
		tab1.setContent(allDisplays);

		stats = new VBox();
		stats.setPadding(new Insets(10, 0, 10, 0));
		stats.setSpacing(10);
		percentData = new Label();

		percentPane = new VBox();
		percentPane.setPadding(new Insets(10, 0, 10, 0));
		percentPane.setSpacing(10);
		percentPane.getChildren().addAll(distribution, percentData);

		statsLayout = new HBox();
		statsLayout.setPadding(new Insets(10, 0, 10, 0));
		statsLayout.setSpacing(50);
		statsLayout.getChildren().addAll(stats, percentPane);

		Tab tab2 = new Tab();
		tab2.setText("Stats");
		tab2.setContent(statsLayout);

		tabPane.getSelectionModel().select(0);
		tabPane.setMinHeight(300);

		tabPane.getTabs().addAll(tab1, tab2);
		VBox midPane = new VBox();
		midPane.setSpacing(10);
		midPane.setPadding(new Insets(10, 10, 10, 10));
		midPane.setStyle("-fx-border-color: white");
		midPane.setBackground(
				new Background(new BackgroundFill(Color.rgb(199, 232, 255), CornerRadii.EMPTY, Insets.EMPTY)));
		midPane.getChildren().addAll(label1, dataEntry, midOptions, tabPane);

		HBox bottomPane = new HBox();
		bottomPane.setSpacing(100);
		bottomPane.setPadding(new Insets(10, 100, 10, 10));
		bottomPane.setStyle("-fx-border-color: white");
		bottomPane.setBackground(
				new Background(new BackgroundFill(Color.rgb(66, 135, 245), CornerRadii.EMPTY, Insets.EMPTY)));
		bottomPane.getChildren().addAll(sortAscend, sortDescend, outputFile);

		this.setCenter(midPane);
		this.setTop(topPane);
		this.setBottom(bottomPane);
		manualButton.setOnAction(new ButtonHandler());
		fileButton.setOnAction(new ButtonHandler());
		deleteButton.setOnAction(new ButtonHandler());
		displayStats.setOnAction(new ButtonHandler());
		outputFile.setOnAction(new ButtonHandler());
		sortDescend.setOnAction(new ButtonHandler());
		sortAscend.setOnAction(new ButtonHandler());
		distribution.setOnAction(new ButtonHandler());
		// addNewButton.setOnAction(new ButtonHandler());
		displayFeatures.setOnAction(new DisplayHandler());

	}

	// Step #2(B)- A handler class used to handle events from Undo & Erase buttons
	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Object source = event.getSource();
			if (source == manualButton) { // user has inputed data manually
				String[] dataString = dataEntry.getText().split(" ");
				avg = 0;
				for (int i = 0; i < dataString.length; i++) {
					if (!dataString[i].matches("[-9.0-9.0]+")) {

						messageFrame = new Alert(AlertType.ERROR);
						messageFrame.setTitle("Data Entry Error");
						messageFrame.setHeaderText("An error has occured.");
						messageFrame.setContentText("Invalid data entry");

						messageFrame.showAndWait();
						return;
					}

				}

				for (int j = 0; j < dataString.length; j++) {
					data.add(j, Float.parseFloat(dataString[j]));
				}

				// Now that there is data, the following options are available thus enabled
				// addNewButton.setDisable(false);
				deleteButton.setDisable(false);
				displayStats.setDisable(false);
				outputFile.setDisable(false);
				displayFeatures.setDisable(false);
				sortAscend.setDisable(false);
				sortDescend.setDisable(false);
				distribution.setDisable(false);
				defaultRowCol();
				displayData();
				displayStats();

			} else if (source == fileButton)

			{
				fileChooser = new FileChooser();

				fileChooser.setTitle("Import Data File");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
				File selectedFile = fileChooser.showOpenDialog(newStage);
				readFileHandler(selectedFile);
			}

			else if (source == deleteButton) {
				deleteOptions = new ChoiceDialog<>("Delete options", choices);

				deleteOptions.setTitle("Deletion Options");
				deleteOptions.setHeaderText("What would you like to delete?");
				deleteOptions.setContentText("Please choose an option");
				avg = 0;
				Optional<String> result = deleteOptions.showAndWait();
				if (result.isPresent()) {
					if (result.get().equals("Delete All")) {
						data.removeAll(data);

						// addNewButton.setDisable(true);
						deleteButton.setDisable(true);
						displayStats.setDisable(true);
						outputFile.setDisable(true);
						displayFeatures.setDisable(true);
						sortAscend.setDisable(true);
						sortDescend.setDisable(true);
						distribution.setDisable(true);
						displayData();
						displayStats();

					} else {
						if (findValuetoDelete()) {
							messageFrame = new Alert(AlertType.INFORMATION);
							messageFrame.setTitle("Message");
							messageFrame.setHeaderText(null);
							messageFrame.setContentText("Data successfully deleted!");

							messageFrame.showAndWait();
							displayData();
							displayStats();
							return;
						}else {
							messageFrame = new Alert(AlertType.WARNING);
							messageFrame.setTitle("Warning");
							messageFrame.setHeaderText(null);
							messageFrame.setContentText("Data was not found, could not delete!");

							messageFrame.showAndWait();
							displayData();
							displayStats();
							return;
						}
					}

				}

			}

			else if (source == outputFile) {
				fileChooser = new FileChooser();
				fileChooser.setTitle("Save Data");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
				File newFile = fileChooser.showSaveDialog(newStage);
				if (newFile != null) {
					writeFileHandler(newFile);
				}
			} else if (source == sortAscend) {
				Collections.sort(data);
				displayData();
			} else if (source == sortDescend) {
				Collections.reverse(data);
				displayData();
			} else if (source == distribution) {
				displayOptions = new TextInputDialog("Percent Distribution");
				displayOptions.setTitle("Percent Distribution mean/average");
				displayOptions.setHeaderText("Input the desired percentage");
				displayOptions.setContentText("Please enter a number: ");
				
				Optional<String> result = displayOptions.showAndWait();
				if(result.isPresent()) {
					//percentage distribution function HERE, the variable "result" holds the percentage
					//value that the user would like to use
					percent = Integer.valueOf(result.get());
					Object[] avgobject = data.toArray();
					Arrays.sort(avgobject);
					int count = 0;
					distavg = 0;
					int size = (data.size()*percent)/100;
					int datasize = data.size();
					while (size < datasize) {
						distavg += (float) avgobject[size];
						count++;
						size++;
					} 
					double distribavg;
					distribavg = (distavg*100)/(count*100);
					Label distlabel = new Label("Average of numbers above " + percent + " percent = " + df.format(distribavg));
					distlabel.setFont(Font.font("Times New Roman", 25));

					percentPane.getChildren().add(distlabel);
				displayStats();
				}
			}

		}
	}

	private class DisplayHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			int dSelection = displayFeatures.getSelectionModel().getSelectedIndex();

			if (dSelection == 0) { // for vertical display (Adjust number of columns)
				isHorizOn = false;
				displayOptions = new TextInputDialog("Number of Columns");
				displayOptions.setTitle("Vertical Adjustment");
				displayOptions.setHeaderText("Input the desired number of columns");
				displayOptions.setContentText("Please enter a number: ");

				Optional<String> result = displayOptions.showAndWait();
				if (result.isPresent()) {
					Integer Cvalue = Integer.valueOf(result.get());
					columns = Cvalue;

					if (data.size() % columns == 0) {
						rows = data.size() / columns;
					} else {
						rows = (int) Math.ceil(data.size() / columns) + 1;
					}

					displayData();
					displayStats();
					return;
				}

			} else { // for horizontal display (Adjust number of rows)
				isHorizOn = true;
				displayOptions = new TextInputDialog("Number of Rows");
				displayOptions.setTitle("Horizontal Adjustment");
				displayOptions.setHeaderText("Input the desired number of rows");
				displayOptions.setContentText("Please enter a number: ");

				Optional<String> result = displayOptions.showAndWait();
				if (result.isPresent()) {
					Integer Rvalue = Integer.valueOf(result.get());
					rows = Rvalue;
					if (data.size() % rows == 0) {
						columns = data.size() / rows;
					} else {
						columns = (int) Math.ceil(data.size() / rows) + 1;
					}
					displayData();
					displayStats();
					return;
				}
			}

		}

	}

	public boolean findValuetoDelete() {
		float dValue = 0;
		TextInputDialog deleteValue = new TextInputDialog("value");
		deleteValue.setTitle("Delete a value");
		deleteValue.setHeaderText("Value entered here will be deleted from data");
		deleteValue.setContentText("Please enter a number: ");

		Optional<String> result = deleteValue.showAndWait();
		if (result.isPresent()) {
			dValue = Float.valueOf(result.get());
		}

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) == dValue) {
				data.remove(i);
				return true;
			}
		}
		return false;
	}

	public void displayStats() {
		stats.getChildren().removeAll(stats.getChildren());
		Label title = new Label("Statistics:");
		title.setStyle("-fx-border-color: white");
		title.setFont(Font.font("Times New Roman", 25));
		stats.getChildren().add(title);

		Label average;
		// Label median;
		Object[] objects = data.toArray();
		Arrays.sort(objects);
		avg = 0;
		median = 0;
		median2 = 0;
		for (int i = 0; i < data.size(); i++) {

			avg += (float) data.get(i);

			if (data.size() % 2 == 0) {
				median = objects[data.size() / 2];
				median2 = objects[(data.size() / 2) - 1];
				medianLbl = new Label("Median =" + median + "," + median2);
			} else {
				median = objects[data.size() / 2];
				medianLbl = new Label("Median =" + median);
			}

		}

		float averages = (float) avg / data.size();
		average = new Label("Average = " + df.format(averages));
		average.setStyle("-fx-border-color: white");
		average.setFont(Font.font("Times New Roman", 25));
		medianLbl.setStyle("-fx-border-color: white");
		medianLbl.setFont(Font.font("Times New Roman", 25));
		// medianLbl = new Label("median =" + median + "," + median2);
		stats.getChildren().addAll(average, medianLbl);
		frequency();
	}

	public void displayData() {
		display.getChildren().removeAll(display.getChildren());
		displayH.getChildren().removeAll(displayH.getChildren());

		if (!isHorizOn) {

			String currentText = "";
			Label current;
			int index = 0;

			for (int j = 0; j < rows; j++) {
				currentText = "";
				for (int k = 0; k < columns; k++) {

					if (index >= data.size()) {
						current = new Label(currentText);
						current.setFont(Font.font("Times New Roman", 20));
						display.getChildren().addAll(current);
						return;
					}

					currentText += data.get(index) + "\t\t";
					index++;
				}

				current = new Label(currentText);
				current.setFont(Font.font("Times New Roman", 20));
				display.getChildren().addAll(current);
			}
		} else {
			String currentText = "";
			Label current;
			int index = 0;

			for (int j = 0; j < columns; j++) {
				currentText = "";
				for (int k = 0; k < rows; k++) {

					if (index >= data.size()) {
						current = new Label(currentText);
						current.setFont(Font.font("Times New Roman", 20));
						displayH.getChildren().addAll(current);
						return;
					}

					currentText += data.get(index) + "\n";
					index++;
				}

				current = new Label(currentText);
				current.setFont(Font.font("Times New Roman", 20));
				displayH.getChildren().addAll(current);
			}
		}

	}

	public void frequency() {
		List<Float> temp = new ArrayList<>();
		for (int m = 0; m < data.size(); m++) {
			temp.add(m, data.get(m));
		}
		float first = findMostFreq(temp);

		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) == first) {
				temp.remove(i);
				i--;
			}
		}

		float second = findMostFreq(temp);
		for (int j = 0; j < temp.size(); j++) {
			if (temp.get(j) == second) {
				temp.remove(j);
			}
		}

		float third = findMostFreq(temp);
		for (int k = 0; k < temp.size(); k++) {
			if (temp.get(k) == third) {
				temp.remove(k);
			}
		}

		Label top3 = new Label("TOP 3 REOCCURING VALUES\n" + "1. " + first + "\n2. " + second + "\n3. " + third);
		top3.setStyle("-fx-border-color: white");
		top3.setFont(Font.font("Times New Roman", 25));
		stats.getChildren().addAll(top3);
	}

	public float findMostFreq(List<Float> list) {
		int freq = 0;
		int newFreq = 0;
		float target = 0;
		for (int i = 0; i < list.size(); i++) {
			newFreq = Collections.frequency(list, list.get(i));

			if (newFreq > freq) {
				freq = newFreq;
				target = list.get(i);
			}
		}

		return target;

	}

	public void defaultRowCol() {
		columns = (int) Math.floor(Math.sqrt(data.size()));
		rows = (int) Math.ceil(data.size() / columns);
	}

	public void writeFileHandler(File file) {
		try {
			PrintWriter writer = new PrintWriter(file);
			for (int i = 0; i < data.size(); i++) {
				writer.println(data.get(i));
			}
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(UserPane.class.getName());
		}
	}

	public void readFileHandler(File file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader inFile = new BufferedReader(fr);
			String line;

			while ((line = inFile.readLine()) != null) {
				// values that are non-numbers/symbols will be filtered out here, and will not
				// be added to the data array
				if (line.matches("[-9.0-9.0]+")) {
					data.add(Float.parseFloat(line));

				}
			}
			inFile.close();
			defaultRowCol();
			displayData();
			displayStats();

			// addNewButton.setDisable(false);
			deleteButton.setDisable(false);
			displayStats.setDisable(false);
			outputFile.setDisable(false);
			displayFeatures.setDisable(false);
			sortAscend.setDisable(false);
			sortDescend.setDisable(false);
			distribution.setDisable(false);

		} catch (FileNotFoundException ex) {
			messageFrame = new Alert(AlertType.ERROR);
			messageFrame.setTitle("Data Entry Error");
			messageFrame.setHeaderText("An error has occured.");
			messageFrame.setContentText("The file " + file + " was not found, please enter valid file name");

			messageFrame.showAndWait();
		} catch (IOException ex2) {
			System.out.println("exception");

			messageFrame = new Alert(AlertType.ERROR);
			messageFrame.setTitle("Data Entry Error");
			messageFrame.setHeaderText("An error has occured.");
			messageFrame.setContentText("IOExcpetion has occured, please try again");

			messageFrame.showAndWait();
		}
		// ************************************************************************************/

	}

}// end class UserPane