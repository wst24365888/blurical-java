package ce1002.blurical.s107502576.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ce1002.blurical.s107502576.blurical;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StartPageController implements Initializable {
	
	public Scene settingPage;
	public Scene helpPage;
    
    //FXML components
	
	@FXML
	public AnchorPane _startPane;

	@FXML
	public ImageView _close;
	@FXML
	public ImageView _start;
	@FXML
	public ImageView _help;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("StartPageController initialized.");
    	
    	_close.setOnMouseClicked((e) -> {
    		blurical.mainStage.close();
    	});
    	
    	_start.setOnMouseClicked((e) -> {
    		
    		//initialize settingPage
    	    
    	    FXMLLoader settingLoadder = new FXMLLoader(getClass().getResource("../views/SettingPage.fxml"));
    	    
    	    Parent setting;
    		try {
    			
    			setting = settingLoadder.load();	//MainController initialized
    		    
    			setting.setOnMousePressed(blurical.grabRoot);	    
    			setting.setOnMouseDragged(blurical.moveWindow);
    		    
    			settingPage = new Scene(setting);
    			
        		blurical.mainStage.setScene(settingPage);
    			
    		} catch (IOException err) {
    			
    			err.printStackTrace();
    			
    		}
    	   	
    	});
    	
    	_help.setOnMouseClicked((e) -> {

    		//initialize helpPage
    	    
    	    FXMLLoader helpLoadder = new FXMLLoader(getClass().getResource("../views/HelpPage.fxml"));
    	    
    	    Parent help;
    		try {
    			
    			help = helpLoadder.load();	//HelpPageController initialized
    		    
    			help.setOnMousePressed(blurical.grabRoot);	    
    			help.setOnMouseDragged(blurical.moveWindow);
    		    
    			helpPage = new Scene(help);
    		    
        		blurical.mainStage.setScene(helpPage);
    			
    		} catch (IOException err) {
    			
    			err.printStackTrace();
    			
    		}
    		
    	});
    	
    	blurical._startPane = this._startPane;
  	  
  	}
    
}
