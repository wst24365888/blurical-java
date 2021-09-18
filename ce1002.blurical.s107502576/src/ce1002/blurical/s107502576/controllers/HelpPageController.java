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
			
			_helpTitle.setText("�����");
			_relation.setVisible(true);
			
		} else if(currentIndex == 1) {
			
			_helpTitle.setText("Q&A");
			_relation.setVisible(false);

			_helpText.setText("Q1. �C�����ؼ�?\nA1. ���ʩҦ����q�C\n\nQ2. �p���ȿ�?\nA2. �ɯŤw�����]�I�A�H�ۤѼƨ��ǳ]�I�N�|���A�ȿ��C���L�]�n�P�ɤp�ߦìV�W�[�����D�@�I\n\nQ3. �ìV�W�[�|���?\nA3. �p�G�ìV�W�[��W�w�q�A�|�����A���`����@���g�@�C�A��h���ܥi��|�y���a�y�����@�I");
			
		} else if(currentIndex == 2) {
			
			_helpTitle.setText("Q&A");
			_relation.setVisible(false);
			
			_helpText.setText("Q4. �骺����O����?\nA4. �a�y����(�Ҧ����q���V�`�M�W�L�C������*100�U)���`�겣(�`�N���O�`����I)�p��s�ɺ�O�C�����ѡC\n\nQ5. �p����w�֦����]�I?\nA5. �b�ɯų]�I�������A�i�ɯŪ��N�O�A�w�֦����@�I\n\nQ6.�p������L�]�I?\nA6. ���ʨ�L���q�A�N�|�o��Ӥ��q�w�֦����]�I�C�p�G�A�٨S���ӳ]�I�A�|�N�����A�p�G�A�w�֦��ӳ]�I�A�h��̤j���űN�|���ɡC");
			
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
        	new Thread(sleeper).start();	//���F���s���}�G�ʵe����1/4��
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
