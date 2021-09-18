package ce1002.blurical.s107502576.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import ce1002.blurical.s107502576.Company;
import ce1002.blurical.s107502576.News;
import ce1002.blurical.s107502576.blurical;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
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

public class CompanyDataController implements Initializable {
	
	//for getting maxValues
	
	private ArrayList<Integer> buyPrices = new ArrayList<Integer>();
	private ArrayList<Integer> dailyEarns = new ArrayList<Integer>();
	private ArrayList<Integer> pollutions = new ArrayList<Integer>();
	private ArrayList<Integer> dailyPollutions = new ArrayList<Integer>();
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
	public Label _title;

	@FXML
	public ImageView _close;
	
	@FXML
	public JFXButton _buyCompany;
	@FXML
	public JFXButton _back;

	@FXML
	public AnchorPane _companyDataPane;
	@FXML
	public AnchorPane _field;

	//暫停任務
	
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
    	
    	for(String companyName:blurical.companyList.keySet()) {
    		
    		buyPrices.add(blurical.companyList.get(companyName).buyPrice);
    		dailyEarns.add(blurical.companyList.get(companyName).dailyEarn);
    		pollutions.add(blurical.companyList.get(companyName).pollution);
    		dailyPollutions.add(blurical.companyList.get(companyName).dailyPollution);
    		reputations.add(blurical.companyList.get(companyName).reputation);
    		
    	}
    	
    }
	
	public void createDataChart() {
		
		//create axis
		
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        
        //create barChart
        
        final BarChart<Number,String> dataChart = new BarChart<>(xAxis,yAxis);

        //set range
        
        xAxis.setAutoRanging(false);	//bound range
        xAxis.setLowerBound(0);
        xAxis.setTickUnit(0.1);
        xAxis.setUpperBound(1);
        
        //xAxis invisible
        
        xAxis.setMinorTickVisible(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        
        //create series
        
        XYChart.Series<Number,String> series1 = new XYChart.Series<Number,String>();
        
        Company targetCompany = blurical.companyList.get(blurical.companySelected);
        
        //set series data
        
        series1.setName(targetCompany.name);
        
        series1.getData().add(new XYChart.Data<Number,String>((double)targetCompany.reputation/Collections.max(reputations), "名聲"));
        series1.getData().add(new XYChart.Data<Number,String>(Math.abs((double)targetCompany.dailyPollution/Collections.max(dailyPollutions)), "日汙染"));
        series1.getData().add(new XYChart.Data<Number,String>(Math.abs((double)targetCompany.pollution/Collections.max(pollutions)), "總汙染"));
        series1.getData().add(new XYChart.Data<Number,String>((double)targetCompany.dailyEarn/Collections.max(dailyEarns), "日收入"));
        series1.getData().add(new XYChart.Data<Number,String>((double)targetCompany.buyPrice/Collections.max(buyPrices), "總資產"));

        //add series1 to dataChart
        
        dataChart.getData().add(series1);

        //負汙染(綠化)時長條圖用綠色呈現
        if(targetCompany.dailyPollution < 0) {
            series1.getData().get(1).getNode().setStyle("-fx-bar-fill: green;");	//節點要series1先裝上去才不會NullPointerException        	
        }
        if(targetCompany.pollution < 0) {
            series1.getData().get(2).getNode().setStyle("-fx-bar-fill: green;");      	
        }
        
        //set dataChart properties
        
        dataChart.setLegendVisible(false);        
        dataChart.setHorizontalGridLinesVisible(false);
        
        dataChart.setBarGap(17.5);	//設定偏移，當有多組series時，bar可以偏移並容納下所有該category的bar
        
        dataChart.setMaxWidth(550);
        dataChart.setMaxHeight(325);
        
        dataChart.setLayoutX(40);
        dataChart.setLayoutY(50);
        
        //add to scene
		
		FadeTransition fadeIn_0 = new FadeTransition(Duration.seconds(1), _title);
		fadeIn_0.setFromValue(0.0);
		fadeIn_0.setToValue(1.0);

		_field.getChildren().add(dataChart);
		
		FadeTransition fadeIn_1 = new FadeTransition(Duration.seconds(1), dataChart);
		fadeIn_1.setFromValue(0.0);
		fadeIn_1.setToValue(1.0);
		
		final Label showMoney = new Label();
		final Label showDailyEarn = new Label();
		final Label showPollution = new Label();
		final Label showDailyPollution = new Label();
		final Label showReputation = new Label();
		
		//add labels to scene
		
		showMoney.setText(String.valueOf(targetCompany.buyPrice));
		showMoney.setLayoutX(105);
		showMoney.setLayoutY(91);
		showMoney.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showMoney);  
		
		FadeTransition fadeIn_2 = new FadeTransition(Duration.seconds(1), showMoney);
		fadeIn_2.setFromValue(0.0);
		fadeIn_2.setToValue(1.0);
		
		showDailyEarn.setText(String.valueOf(targetCompany.dailyEarn));
		showDailyEarn.setLayoutX(105);
		showDailyEarn.setLayoutY(91+56.8);
		showDailyEarn.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showDailyEarn);  
		
		FadeTransition fadeIn_3 = new FadeTransition(Duration.seconds(1), showDailyEarn);
		fadeIn_3.setFromValue(0.0);
		fadeIn_3.setToValue(1.0);
		
		showPollution.setText(String.valueOf(targetCompany.pollution));
		showPollution.setLayoutX(105);
		showPollution.setLayoutY(91+56.8+56.8);
		showPollution.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showPollution);  
		
		FadeTransition fadeIn_4 = new FadeTransition(Duration.seconds(1), showPollution);
		fadeIn_4.setFromValue(0.0);
		fadeIn_4.setToValue(1.0);
		
		showDailyPollution.setText(String.valueOf(targetCompany.dailyPollution));
		showDailyPollution.setLayoutX(105);
		showDailyPollution.setLayoutY(91+56.8+56.8+56.8);
		showDailyPollution.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showDailyPollution);  
		
		FadeTransition fadeIn_5 = new FadeTransition(Duration.seconds(1), showDailyPollution);
		fadeIn_5.setFromValue(0.0);
		fadeIn_5.setToValue(1.0);
		
		showReputation.setText(String.valueOf(targetCompany.reputation));
		showReputation.setLayoutX(105);
		showReputation.setLayoutY(91+56.8+56.8+56.8+56.8);
		showReputation.setStyle("-fx-font-size: 10;");
		_field.getChildren().add(showReputation);        
		
		FadeTransition fadeIn_6 = new FadeTransition(Duration.seconds(1), showReputation);
		fadeIn_6.setFromValue(0.0);
		fadeIn_6.setToValue(1.0);
        
        ParallelTransition animation = new ParallelTransition(fadeIn_0, fadeIn_1, fadeIn_2, fadeIn_3, fadeIn_4, fadeIn_5, fadeIn_6);
        
        animation.play();
        
	}
	
	public void updateData() {
		
		_money.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).money));
		_pollution.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).pollution));
		_reputation.setText(String.valueOf(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).reputation));
		
		_date.setText(blurical.getDate());
		_title.setText(blurical.companySelected + " 的財務報表");
		
		blurical.updateMainPageData();	//點回mainPage時如果還沒接到下一個frame的話，會出現_money顯示不一樣的數值(雖然只有一個frame
		
		if(blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).money < blurical.companyList.get(blurical.companySelected).buyPrice || blurical.companySelected.equals(blurical.companyList.keySet().toArray()[0])) {
			_buyCompany.setDisable(true);
		} else {
			_buyCompany.setDisable(false);
		}
		
		getMaxValues();
		createDataChart();
		
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("CompanyDataController initialized with company " + blurical.companySelected + ".");
    	
    	//掛載暫停 	
    	
        sleeper.setOnSucceeded((e) -> {
    		MainController.pause = false;
    		blurical.mainStage.setScene(blurical.mainPage);   
        });
        
        //掛載按鈕
    	
    	_close.setOnMouseClicked((e) -> {
    		blurical.mainStage.close();
    	});
    	
    	_back.setOnMouseClicked((e) -> {        	
    		
        	System.out.println("CompanyDataController disposed.");     
        	
        	new Thread(sleeper).start();	//為了按鈕的漂亮動畫延遲1/4秒
        	
    	});
    	
    	_buyCompany.setOnMouseClicked((e) -> {
    		
        	blurical._newNews.setVisible(true);
        	blurical.newsList.add(0, new News("公司併購事件", blurical.userName + "買下了" + blurical.companySelected + "。"));
    		
    		blurical.companyList.get(blurical.companyList.keySet().toArray()[0]).buyCompany(blurical.companyList.get(blurical.companySelected));
    		
    		updateData();
    		
    		_title.setText(blurical.companySelected + " 已併購");
    		
    		_buyCompany.setDisable(true);
        	
    	});
    	
    	blurical._back = this._back;
    	blurical._companyDataPane = this._companyDataPane;
    	
    	updateData();
  	  
  	}
    
}
