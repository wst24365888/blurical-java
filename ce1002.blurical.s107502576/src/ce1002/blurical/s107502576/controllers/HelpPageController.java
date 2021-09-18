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

public class HelpPageController implements Initializable {

	private int helpPageSize = 3;

	private int currentIndex = 0;
    
    //FXML components
    
	@FXML
	public Label _index;
	@FXML
	public Label _helpTitle;
	@FXML
	public Label _helpText;

	@FXML
	public ImageView _close;
	@FXML
	public ImageView _indexPlus;
	@FXML
	public ImageView _indexMinus;
	@FXML
	public ImageView _relation;

	@FXML
	public JFXButton _back;

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
		
		_index.setText((currentIndex+1) + "/" + helpPageSize);
		
		if(currentIndex == 0) {
			
			_helpTitle.setText("機制圖");
			_relation.setVisible(true);
			
		} else if(currentIndex == 1) {
			
			_helpTitle.setText("Q&A");
			_relation.setVisible(false);

			_helpText.setText("Q1. 遊戲的目標?\nA1. 併購所有公司。\n\nQ2. 如何賺錢?\nA2. 升級已有的設施，隨著天數那些設施就會幫你賺錢。不過也要同時小心污染增加的問題哦！\n\nQ3. 污染增加會怎樣?\nA3. 如果污染增加到規定量，會扣除你的總資金作為懲罰。再更多的話可能會造成地球毀滅哦！");
			
		} else if(currentIndex == 2) {
			
			_helpTitle.setText("Q&A");
			_relation.setVisible(false);
			
			_helpText.setText("Q4. 輸的條件是什麼?\nA4. 地球毀滅(所有公司汙染總和超過遊戲難度*100萬)或總資產(注意不是總資金！)小於零時算是遊戲失敗。\n\nQ5. 如何找到已擁有的設施?\nA5. 在升級設施介面中，可升級的就是你已擁有的哦！\n\nQ6.如何解鎖其他設施?\nA6. 併購其他公司，將會得到該公司已擁有的設施。如果你還沒有該設施，會將其解鎖，如果你已擁有該設施，則其最大等級將會提升。");
			
		}
		
		
		
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("HelpPageController initialized.");
    	
        sleeper.setOnSucceeded((e) -> {
    		MainController.pause = false;
    		blurical.mainStage.setScene(blurical.startPage);   
        });
    	
    	_close.setOnMouseClicked((e) -> {
    		blurical.mainStage.close();
    	});
    	
    	_back.setOnMouseClicked((e) -> {        	
        	System.out.println("HelpPageController disposed.");
        	new Thread(sleeper).start();	//為了按鈕的漂亮動畫延遲1/4秒
    	});
    	
    	_indexPlus.setOnMouseClicked((e) -> {
        	
        	currentIndex += 1;
        	
        	if(currentIndex > helpPageSize - 1) {
        		currentIndex -= helpPageSize;
        	} else if (currentIndex < 0) {
        		currentIndex += helpPageSize;        		
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
        	
        	if(currentIndex > helpPageSize - 1) {
        		currentIndex -= helpPageSize;
        	} else if (currentIndex < 0) {
        		currentIndex += helpPageSize;        		
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
		
		createSubWidget();
  	  
  	}
    
}
