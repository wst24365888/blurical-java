package ce1002.blurical.s107502576.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ObservableValue;
import ce1002.blurical.s107502576.blurical;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SettingPageController implements Initializable {
	
	public Scene mainPage;
	public Scene helpPage;
	
	public int level = 50;
    
    //FXML components
	
	@FXML
	public AnchorPane _settingPane;
	
	@FXML
	public JFXButton _submit;
	
	@FXML
	public JFXTextField _userName;
	
	@FXML
	public JFXSlider _level;

	@FXML
	public ImageView _close;
	@FXML
	public ImageView _back;
	
	@FXML
	public Label _hintText;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("SettingPageController initialized.");
    	
    	_close.setOnMouseClicked((e) -> {
    		blurical.mainStage.close();
    	});
    	
    	_back.setOnMouseClicked((e) -> {        	
        	System.out.println("SettingPageController disposed.");
    		blurical.mainStage.setScene(blurical.startPage);
    	});

		_level.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			
			level = Math.round(newValue.floatValue());
			
			if(level >= 1 && level <= 3) {
				_hintText.setText(level + " : 簡單");
			} else if(level >= 4 && level <= 7) {
				_hintText.setText(level + " : 適中");				
			} else if(level >= 8 && level <= 10) {
				_hintText.setText(level + " : 頗難");				
			} else if(level >= 11 && level <= 15) {
				_hintText.setText(level + " : 挑戰");			
			} else {
				_hintText.setText(level + " : 不建議遊玩");		
			}
			
		});
		
		_submit.setOnMouseClicked((e) -> {
			
			System.out.println(_userName.getText());
			
			if(_userName.getText().equals("") || _userName.getText().equals("清綠城") || _userName.getText().equals("阿三") || _userName.getText().equals("蒼之鎧") || _userName.getText().equals("格拉斯") || _userName.getText().equals("瑪迦羋") || _userName.getText().equals("鹿過") || _userName.getText().equals("K424") || _userName.getText().equals("Xyphuz") || _userName.getText().equals("Seal&Friends")) {
				_userName.setText("");
				_userName.setPromptText("此名稱無法使用，請換一個名稱");
				return;
			};
			
			blurical.userName = _userName.getText();
			
			blurical.level = this.level;
    		
    		blurical.days = 0;
    	    
    		blurical.date = 1;
    		blurical.month = 1;
    		blurical.year = 2019;
    		
    		//input data
    		
    		try {
				blurical.getCompanyData();
			} catch (IOException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}
    		
    		try {
				blurical.getEventsData();
			} catch (IOException err) {
				// TODO Auto-generated catch block
				err.printStackTrace();
			}
    		
    		//initialize mainPage
    	    
    	    FXMLLoader mainLoadder = new FXMLLoader(getClass().getResource("../views/MainPage.fxml"));
    	    
    	    Parent main;
    		try {
    			
    			main = mainLoadder.load();	//MainController initialized
    		    
    			main.setOnMousePressed(blurical.grabRoot);	    
    			main.setOnMouseDragged(blurical.moveWindow);
    		    
    		    mainPage = new Scene(main);
    		    
    		    blurical.mainPage = this.mainPage;
    		    
    		    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.75), _settingPane);
    		    fadeOut.setFromValue(1.0);
    		    fadeOut.setToValue(0.0);
    		    
    		    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), blurical._mainPane);
    		    fadeIn.setFromValue(0.0);
    		    fadeIn.setToValue(1.0);
    		    
    		    fadeOut.setOnFinished((event) -> {
    		    	blurical._mainPane.setOpacity(0.0);
            		blurical.mainStage.setScene(blurical.mainPage);
            		MainController.pause = false;
    		    	fadeIn.play();
    		    });
    		    
    		    fadeOut.play();
    			
    		} catch (IOException err) {
    			
    			err.printStackTrace();
    			
    		}
			
		});
  	  
  	}
    
}
