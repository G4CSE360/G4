
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
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;

public class UserPane extends BorderPane 
{
	private Button manualButton, fileButton, deleteButton, displayStats, outputFile;
	//addNewButton;
	private ComboBox<String> displayFeatures;

	private TextArea dataEntry;
	private Label label1;
	public Label medianLbl;
	private TilePane stats;
	private List<Float> data = new ArrayList<>();
	private int rows, columns;
	public float avg;
	public float median;
	public float median2;
	

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
	public UserPane() 
	{
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
		//addNewButton = new Button("add new data");

		//addNewButton.setFont(Font.font("Times New Roman", 15));

		// Buttons that require data will be disabled until data is entered to avoid
		// errors
		manualButton.setDisable(false);
		fileButton.setDisable(false);
		deleteButton.setDisable(true);
		displayStats.setDisable(true);
		outputFile.setDisable(true);
		//addNewButton.setDisable(true);

		// Instructions and Text Area for manual entry:
		dataEntry = new TextArea();
		dataEntry.setText("Ex: 34 6 8 20 9 28");
		dataEntry.setFont(Font.font("Times New Roman", 15));
		label1 = new Label();
		label1.setText("Enter data manually below OR press 'Add data from file' to upload a file");
		label1.setFont(Font.font("Times New Roman", 15));
		label1.setTextFill(Color.web("#000000"));
		

		// Create the display comboBox,
		// ----
		displayFeatures = new ComboBox<String>();
		displayFeatures.getItems().addAll("display vertically", "display horizontally");

		// topPane should contain two combo boxes and two buttons
		HBox topPane = new HBox();
		topPane.setSpacing(40);
		topPane.setPadding(new Insets(10, 10, 10, 10));
		topPane.setStyle("-fx-border-color: white");

		topPane.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250), CornerRadii.EMPTY, Insets.EMPTY)));
		topPane.getChildren().addAll(manualButton, fileButton, displayFeatures);

		HBox midOptions = new HBox();
		midOptions.setSpacing(40);
		midOptions.setPadding(new Insets(10, 10, 10, 10));
		midOptions.setStyle("-fx-border-color: white");
		midOptions.setBackground(
				new Background(new BackgroundFill(Color.rgb(250, 250, 250), CornerRadii.EMPTY, Insets.EMPTY)));
		midOptions.getChildren().addAll(deleteButton);

		// Pane where all of the data and stats will be displayed
		stats = new TilePane();
		stats.setHgap(10);
		stats.setVgap(10);
		stats.setPadding(new Insets(0, 10, 0, 10));

		VBox midPane = new VBox();
		midPane.setSpacing(10);
		midPane.setPadding(new Insets(10, 10, 10, 10));
		midPane.setStyle("-fx-border-color: white");
		midPane.setBackground(
				new Background(new BackgroundFill(Color.rgb(250, 250, 250), CornerRadii.EMPTY, Insets.EMPTY)));
		midPane.getChildren().addAll(label1, dataEntry, midOptions, stats);

		HBox bottomPane = new HBox();
		bottomPane.setSpacing(100);
		bottomPane.setPadding(new Insets(10, 100, 10, 10));
		bottomPane.setStyle("-fx-border-color: white");
		bottomPane.setBackground(
				new Background(new BackgroundFill(Color.rgb(250, 250, 250), CornerRadii.EMPTY, Insets.EMPTY)));
		bottomPane.getChildren().addAll(displayStats, outputFile);

		this.setCenter(midPane);
		this.setTop(topPane);
		this.setBottom(bottomPane);
		manualButton.setOnAction(new ButtonHandler());
		fileButton.setOnAction(new ButtonHandler());
		deleteButton.setOnAction(new ButtonHandler());
		displayStats.setOnAction(new ButtonHandler());
		outputFile.setOnAction(new ButtonHandler());
		//addNewButton.setOnAction(new ButtonHandler());
		displayFeatures.setOnAction(new DisplayHandler());

	}

	// Step #2(B)- A handler class used to handle events from Undo & Erase buttons
	private class ButtonHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent event) {
			Object source = event.getSource();
			if (source == manualButton) 
			{ // user has inputed data manually
				String[] dataString = dataEntry.getText().split(" ");
				avg = 0;
				for (int i = 0; i < dataString.length; i++) 
				{
					if (!dataString[i].matches("[-9.0-9.0]+")) 
					{
						
						messageFrame = new Alert(AlertType.ERROR);
						messageFrame.setTitle("Data Entry Error");
						messageFrame.setHeaderText("An error has occured.");
						messageFrame.setContentText("Invalid data entry");

						messageFrame.showAndWait();
						return;
					}

				}

				for (int j = 0; j < dataString.length; j++)
				{
					data.add(j, Float.parseFloat(dataString[j]));
				}

				// Now that there is data, the following options are available thus enabled
				//addNewButton.setDisable(false);
				deleteButton.setDisable(false);
				displayStats.setDisable(false);
				outputFile.setDisable(false);

				displayData();

			} 
			else if (source == fileButton)

			{
				fileChooser = new FileChooser();

				fileChooser.setTitle("Import Data File");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
				File selectedFile = fileChooser.showOpenDialog(newStage);
				readFileHandler(selectedFile);
			}

			else if (source == deleteButton) 
			{
				deleteOptions = new ChoiceDialog<>("Delete options", choices);

				deleteOptions.setTitle("Deletion Options");
				deleteOptions.setHeaderText("What would you like to delete?");
				deleteOptions.setContentText("Please choose an option");
				avg = 0;
				Optional<String> result = deleteOptions.showAndWait();
				if (result.isPresent())
				{
					if (result.get().equals("Delete All")) 
					{
						data.removeAll(data);

						//addNewButton.setDisable(true);
						deleteButton.setDisable(true);
						displayStats.setDisable(true);
						outputFile.setDisable(true);
						displayData();

					} 
					else 
					{
						if (findValuetoDelete()) 
						{
							messageFrame = new Alert(AlertType.INFORMATION);
							messageFrame.setTitle("Message");
							messageFrame.setHeaderText(null);
							messageFrame.setContentText("Data successfully deleted!");

							messageFrame.showAndWait();
							displayData();
							return;
						}
						else 
						{
							messageFrame = new Alert(AlertType.WARNING);
							messageFrame.setTitle("Warning");
							messageFrame.setHeaderText(null);
							messageFrame.setContentText("Data was not found, could not delete!");

							messageFrame.showAndWait();
							displayData();
							return;
						}
					}

				}

			}

			else if (source == outputFile) 
			{
				fileChooser = new FileChooser();
				fileChooser.setTitle("Save Data");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
				File newFile = fileChooser.showSaveDialog(newStage);
				if (newFile != null) 
				{
					writeFileHandler(newFile);
				}
			} 
			/*else if (source == addNewButton) 
			{

				TextInputDialog addNew = new TextInputDialog("value");
				addNew.setTitle("Delete a value");
				addNew.setHeaderText("Value entered here will be deleted from data");
				addNew.setContentText("Please enter a number: ");

				Optional<String> result = addNew.showAndWait();
				if (result.isPresent()) 
				{
					try 
					{
						Float newValue = Float.valueOf(result.get());
						data.add(newValue);
						displayData();
					} catch (NumberFormatException ex) 
					{
						messageFrame = new Alert(AlertType.ERROR);
						messageFrame.setTitle("Data Entry Error");
						messageFrame.setHeaderText("An error has occured.");
						messageFrame.setContentText("Not a valid data value!");

						messageFrame.showAndWait();
					}

				}

			}*/

		}
	}

	private class DisplayHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent event) 
		{
			int dSelection = displayFeatures.getSelectionModel().getSelectedIndex();

			if (dSelection == 0) { // for vertical display (Adjust number of columns)
				displayOptions = new TextInputDialog("Number of Columns");
				displayOptions.setTitle("Vertical Adjustment");
				displayOptions.setHeaderText("Input the desired number of columns");
				displayOptions.setContentText("Please enter a number: ");

				Optional<String> result = displayOptions.showAndWait();
				if (result.isPresent()) 
				{
					Integer Cvalue = Integer.valueOf(result.get());
					columns = Cvalue;
					displayData();
					return;
				}

			} 
			else 
			{ // for horizontal display (Adjust number of rows)
				displayOptions = new TextInputDialog("Number of Rows");
				displayOptions.setTitle("Horizontal Adjustment");
				displayOptions.setHeaderText("Input the desired number of rows");
				displayOptions.setContentText("Please enter a number: ");

				Optional<String> result = displayOptions.showAndWait();
				if (result.isPresent()) {
					Integer Rvalue = Integer.valueOf(result.get());
					rows = Rvalue;
					displayData();
					return;
				}
			}

		}

	}

	public boolean findValuetoDelete() 
	{
		float dValue = 0;
		TextInputDialog deleteValue = new TextInputDialog("value");
		deleteValue.setTitle("Delete a value");
		deleteValue.setHeaderText("Value entered here will be deleted from data");
		deleteValue.setContentText("Please enter a number: ");

		Optional<String> result = deleteValue.showAndWait();
		if (result.isPresent()) 
		{
			dValue = Float.valueOf(result.get());
		}

		for (int i = 0; i < data.size(); i++)
		{
			if (data.get(i) == dValue)
			{
				data.remove(i);
				return true;
			}
		}
		return false;
	}
	


	public void displayData() 
	{
		stats.getChildren().removeAll(stats.getChildren());
		stats.setPrefColumns(columns);
		stats.setPrefRows(rows);
		Label current;
		Label average;
		//Label median;

		for (int i = 0; i < data.size(); i++)
		{
			current = new Label("" + data.get(i));
			stats.getChildren().add(current);
			avg += (float) data.get(i);
			
			if(data.size() %2 == 0)
			{	
				median = data.get(data.size()/2);
				median2 = data.get((data.size()/2) -1);	
				medianLbl = new Label("median =" + median + "," + median2);
			}
			else
			{
				median = data.get(data.size()/2);
				medianLbl = new Label("median =" + median);
			}
			
		}
		float averages = (float) avg / data.size();
		average = new Label("mean = " + df.format(averages));
		//medianLbl = new Label("median =" + median + "," + median2);
		stats.getChildren().addAll(average,medianLbl);
	}

	public void writeFileHandler(File file) 
	{
		try 
		{
			PrintWriter writer = new PrintWriter(file);
			for (int i = 0; i < data.size(); i++) 
			{
				writer.println(data.get(i));
			}
			writer.close();
		} 
		catch (IOException ex)
		{
			Logger.getLogger(UserPane.class.getName());
		}
	}

	public void readFileHandler(File file) 
	{
		try {
			FileReader fr = new FileReader(file);
			BufferedReader inFile = new BufferedReader(fr);
			String line;

			while ((line = inFile.readLine()) != null) 
			{
				// values that are non-numbers/symbols will be filtered out here, and will not
				// be added to the data array
				if (line.matches("[0.0-9.0]+")) 
				{
					data.add(Float.parseFloat(line));

				}
			}
			inFile.close();
			displayData();

			//addNewButton.setDisable(false);
			deleteButton.setDisable(false);
			displayStats.setDisable(false);
			outputFile.setDisable(false);

		} catch (FileNotFoundException ex) 
		{
			messageFrame = new Alert(AlertType.ERROR);
			messageFrame.setTitle("Data Entry Error");
			messageFrame.setHeaderText("An error has occured.");
			messageFrame.setContentText("The file " + file + " was not found, please enter valid file name");

			messageFrame.showAndWait();
		} catch (IOException ex2)
		{
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