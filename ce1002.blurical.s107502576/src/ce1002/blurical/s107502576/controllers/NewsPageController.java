package ce1002.blurical.s107502576.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import ce1002.blurical.s107502576.blurical;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class NewsPageController implements Initializable {

	private int currentIndex = 0;
    
    //FXML components
    
	@FXML
	public Label _money;
	@FXML
	public Label _pollution;
	@FXML
	public Label _reputation;
	@FXML
	public Label _date;
	@FXML
	public Label _index;
	@FXML
	public Label _newsTitle;
	@FXML
	public Label _newsDate;
	@FXML
	public Label _newsBody;

	@FXML
	public ImageView _close;
	@FXML
	public ImageView _indexPlus;
	@FXML
	public ImageView _indexMinus;
	
	@FXML
	public JFXButton _buyTech;
	@FXML
	public JFXButton _closeTech;
	@FXML
	public JFXButton _back;
	
	@FXML
	public AnchorPane _field;

    Task<Void> sleeper = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
            	
            }
            return null;
        }
    };
	
	public void createSubWidget() {
		
		_newsTitle.setText(blurical.newsList.get(currentIndex).title);
		_newsDate.setText(blurical.newsList.get(currentIndex).date);
		_newsBody.setText(blurical.newsList.get(currentIndex).body);
		
		_index.setText((currentIndex+1) + "/" + blurical.newsList.size());
		
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("NewsPageController initialized.");
    	
        sleeper.setOnSucceeded((e) -> {
    		MainController.pause = false;
    		blurical.mainStage.setScene(blurical.mainPage);   
        });
    	
    	_close.setOnMouseClicked((e) -> {
    		blurical.mainStage.close();
    	});
    	
    	_back.setOnMouseClicked((e) -> {        	
        	System.out.println("NewsPageController disposed.");
        	new Thread(sleeper).start();	//為了按鈕的漂亮動畫延遲1/4秒
    	});
    	
    	_indexPlus.setOnMouseClicked((e) -> {
        	
        	currentIndex += 1;
        	
        	if(currentIndex > blurical.newsList.size() - 1) {
        		currentIndex -= blurical.newsList.size();
        	} else if (currentIndex < 0) {
        		currentIndex += blurical.newsList.size();        		
        	}

        	createSubWidget();	//repaint
        	
    	});
		
		_indexPlus.setOnMouseEntered((e) -> {
			_indexPlus.setLayoutX(_indexPlus.getLayoutX()-2.5);
			_indexPlus.setLayoutY(_indexPlus.getLayoutY()-2.5);
			_indexPlus.setFitWidth(35);
			_indexPlus.setFitHeight(35);
		});
		
		_indexPlus.setOnMouseExited((e) -> {
			_indexPlus.setLayoutX(_indexPlus.getLayoutX()+2.5);
			_indexPlus.setLayoutY(_indexPlus.getLayoutY()+2.5);
			_indexPlus.setFitWidth(30);
			_indexPlus.setFitHeight(30);
		});
    	
    	_indexMinus.setOnMouseClicked((e) -> {
        	
        	currentIndex -= 1;
        	
        	if(currentIndex > blurical.newsList.size() - 1) {
        		currentIndex -= blurical.newsList.size();
        	} else if (currentIndex < 0) {
        		currentIndex += blurical.newsList.size();        		
        	}
        	
        	createSubWidget();	//repaint
        	
    	});
		
		_indexMinus.setOnMouseEntered((e) -> {
			_indexMinus.setLayoutX(_indexMinus.getLayoutX()-2.5);
			_indexMinus.setLayoutY(_indexMinus.getLayoutY()-2.5);
			_indexMinus.setFitWidth(35);
			_indexMinus.setFitHeight(35);
		});
		
		_indexMinus.setOnMouseExited((e) -> {
			_indexMinus.setLayoutX(_indexMinus.getLayoutX()+2.5);
			_indexMinus.setLayoutY(_indexMinus.getLayoutY()+2.5);
			_indexMinus.setFitWidth(30);
			_indexMinus.setFitHeight(30);
		});
    	
		_money.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).money));
		_pollution.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).pollution));
		_reputation.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).reputation));
		
		_date.setText(blurical.getDate());
		
		createSubWidget();
  	  
  	}
    
}
