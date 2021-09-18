package ce1002.blurical.s107502576;

import java.util.ArrayList;

import ce1002.blurical.s107502576.controllers.MainController;

public class Events {

	public String newsTitle;
	public String newsBody;	
	
	public ArrayList<Buff> buffList = new ArrayList<Buff>();
	
	public Events(String newsTitle, String newsBody, ArrayList<Buff> buffList) {
		this.newsTitle = newsTitle;
		this.newsBody = newsBody;
		this.buffList = buffList;
	}
	
	public void happen(String companyName) {
    	
    	MainController.pause = true;
    	blurical._helpPane.setVisible(true);
    	blurical._newNews.setVisible(true);
    	
		blurical.newsList.add(0, new News(this.newsTitle.replaceAll("\\$company", companyName), this.newsBody.replaceAll("\\$company", companyName)));
		
		for(Buff buff:buffList) {
			buff.apply(companyName);
		}
		
	}

}
