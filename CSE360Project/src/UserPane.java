
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class UserPane extends BorderPane {
	private Button manualButton, fileButton, deleteButton, displayStats, outputFile;
	private ComboBox<String> displayFeatures;
	private Pane canvas;

	// Constructor

	public UserPane() {
		// Step #1: initialize instance variable and set up layout
		manualButton = new Button("add data");
		fileButton = new Button("add data from file");
		deleteButton = new Button("delete data");
		displayStats = new Button("display statistics");
		outputFile = new Button("output file");
		
		manualButton.setMinWidth(80.0);
		fileButton.setMinWidth(80.0);
		deleteButton.setMinWidth(80.0);
		displayStats.setMinWidth(150.0);
		outputFile.setMinWidth(150.0);
		
		manualButton.setDisable(false);
		fileButton.setDisable(false);
		deleteButton.setDisable(false);
		displayStats.setDisable(false);
		outputFile.setDisable(false);
		
		// Create the display comboBox,
		// ----
		displayFeatures = new ComboBox<String>();
		displayFeatures.getItems().addAll("display vertically", "display horizontally");
		

		// topPane should contain two combo boxes and two buttons
		HBox topPane = new HBox();
		topPane.setSpacing(40);
		topPane.setPadding(new Insets(10, 10, 10, 10));
		topPane.setStyle("-fx-border-color: black");
		topPane.getChildren().addAll(manualButton, fileButton, displayFeatures, deleteButton);
		
		HBox bottomPane = new HBox();
		bottomPane.setSpacing(100);
		bottomPane.setPadding(new Insets(10, 100, 10, 10));
		bottomPane.setStyle("-fx-border-color: black");
		bottomPane.getChildren().addAll(displayStats, outputFile);
		

		// canvas is a Pane where we will draw lines
		canvas = new Pane();
		canvas.setStyle("-fx-background-color: white;");

		// add the canvas at the center of the pane and top panel
		// should have two combo boxes and two buttons
		this.setCenter(canvas);
		this.setTop(topPane);
		this.setBottom(bottomPane);

		// Step #3: Register the source nodes with its handler objects


		manualButton.setOnAction(new ButtonHandler());
		fileButton.setOnAction(new ButtonHandler());
		deleteButton.setOnAction(new ButtonHandler());
		displayStats.setOnAction(new ButtonHandler());
		outputFile.setOnAction(new ButtonHandler());
		

		// Need to set the initial values for the combo boxes and fireEvent to call
		// handler code.


	}



	// Step #2(B)- A handler class used to handle events from Undo & Erase buttons
	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Object source = event.getSource();

		
		}

	}


}// end class UserPane