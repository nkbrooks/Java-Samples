package table;

import javafx.scene.control.TextArea;
import javafx.application.*; 
import javafx.scene.control.Slider; 
import javafx.scene.control.TextField; 
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.control.Label;


public class InterestTableGUI extends Application {
private static final int SIMPLE_INTEREST = 1, COMPOUND_INTEREST = 2;
@Override
	public void start(Stage primaryStage){
		int sceneWidth = 700;
		int sceneHeight = 600;
		
		//Bottom Buttons
		 FlowPane buttons = new FlowPane(); 
		 buttons.setHgap(6); 
		 buttons.setVgap(6); 
		 Button simpleInterest = new Button("Simple Interest");
		 Button compoundInterest = new Button("Compound Interest"); 
		 Button bothInterests = new Button("Both Interests"); 
		 buttons.setAlignment(Pos.BOTTOM_CENTER); 
		 buttons.getChildren().addAll(simpleInterest, compoundInterest, bothInterests); 
		 
		//Principal and Rate(Percentage) Fields 
		FlowPane pane  = new FlowPane(); 
		 pane.setHgap(10); 
		 
		 Label principalLabel = new Label("Principal: "); 
		 Label ratePercentageLabel = new Label("Rate: "); 
		 TextField principalField = new TextField(); 
		 TextField ratePercentage = new TextField(); 
		 
		 
		 pane.getChildren().add(principalLabel);
		 pane.getChildren().add(principalField);
		 pane.getChildren().add(ratePercentageLabel);
		 pane.getChildren().add(ratePercentage);

		 pane.setAlignment(Pos.CENTER); 
		 
		 

		 //Text Area
		 TextArea displayArea = new TextArea();
		 displayArea.setWrapText(true); 
		 
		 
		 //Number of years horizontal slider
		 FlowPane slider = new FlowPane(); 
		 
		 Slider horizontalSlider = new Slider(); 
		 horizontalSlider.setMin(0); 
		 horizontalSlider.setMax(25); 
		  horizontalSlider.setValue(25);
		  horizontalSlider.setMajorTickUnit(1); 
		  horizontalSlider.setShowTickMarks(true); 
		  horizontalSlider.setShowTickLabels(true); 
		  
		  Label sliderLabel = new Label("Number of Years: "); 
		  horizontalSlider.setPrefWidth(450.0); 
		  
		  slider.getChildren().add(sliderLabel);
		  slider.getChildren().add(horizontalSlider); 

		  slider.setAlignment(Pos.CENTER); 
		  
		 
		 //Overall Structure
		 VBox structure = new VBox(8); 
		 structure.getChildren().addAll(displayArea, pane, slider, buttons); 
		 
		 //Controller
		 
		 //inner class
		 class SimpleInterestEventHandler implements EventHandler<ActionEvent> { 
		 
			 @Override 
			 public void handle(ActionEvent event) { 
				 int sliderValue = (int)horizontalSlider.getValue();
				 displayArea.setText(driver.format(Double.parseDouble(principalField.getText()), 
						 Double.parseDouble(ratePercentage.getText()), sliderValue ,SIMPLE_INTEREST));  
			 }
			 
		 }
		 
		 simpleInterest.setOnAction(new SimpleInterestEventHandler()); 
		 
		 //anonymous class 
		 
		 compoundInterest.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 
			 public void handle(ActionEvent event) {
				 
				 int sliderValue = (int)horizontalSlider.getValue();
				 
				 displayArea.setText(driver.format(Double.parseDouble(principalField.getText()),
						 Double.parseDouble(ratePercentage.getText()), sliderValue, COMPOUND_INTEREST)); 
				 
				 //The parse double method returns the double representation of the passed String argument. 
			}
		 } );
		 
		 //Lambda functions
		 
		 bothInterests.setOnAction (
				 
				 e -> {
					 displayArea.setText(driver.format(Double.parseDouble(principalField.getText()),
							 Double.parseDouble(ratePercentage.getText()), (int)horizontalSlider.getValue(), 3));
				 }
				 
				 );
		 
		 Scene scene = new Scene(structure, sceneWidth, sceneHeight); 
		 primaryStage.setTitle("Interest Table Calculator"); 
		 primaryStage.setScene(scene); 
		 primaryStage.show();
		 
		}

		public static void main (String[] args) {
			Application.launch(args);
	}
}
