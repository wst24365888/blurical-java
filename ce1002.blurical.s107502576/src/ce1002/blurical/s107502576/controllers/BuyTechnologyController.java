package ce1002.blurical.s107502576.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import ce1002.blurical.s107502576.Technology;
import ce1002.blurical.s107502576.blurical;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class BuyTechnologyController implements Initializable {

	private int currentCategory = 0;
	private int currentIndex = 0;
	
	private boolean initialized = false;
	
	private ArrayList<String> categoryItems = new ArrayList<String>();
	
	private ArrayList<Integer> dailyEarns = new ArrayList<Integer>();
	private ArrayList<Integer> dialyPollutions = new ArrayList<Integer>();
	private ArrayList<Integer> reputations = new ArrayList<Integer>();
    
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
	public Label _category;
	@FXML
	public Label _index;
	@FXML
	public Label _techName;
	@FXML
	public Label _upgradeCost;
	@FXML
	public Label _level;

	@FXML
	public ImageView _close;
	@FXML
	public ImageView _categoryPlus;
	@FXML
	public ImageView _categoryMinus;
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
    
    public void getMaxValues() {
    	
    	for(String techName:blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.keySet()) {
    		
    		Technology target = blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName);
    		
    		dailyEarns.add(target.dailyEarn);
    		dialyPollutions.add(target.dailyPollution);
    		reputations.add(target.reputation);
    		
    	}
    	
    }
	
	public void createDataChart(String techName) {
		
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        
        final BarChart<Number,String> dataChart = new BarChart<>(xAxis,yAxis);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setTickUnit(0.1);
        xAxis.setUpperBound(1);
        
        xAxis.setMinorTickVisible(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        
        XYChart.Series<Number,String> series1 = new XYChart.Series<Number,String>();
        
        Technology targetTech = blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName);
        
        series1.setName(targetTech.name);
        series1.getData().add(new XYChart.Data<Number,String>((double)targetTech.reputation/Collections.max(reputations), "名聲")); 
        series1.getData().add(new XYChart.Data<Number,String>(Math.abs((double)targetTech.dailyPollution/Collections.max(dialyPollutions)), "日汙染"));
        series1.getData().add(new XYChart.Data<Number,String>(Math.abs((double)targetTech.dailyEarn/Collections.max(dailyEarns)), "日收入"));

        dataChart.getData().add(series1);

        //負汙染(綠化)時長條圖用綠色呈現
        if(targetTech.dailyPollution < 0) {
            series1.getData().get(1).getNode().setStyle("-fx-bar-fill: green;");	//節點要series1先裝上去才不會NullPointerException
        }
        if(targetTech.dailyEarn < 0) {
            series1.getData().get(2).getNode().setStyle("-fx-bar-fill: black;");
        }
        
        dataChart.setLegendVisible(false);
        
        dataChart.setHorizontalGridLinesVisible(false);
        
        dataChart.setBarGap(20.0);
        
        dataChart.setMaxWidth(550);
        dataChart.setMaxHeight(225);
        
        dataChart.setLayoutX(40);
        dataChart.setLayoutY(100);
        
        //add to scene
		
		FadeTransition fadeIn_0 = new FadeTransition(Duration.seconds(1), _techName);
		fadeIn_0.setFromValue(0.0);
		fadeIn_0.setToValue(1.0);

		_field.getChildren().add(dataChart);
		
		FadeTransition fadeIn_1 = new FadeTransition(Duration.seconds(1), dataChart);
		fadeIn_1.setFromValue(0.0);
		fadeIn_1.setToValue(1.0);
		
		final Label showDailyEarn = new Label();
		final Label showDailyPollution = new Label();
		final Label showReputation = new Label();
		
		showDailyEarn.setText(String.valueOf(targetTech.dailyEarn));
		showDailyEarn.setLayoutX(105);
		showDailyEarn.setLayoutY(214-70);
		showDailyEarn.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showDailyEarn);
		
		FadeTransition fadeIn_2 = new FadeTransition(Duration.seconds(1), showDailyEarn);
		fadeIn_2.setFromValue(0.0);
		fadeIn_2.setToValue(1.0);
		
		showDailyPollution.setText(String.valueOf(targetTech.dailyPollution));
		showDailyPollution.setLayoutX(105);
		showDailyPollution.setLayoutY(214-70+60.8);
		showDailyPollution.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showDailyPollution);  
		
		FadeTransition fadeIn_3 = new FadeTransition(Duration.seconds(1), showDailyPollution);
		fadeIn_3.setFromValue(0.0);
		fadeIn_3.setToValue(1.0);
		
		showReputation.setText(String.valueOf(targetTech.reputation));
		showReputation.setLayoutX(105);
		showReputation.setLayoutY(214-70+60.8+60.8);
		showReputation.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showReputation);        
		
		FadeTransition fadeIn_4 = new FadeTransition(Duration.seconds(1), showReputation);
		fadeIn_4.setFromValue(0.0);
		fadeIn_4.setToValue(1.0);
		
		FadeTransition fadeIn_5 = new FadeTransition(Duration.seconds(1), _upgradeCost);
		fadeIn_5.setFromValue(0.0);
		fadeIn_5.setToValue(1.0);
		
		FadeTransition fadeIn_6 = new FadeTransition(Duration.seconds(1), _level);
		fadeIn_6.setFromValue(0.0);
		fadeIn_6.setToValue(1.0);
        
        ParallelTransition animation = new ParallelTransition(fadeIn_0, fadeIn_1, fadeIn_2, fadeIn_3, fadeIn_4, fadeIn_5, fadeIn_6);
        
        if(!initialized) {
            animation.play();        	
        }
        
	}
	
	public void updateData() {
		
		_money.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).money));
		_pollution.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).pollution));
		_reputation.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).reputation));
		
		_date.setText(blurical.getDate());
		
		blurical.updateMainPageData();	//點回mainPage時如果還沒接到下一個frame的話，會出現_money顯示不一樣的數值(雖然只有一個frame
		
		getMaxValues();
		
	}
	
	public void createSubWidget(String techName) {
		
		//System.out.println(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).unlocked);
    	
    	_index.setText((currentIndex+1) + "/" + categoryItems.size());
		
		createDataChart(techName);
		
		_techName.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).name));
		_upgradeCost.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).cost));
		_level.setText(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).level + " / " + blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).maxLevel);
		
		
		if(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).unlocked) {
			
			_buyTech.setDisable(false);
			
			if(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).level == 0) {
				_closeTech.setDisable(true);				
			} else if(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).level == blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).maxLevel) {
				_buyTech.setDisable(true);
				_closeTech.setDisable(false);		
			} else {			
				_closeTech.setDisable(false);	
			}
			
		} else {
			
			_buyTech.setDisable(true);
			_closeTech.setDisable(true);
			
		}
		
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("BuyTechnologyController initialized.");
    	
        sleeper.setOnSucceeded((e) -> {
    		MainController.pause = false;
    		blurical.mainStage.setScene(blurical.mainPage);   
        });
    	
    	_close.setOnMouseClicked((e) -> {
    		blurical.mainStage.close();
    	});
    	
    	_back.setOnMouseClicked((e) -> {        	
        	System.out.println("BuyTechnologyController disposed.");
        	new Thread(sleeper).start();	//為了按鈕的漂亮動畫延遲1/4秒
    	});
    	
    	_category.textProperty().addListener((ObservableValue<? extends String> observable, String oldString, String newString) -> {
        	
        	ArrayList<String> currentCategoryItems = new ArrayList<String>();
        	
        	for(var techName:blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.keySet()) {
        		
        		if(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).categoryName == newString) {
        			currentCategoryItems.add(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName).name);
        		}
        		
        	}
        	
        	categoryItems = currentCategoryItems;
        	
        	currentIndex = 0;
        	
        	String techName = categoryItems.get(currentIndex);
        	
        	createSubWidget(techName);
            	
        });
    	
    	_categoryPlus.setOnMouseClicked((e) -> {
    		
    		currentCategory += 1;
        	
        	if(currentCategory > 5) {
        		currentCategory -= 6;
        	} else if (currentCategory < 0) {
        		currentCategory += 6;        		
        	}
    		
    		_category.setText(Technology.technologyCategory[currentCategory]);  
    		
    	});
		
		_categoryPlus.setOnMouseEntered((e) -> {
			_categoryPlus.setLayoutX(_categoryPlus.getLayoutX()-2.5);
			_categoryPlus.setLayoutY(_categoryPlus.getLayoutY()-2.5);
			_categoryPlus.setFitWidth(35);
			_categoryPlus.setFitHeight(35);
		});
		
		_categoryPlus.setOnMouseExited((e) -> {
			_categoryPlus.setLayoutX(_categoryPlus.getLayoutX()+2.5);
			_categoryPlus.setLayoutY(_categoryPlus.getLayoutY()+2.5);
			_categoryPlus.setFitWidth(30);
			_categoryPlus.setFitHeight(30);
		});
    	
    	_categoryMinus.setOnMouseClicked((e) -> {
    		
    		currentCategory -= 1;
        	
        	if(currentCategory > 5) {
        		currentCategory -= 6;
        	} else if (currentCategory < 0) {
        		currentCategory += 6;        		
        	}
        	
    		_category.setText(Technology.technologyCategory[currentCategory]); 
    		
    	});
		
		_categoryMinus.setOnMouseEntered((e) -> {
			_categoryMinus.setLayoutX(_categoryMinus.getLayoutX()-2.5);
			_categoryMinus.setLayoutY(_categoryMinus.getLayoutY()-2.5);
			_categoryMinus.setFitWidth(35);
			_categoryMinus.setFitHeight(35);
		});
		
		_categoryMinus.setOnMouseExited((e) -> {
			_categoryMinus.setLayoutX(_categoryMinus.getLayoutX()+2.5);
			_categoryMinus.setLayoutY(_categoryMinus.getLayoutY()+2.5);
			_categoryMinus.setFitWidth(30);
			_categoryMinus.setFitHeight(30);
		});
    	
    	_indexPlus.setOnMouseClicked((e) -> {
        	
        	currentIndex += 1;
        	
        	if(currentIndex > categoryItems.size() - 1) {
        		currentIndex -= categoryItems.size();
        	} else if (currentIndex < 0) {
        		currentIndex += categoryItems.size();        		
        	}
        	
        	String techName = categoryItems.get(currentIndex);
        	
        	createSubWidget(techName);	//repaint
        	
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
        	
        	if(currentIndex > categoryItems.size() - 1) {
        		currentIndex -= categoryItems.size();
        	} else if (currentIndex < 0) {
        		currentIndex += categoryItems.size();        		
        	}
        	
        	String techName = categoryItems.get(currentIndex);
        	
        	createSubWidget(techName);	//repaint
        	
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
    	
    	_buyTech.setOnMouseClicked((e) -> {
        	
        	String techName = categoryItems.get(currentIndex);
    		
    		blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).buyTechnology(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName));
        	
        	createSubWidget(techName);	//repaint
        	
        	updateData();
    		
    	});
    	
    	_closeTech.setOnMouseClicked((e) -> {
        	
        	String techName = categoryItems.get(currentIndex);
    		
    		blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).closeTechnology(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).ownedTechnologyList.get(techName));
        	
        	createSubWidget(techName);
        	
        	updateData();
    		
    	});
    	
    	updateData();
		
		_category.setText(Technology.technologyCategory[currentCategory]);
		
		initialized = true;
  	  
  	}
    
}
