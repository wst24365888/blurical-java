package ce1002.blurical.s107502576;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class blurical extends Application {

	//global variables
	
	public static String userName;
	
	public static int level = 10;
	
	public static int days = 0;	//遊戲時間
    
	public static int date = 1;
	public static int month = 1;
	public static int year = 2019;
	
	public static int maxPollution = 10000;
    
	public static String companySelected;	
	public static HashMap<String, ImageView> companyButtonData = new HashMap<String, ImageView>();
	
	public static ArrayList<News> newsList =  new ArrayList<News>();
	public static ArrayList<Events> eventsList =  new ArrayList<Events>();

	public static AnchorPane _startPane;
	public static AnchorPane _companyDataPane; 
	public static AnchorPane _mainPane; 
	public static AnchorPane _helpPane; 	//宣告一個_helpPane的指標，直接把MainController中的_helpPane宣告成static會有NullPointerException
	
	public static ImageView _newNews;
	
	public static JFXButton _back;	//遊戲成功返回鍵
	
	public static Label _money;
	public static Label _pollution;
	public static Label _reputation;	
	public static Label _date;
	
	public static Timeline fps;

	public static LinkedHashMap<String, Company> companyList = new LinkedHashMap<String, Company>();	//所有改動都改裡面的東西
	
    //stage
    
	public static Stage mainStage;
	public static Scene startPage;
	public static Scene mainPage;

	//offsets
	
	private static double xOffset = 0;
	private static double yOffset = 0;
	
	//for other controller to update main page data
	
	public static void updateMainPageData() {
		
		_money.setText(String.valueOf(companyList.get(blurical.companyList.keySet().toArray()[0]).money));
		_pollution.setText(String.valueOf(companyList.get(blurical.companyList.keySet().toArray()[0]).pollution));
		_reputation.setText(String.valueOf(companyList.get(blurical.companyList.keySet().toArray()[0]).reputation));
		
		_date.setText(getDate());
		
	}
	
	//update game time

    public static void updateDate() {
    	
        days += 1;
        date += 1;
        
        switch (month) {
        
            case 12:
                if (date > 31) {
                    date = 1;
                    month = 1;
                    year += 1;
                }
                break;
                
            case 2:
                if (date > 28) {
                    if((year % 400 != 0 && year % 100 == 0) || year % 4 != 0) {
                    	//平年
                        date = 1;
                        month += 1;
                    } else if (date > 29) {
                    	//閏年
                        date = 1;
                        month += 1;
                    }
                }
                break;
                
            default:
                if(date > 30) {
                    if(month == 4 || month == 6 || month == 9 || month == 11) {
                        date = 1;
                        month += 1;
                    }
                    else if (date > 31) {
                        date = 1;
                        month += 1;
                    }
                }
                break;
        }
        
    }

    public static String getDate() {
    	//System.out.println(year + "-" + month + "-" + date);
        return year + "-" + month + "-" + date;
    }
	
	//input technologyData
	
	public static HashMap<String, Technology> getTechnologyData() throws IOException {
		
		HashMap<String, Technology> technologyDataList = new HashMap<String, Technology>();
    	
		FileInputStream technologyData = new FileInputStream("D:\\Dropbox\\Java\\class\\ce1002.blurical.s107502576\\src\\ce1002\\blurical\\s107502576\\data\\technologies.txt");
		byte[] allbytes = technologyData.readAllBytes();
		String content = new String(allbytes, "UTF-8").substring(1);
		
		//System.out.println(content);

		String[] contentArr = content.split("\\s+");
		
		for(int i = 0; i < contentArr.length;) {
			
			String name = contentArr[i++];
			
			int level = Integer.parseInt(contentArr[i++]);
			int dailyEarn = Integer.parseInt(contentArr[i++]);
			int dailyPollution = Integer.parseInt(contentArr[i++]);
			int reputation = Integer.parseInt(contentArr[i++]);
			int cost = Integer.parseInt(contentArr[i++]);
			int category = Integer.parseInt(contentArr[i++]);
			
			technologyDataList.put(name, new Technology(name, level, dailyEarn, dailyPollution, reputation, cost, category));
			
		}
		
		technologyData.close();
		
		return technologyDataList;
		
	}
	
	//input companyData
	
	public static void getCompanyData() throws IOException {
    	
		FileInputStream companyData = new FileInputStream("D:\\Dropbox\\Java\\class\\ce1002.blurical.s107502576\\src\\ce1002\\blurical\\s107502576\\data\\companies.txt");
		byte[] allbytes = companyData.readAllBytes();
		String content = new String(allbytes, "UTF-8").substring(1);
		
		//System.out.println(content);

		String[] contentArr = content.split("\\s+");
		
		for(int i = 0; i < contentArr.length;) {
			
			String name = contentArr[i++].replace("User", userName);
			
			int money = Integer.parseInt(contentArr[i++]);
			int dailyEarn = Integer.parseInt(contentArr[i++]);
			int pollution = Integer.parseInt(contentArr[i++]);
			int dailyPollution = Integer.parseInt(contentArr[i++]);
			int reputation = Integer.parseInt(contentArr[i++]);
			
			companyList.put(name, new Company(name, money, dailyEarn, pollution, dailyPollution, reputation, getTechnologyData()));
			
			int ownTechnologyAmount = Integer.parseInt(contentArr[i++]);
			
			for(int j = 0; j < ownTechnologyAmount; j++) {
				//System.out.println(contentArr[i]);
				companyList.get(name).ownedTechnologyName.add(contentArr[i]);
				companyList.get(name).ownedTechnologyList.get(contentArr[i++]).unlocked = true;
			}
			
		}
		
		companyData.close();
		
	}
	
	//input eventsData

	
	public static void getEventsData() throws IOException {
    	
		FileInputStream eventsData = new FileInputStream("D:\\Dropbox\\Java\\class\\ce1002.blurical.s107502576\\src\\ce1002\\blurical\\s107502576\\data\\news.txt");
		byte[] allbytes = eventsData.readAllBytes();
		String content = new String(allbytes, "UTF-8").substring(1);
		
		//System.out.println(content);

		String[] contentArr = content.split("\\s+");
		
		for(int i = 0; i < contentArr.length;) {
			
			String newsTitle = contentArr[i++];
			String newsBody = contentArr[i++];
			
			int eventsAmount = Integer.parseInt(contentArr[i++]);
			
			ArrayList<Buff> buffList = new ArrayList<Buff>();
			
			for(int j = 0; j < eventsAmount; j++) {
				
				//System.out.println(contentArr[i]);
				String buffInfo = contentArr[i++];
				String[] buffInfoArr = buffInfo.split(",");
				
				buffList.add(new Buff(Integer.parseInt(buffInfoArr[0]), Integer.parseInt(buffInfoArr[1]), Double.parseDouble(buffInfoArr[2])));
				
			}
			
			eventsList.add(new Events(newsTitle, newsBody, buffList));
			
		}
		
		eventsData.close();
		
	}
    
    //grab mouse position to root
	
	public static EventHandler<MouseEvent> grabRoot = (event) -> {
		//System.out.println(event.getSceneX() + ", " + event.getSceneY());
   		if(event.getSceneY() <= 40.0) {
    	   	xOffset = event.getSceneX();
           	yOffset = event.getSceneY();
   		}		
	};
	   
	//drag and move around
	
	public static EventHandler<MouseEvent> moveWindow = (event) -> {
		//System.out.println(event.getScreenX() + ", " + event.getScreenY());
   		if(event.getSceneY() <= 40.0) {
	    	mainStage.setX(event.getScreenX() - xOffset);
	    	mainStage.setY(event.getScreenY() - yOffset);	       			
   		}
	};
	
	@Override
	public void start(Stage mainStage) throws IOException {
		
		//initialize mainPage
	    
	    FXMLLoader startLoadder = new FXMLLoader(getClass().getResource("views/StartPage.fxml"));
	    Parent start = startLoadder.load();		//StartPageController initialized
	    
	    start.setOnMousePressed(grabRoot);	    
	    start.setOnMouseDragged(moveWindow);
	    
	    startPage = new Scene(start);
	    
	    //set mainStage
		
		blurical.mainStage = mainStage;
	    
	    mainStage.initStyle(StageStyle.TRANSPARENT);
	    mainStage.setTitle("Blurical");
	    mainStage.setResizable(false);
	   	
	   	mainStage.setScene(startPage);
	   	mainStage.show();
	
	}

	public static void main(String[] args){
	    launch(args);
	}


}
