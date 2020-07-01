


//Group 4
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;

public class UserPane extends BorderPane 
{
	private Button manualButton, fileButton, deleteButton, displayStats, outputFile, addNewButton;
	private ComboBox<String> displayFeatures;
	private Pane canvas;
	private TextArea dataEntry;
	private Label label1;
	private TilePane stats;
	private float[] data;
	private int rows, columns;
	private String filename;
	//Pop Ups:
	Alert messageFrame;
	TextInputDialog displayOptions;
	TextInputDialog fileInput;
	ChoiceDialog<String> deleteOptions;
	List<String> choices = new ArrayList<>();
	
	// Constructor

	public UserPane() 
	{
		// Step #1: initialize instance variable and set up layout
		manualButton = new Button("add data");
		fileButton = new Button("add data from file");
		deleteButton = new Button("delete data");
		displayStats = new Button("display statistics");
		outputFile = new Button("output file");
		addNewButton = new Button("add new data");
	
		
		manualButton.setMinWidth(80.0);
		fileButton.setMinWidth(80.0);
		deleteButton.setMinWidth(80.0);
		displayStats.setMinWidth(150.0);
		outputFile.setMinWidth(150.0);
		
		
		
		manualButton.setDisable(false);
		fileButton.setDisable(false);
		deleteButton.setDisable(true);
		displayStats.setDisable(true);
		outputFile.setDisable(true);
		addNewButton.setDisable(true);
		
		dataEntry = new TextArea();
		dataEntry.setText("Ex: 34 6 8 20 9 28");
		label1 = new Label();
		label1.setText("Enter data manually below OR press 'Add data from file' to upload a file");
		
	
		// Create the display comboBox,
		// ----
		displayFeatures = new ComboBox<String>();
		displayFeatures.getItems().addAll("display vertically", "display horizontally");
		

		// topPane should contain two combo boxes and two buttons
		HBox topPane = new HBox();
		topPane.setSpacing(40);
		topPane.setPadding(new Insets(10, 10, 10, 10));
		topPane.setStyle("-fx-border-color: black");
		topPane.getChildren().addAll(manualButton, fileButton, displayFeatures);
		
		HBox midOptions = new HBox();
		midOptions.setSpacing(40);
		midOptions.setPadding(new Insets(10, 10, 10, 10));
		midOptions.setStyle("-fx-border-color: white");
		midOptions.getChildren().addAll(addNewButton, deleteButton);
		
		//Pane where all of the data and stats will be displayed
		stats = new TilePane();
		stats.setHgap(10);
		stats.setVgap(10);
		stats.setPadding(new Insets(0, 10, 0, 10));
		
		
		VBox midPane = new VBox();
		midPane.setSpacing(10);
		midPane.setPadding(new Insets(10, 10, 10, 10));
		midPane.setStyle("-fx-border-color: black");
		midPane.getChildren().addAll(label1, dataEntry, midOptions, stats);
		
		
		HBox bottomPane = new HBox();
		bottomPane.setSpacing(100);
		bottomPane.setPadding(new Insets(10, 100, 10, 10));
		bottomPane.setStyle("-fx-border-color: black");
		bottomPane.getChildren().addAll(displayStats, outputFile);
		
		

		this.setCenter(midPane);
		this.setTop(topPane);
		this.setBottom(bottomPane);


		manualButton.setOnAction(new ButtonHandler());
		fileButton.setOnAction(new ButtonHandler());
		deleteButton.setOnAction(new ButtonHandler());
		displayStats.setOnAction(new ButtonHandler());
		outputFile.setOnAction(new ButtonHandler());
		
		displayFeatures.setOnAction(new DisplayHandler());
		

	}



	// Step #2(B)- A handler class used to handle events from Undo & Erase buttons
	private class ButtonHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent event) 
		{
			Object source = event.getSource();
			if(source == manualButton) 
			{ //user has inputed data manually
				String[] dataString = dataEntry.getText().split(" ");
				
				for(int i = 0; i < dataString.length; i++) 
				{
					if(!dataString[i].matches("[0-9]+"))
					{
						messageFrame = new Alert(AlertType.ERROR);
						messageFrame.setTitle("Data Entry Error");
						messageFrame.setHeaderText("An error has occured.");
						messageFrame.setContentText("Invalid data entry");
						
						messageFrame.showAndWait();
						return;
					}
				
				}
				data = new float[dataString.length];
				for(int j = 0; j<dataString.length; j++) 
				{
					data[j] = Integer.parseInt(dataString[j]);
				}
				
				//Now that there is data, the following options are available thus enabled
				addNewButton.setDisable(false);
				deleteButton.setDisable(false);
				displayStats.setDisable(false);
				outputFile.setDisable(false);
				
				displayData();
				
			}
			else if(source == fileButton)
			{ //user has opted to upload a file with data
				fileInput = new TextInputDialog(".txt file name");
				fileInput.setTitle("Upload Data");
				fileInput.setHeaderText("Input a file name with your data");
				fileInput.setContentText("Please enter file name");
				
				Optional<String> result = fileInput.showAndWait();
				if(result.isPresent()) {
					filename = result.get();
				}
				
			}
			else if(source == deleteButton) 
			{
				choices.add("Delete All");
				choices.add("Delete Data Value");
				deleteOptions = new ChoiceDialog<>("Delete options", choices);
				
				deleteOptions.setTitle("Deletion Options");
				deleteOptions.setHeaderText("What would you like to delete?");
				deleteOptions.setContentText("Please choose an option");
				
				Optional<String> result = deleteOptions.showAndWait();
				if(result.isPresent()) {
					if(result.get().equals("Delete All")) {
						data = null;
						
						addNewButton.setDisable(true);
						deleteButton.setDisable(true);
						displayStats.setDisable(true);
						outputFile.setDisable(true);
						displayData();
						
					}
					else 
					{
						if(findValuetoDelete()) 
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
			else if(source == displayStats)
			{
				
			}
		
		}

	}
	
	private class DisplayHandler implements EventHandler<ActionEvent>
	{

		public void handle(ActionEvent event) 
		{
			int dSelection = displayFeatures.getSelectionModel().getSelectedIndex();
			
			if(dSelection == 0) { //for vertical display (Adjust number of columns)
				displayOptions = new TextInputDialog("Number of Columns");
				displayOptions.setTitle("Vertical Adjustment");
				displayOptions.setHeaderText("Input the desired number of columns");
				displayOptions.setContentText("Please enter a number: ");
				
				Optional<String> result = displayOptions.showAndWait();
				if(result.isPresent())
				{
					Integer Cvalue = Integer.valueOf(result.get());
					columns = Cvalue;
					displayData();
					return;
				}
				
			}
			else 
			{				  //for horizontal display (Adjust number of rows)
				displayOptions = new TextInputDialog("Number of Rows");
				displayOptions.setTitle("Horizontal Adjustment");
				displayOptions.setHeaderText("Input the desired number of rows");
				displayOptions.setContentText("Please enter a number: ");
				
				Optional<String> result = displayOptions.showAndWait();
				if(result.isPresent()) {
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
		if(result.isPresent())
		{
			 dValue = Integer.valueOf(result.get());
		}
		
		for(int i = 0; i<data.length; i++)
		{
			if(data[i] == dValue) 
			{
				data[i] = 0;
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
		for(int i = 0; i < data.length; i++) {
			current = new Label("" + data[i]);
			stats.getChildren().add(current);
		}
	}

}// end class UserPane
