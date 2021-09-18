package ce1002.blurical.s107502576.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import ce1002.blurical.s107502576.blurical;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainController implements Initializable {
	
	public static boolean pause = false;	//for game time
	
	public static Scene compayDataPage;
	public static Scene buyTechnologyPage;
	public static Scene newsPage;
    
    //FXML components

	@FXML
	public AnchorPane _mainPane;
	@FXML
	public AnchorPane _helpPane;
    
	@FXML
	public Label _money;
	@FXML
	public Label _pollution;
	@FXML
	public Label _reputation;
	@FXML
	public Label _date;
	@FXML
	public Label _helpText;
	
	@FXML
	public ImageView _company0;
	@FXML
	public ImageView _company1;
	@FXML
	public ImageView _company2;
	@FXML
	public ImageView _company3;
	@FXML
	public ImageView _company4;
	@FXML
	public ImageView _company5;
	@FXML
	public ImageView _company6;
	@FXML
	public ImageView _company7;
	@FXML
	public ImageView _company8;
	@FXML
	public ImageView _company9;
	@FXML
	public ImageView _close;
	@FXML
	public ImageView _newNews;
	@FXML
	public ImageView _helpClose;
	
	@FXML
	public JFXButton _buyTechnology;
	@FXML
	public JFXButton _checkNews;
	
	//set game time
	
	Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000/5),(e)-> {
		
		if(!pause) {
			
			blurical.updateDate();
			
			for(var companyName:blurical.companyList.keySet()) {
				blurical.companyList.get(companyName).grow();
			}
			
			blurical.updateMainPageData();
			
			_buyTechnology.setDisable(false);
			
			if(blurical.newsList.size() > 0) {
				_checkNews.setDisable(false);				
			} else {
				_checkNews.setDisable(true);	
			}
			
			boolean checkAllBought = true;
			
			int worldPollution = 0;
			
			for(String company:blurical.companyList.keySet()) {
				
				if(company.equals(blurical.companyList.keySet().toArray()[0])) {
					checkAllBought = checkAllBought && !blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).isBought;
				} else {
					checkAllBought = checkAllBought && blurical.companyList.get(company).isBought;
				}
				
				worldPollution += blurical.companyList.get(company).pollution;
				
			}
			
			if(checkAllBought || blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).buyPrice < 0 || worldPollution > blurical.level*1000000) {
				
		    	MainController.pause = true;
		    	
		    	if(checkAllBought) {
		    		
			    	_helpText.setText("你贏了！");	
			    	
			    	blurical.companySelected = blurical.companyList.keySet().toArray()[0].toString();
			    	
			    	_helpClose.setOnMouseClicked((event) -> {
						
						//initialize companyDataPage
					    
					    FXMLLoader companyDataLoadder = new FXMLLoader(getClass().getResource("../views/CompanyDataPage.fxml"));
					    
					    Parent companyData;
						try {
							
							companyData = companyDataLoadder.load();	//CompanyDataController initialized
					    	
					    	blurical._back.setOnMouseClicked((mouseEvent) -> {
					    		
					    		blurical.fps.stop();
				    		    
				    		    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.75), blurical._companyDataPane);
				    		    fadeOut.setFromValue(1.0);
				    		    fadeOut.setToValue(0.0);
				    		    
				    		    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), blurical._startPane);
				    		    fadeIn.setFromValue(0.0);
				    		    fadeIn.setToValue(1.0);
				    		    
				    		    fadeOut.setOnFinished((finishedEvent) -> {
				    		    	blurical._startPane.setOpacity(0.0);
				            		blurical.mainStage.setScene(blurical.startPage);  
				    		    	fadeIn.play();
				    		    });
				    		    
				    		    fadeOut.play();
				    		    
					    	});
						    
						    companyData.setOnMousePressed(blurical.grabRoot);	    
						    companyData.setOnMouseDragged(blurical.moveWindow);
						    
						    compayDataPage = new Scene(companyData);
						    
				    		blurical.mainStage.setScene(compayDataPage);   
							
						} catch (IOException err) {
							
							err.printStackTrace();
							
						}
						
			    	});
			    	
		    	} else  {
		    		
		    		if(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).buyPrice < 0) {
				    	_helpText.setText("你破產了！");
			    	} else if(worldPollution > blurical.level*1000000) {
				    	_helpText.setText("地球毀滅了！");		    		
			    	}
			    	
			    	_helpClose.setOnMouseClicked((mouseEvent) -> {
			    		
			    		blurical.fps.stop();
		    		    
		    		    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.75), blurical._mainPane);
		    		    fadeOut.setFromValue(1.0);
		    		    fadeOut.setToValue(0.0);
		    		    
		    		    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.75), blurical._startPane);
		    		    fadeIn.setFromValue(0.0);
		    		    fadeIn.setToValue(1.0);
		    		    
		    		    fadeOut.setOnFinished((event) -> {
		    		    	blurical._startPane.setOpacity(0.0);
		            		blurical.mainStage.setScene(blurical.startPage);  
		    		    	fadeIn.play();
		    		    });
		    		    
		    		    fadeOut.play();
		    		    
			    	});
			    	
		    	}
		    	
		    	blurical._helpPane.setVisible(true);
		    	
			}			
			
		} else {
			
			_buyTechnology.setDisable(true);
			_checkNews.setDisable(true);
			
		}
		
	}));
    
    //to get company info
	
	public EventHandler<MouseEvent> onCompanySelected = (event) -> {
		
		ImageView source = (ImageView) event.getSource();
		
		if(blurical.companyList.get(source.getId()).isBought || pause) return;
		
		source.setLayoutX(source.getLayoutX()+5);
		source.setLayoutY(source.getLayoutY()+5);
		source.setFitWidth(30);
		source.setFitHeight(30);
		
		blurical.companySelected = source.getId();
		//System.out.println(source.getId());
		
		pause = true;
		
		//initialize companyDataPage
	    
	    FXMLLoader companyDataLoadder = new FXMLLoader(getClass().getResource("../views/CompanyDataPage.fxml"));
	    
	    Parent companyData;
		try {
			
			companyData = companyDataLoadder.load();	//CompanyDataController initialized
		    
		    companyData.setOnMousePressed(blurical.grabRoot);	    
		    companyData.setOnMouseDragged(blurical.moveWindow);
		    
		    compayDataPage = new Scene(companyData);
		    
    		blurical.mainStage.setScene(compayDataPage);   
			
		} catch (IOException err) {
			
			err.printStackTrace();
			
		}
		
	};
	
	public void settingCompanyButton(ImageView button) {
		
		button.setOnMouseClicked(onCompanySelected);
		
		button.setOnMouseEntered((e) -> {		
			if(pause) return;	//預防暫停
			button.setLayoutX(button.getLayoutX()-5);
			button.setLayoutY(button.getLayoutY()-5);
			button.setFitWidth(40);
			button.setFitHeight(40);
		});
		
		button.setOnMouseExited((e) -> {
			if(pause && button.getFitWidth() != 40) return;		//預防暫停，圖案在放大時暫停除外
			button.setLayoutX(button.getLayoutX()+5);
			button.setLayoutY(button.getLayoutY()+5);
			button.setFitWidth(30);
			button.setFitHeight(30);
		});
		
		blurical.companyButtonData.put(button.getId(), button);
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("MainController initialized.");
    	
    	//掛載按鈕
    	
    	_company0.setId(blurical.companyList.keySet().toArray()[0].toString());	//EventHandler可以拿到source的Id
    	settingCompanyButton(_company0);
    	
    	_company1.setId("清綠城");
    	settingCompanyButton(_company1);
    	
    	_company2.setId("阿三");
    	settingCompanyButton(_company2);
    	
    	_company3.setId("蒼之鎧");
    	settingCompanyButton(_company3);
    	
    	_company4.setId("格拉斯");
    	settingCompanyButton(_company4);
    	
    	_company5.setId("瑪迦羋");
    	settingCompanyButton(_company5);
    	
    	_company6.setId("鹿過");
    	settingCompanyButton(_company6);
    	
    	_company7.setId("K424");
    	settingCompanyButton(_company7);
    	
    	_company8.setId("Xyphuz");
    	settingCompanyButton(_company8);
    	
    	_company9.setId("Seal&Friends");
    	settingCompanyButton(_company9);
        
        //掛載按鈕
    	
    	_close.setOnMouseClicked((e) -> {
    		blurical.mainStage.close();
    	});
    	
    	_buyTechnology.setOnMouseClicked((e) -> {
    		
    		pause = true;
    		
    		//to initialize buyTechnologyPage
    	    
    	    FXMLLoader buyTechnologyLoadder = new FXMLLoader(getClass().getResource("../views/BuyTechnologyPage.fxml"));
    	    
    	    Parent buyTechnology;
    		try {
    			
    			buyTechnology = buyTechnologyLoadder.load();	//BuyTechnologyController initialized
    		    
    			buyTechnology.setOnMousePressed(blurical.grabRoot);	    
    			buyTechnology.setOnMouseDragged(blurical.moveWindow);
    		    
    		    buyTechnologyPage = new Scene(buyTechnology);

        		blurical.mainStage.setScene(buyTechnologyPage);   //Thread一個週期只能跑一次，這裡沒有dispose就沒動畫QQ
    			
    		} catch (IOException err) {
    			
    			err.printStackTrace();
    			
    		}
    		
    	});
    	
    	_helpClose.setOnMouseClicked((e) -> {
    		if(!pause && !_helpPane.isVisible()) return;	//有時有bug，在_helpPane出現並應該暫停時，時間會繼續跑(推測是剛好暫停時按到按鈕，回主畫面時繼續運行)，所以一旦_helpPane出現就要可以按_helpClose
    		pause = false;
    		_helpPane.setVisible(false);
    	});
    	
    	_checkNews.setOnMouseClicked((e) -> {
    		
    		pause = true;
    		
    		_newNews.setVisible(false);
    		
    		//to initialize buyTechnologyPage
    	    
    	    FXMLLoader newsLoadder = new FXMLLoader(getClass().getResource("../views/NewsPage.fxml"));
    	    
    	    Parent news;
    		try {
    			
    			news = newsLoadder.load();	//BuyTechnologyController initialized
    		    
    			news.setOnMousePressed(blurical.grabRoot);	    
    			news.setOnMouseDragged(blurical.moveWindow);
    		    
    			newsPage = new Scene(news);

        		blurical.mainStage.setScene(newsPage);   //Thread一個週期只能跑一次，這裡沒有dispose就沒動畫QQ
    			
    		} catch (IOException err) {
    			
    			err.printStackTrace();
    			
    		}
    		
    	});
    	
    	//set pointers
    	
    	blurical._mainPane = this._mainPane;
    	blurical._helpPane = this._helpPane;
    	blurical._newNews = this._newNews;
    	
    	blurical._money = this._money;
    	blurical._pollution = this._pollution;
    	blurical._reputation = this._reputation;
    	blurical._date = this._date;
    	
    	blurical.fps = this.fps;
    	
    	//time runs
  	  
    	fps.setCycleCount(Timeline.INDEFINITE);
    	fps.play();
  	  
  	}

}

