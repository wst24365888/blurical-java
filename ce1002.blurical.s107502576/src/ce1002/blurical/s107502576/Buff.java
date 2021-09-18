package ce1002.blurical.s107502576;

import java.util.Random;

public class Buff {
	
	public int type;	//buff種類(1~3公司項, 4~8設施項)
	public int category;	//欲buff的設施種類(0~5, -1隨機種類或是公司項, 全部種類的話就都填一遍)
	public int finalCategory;		//如果category是隨機，則每次apply都要不一樣
	public double value;	//減少就是負的(0是隨機)
	public double finalValue;		//如果value是隨機，則每次apply都要不一樣

    public Buff(int type, int category, double value) {
        
    	this.type = type;
        this.value = value;
        this.category = category;
        this.finalCategory = 0;
        
    }
    
    public void apply(String companyName) {
        
    	//實作隨機種類設施
    	
        if(category == -1) {
            this.finalCategory = new Random().nextInt(6);
        } else {
        	this.finalCategory = this.category;
        }
        
        if(value == 0) {
            this.finalValue = new Random().nextInt(9) + 1;   	
        } else {
        	this.finalValue = this.value;
        }
    	
    	Company target = blurical.companyList.get(companyName);
    	
    	switch (type) {
    	
            case 1:
            	target.money += Math.round(this.finalValue*blurical.days*0.1);
            	blurical.newsList.add(1, new News(companyName + "總資金變動", "受到近期事件影響，" + companyName + "的總資金" + (this.finalValue > 0 ? "增加" : "減少") + "了" + Math.round(Math.abs((int) this.finalValue*blurical.days*0.1)) + "元"));
                break;
            case 2:
            	target.pollution += Math.round(this.finalValue*blurical.days*0.1);
            	blurical.newsList.add(1, new News(companyName + "造成的汙染變動", "受到近期事件影響，" + companyName + "的汙染" + (this.finalValue > 0 ? "增加" : "減少") + "了" + Math.round(Math.abs((int) this.finalValue*blurical.days*0.1))));
                break;
            case 3:
            	double tempReputation = target.reputation;
            	target.reputation += Math.round(this.finalValue*blurical.days*0.01);
            	target.reputation = Math.max(0, target.reputation);		//過濾負數
            	blurical.newsList.add(1, new News(companyName + "名聲變動", "受到近期事件影響，" + companyName + "的名聲" + (this.finalValue > 0 ? "增加" : "減少") + "了" + (target.reputation == 0 ? Math.round(tempReputation) : Math.round(Math.abs((int) this.finalValue*blurical.days*0.01)))));
                break;
            case 4:
            	blurical.newsList.add(1, new News(companyName + "設施成本變動", "受到近期事件影響 ，" + companyName + "的" + Technology.technologyCategory[finalCategory] + "設施等級減少了" + Math.abs((int) this.finalValue)));
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			//only close technology(升等太奇怪)
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {
            			for(int i = 0; i < this.finalValue; i++) {
            				target.closeTechnology(target.ownedTechnologyList.get(techName));
            			}
        			}
        			
        		}
        		break;
            case 5:        	
            	blurical.newsList.add(1, new News(companyName + "設施成本變動", "受到近期事件影響 ，" + companyName + "的" + Technology.technologyCategory[finalCategory] + "設施成本變為原本的" + Math.round(this.finalValue*100) + "%" ));	
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {
            			target.ownedTechnologyList.get(techName).cost *= this.finalValue;           	
            			target.ownedTechnologyList.get(techName).costBuff *= this.finalValue;        
            			//target.ownedTechnologyList.get(techName).printBuff();    				
        			}
        			
        		}
        		break;
            case 6:        		
            	blurical.newsList.add(1, new News(companyName + "設施收入變動", "受到近期事件影響 ，" + companyName + "的" + Technology.technologyCategory[finalCategory] + "設施收入變為原本的" + Math.round(this.finalValue*100) + "%" ));
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {
            			target.ownedTechnologyList.get(techName).dailyEarn *= this.finalValue;         
            			target.ownedTechnologyList.get(techName).dailyEarnBuff *= this.finalValue;      
            			//target.ownedTechnologyList.get(techName).printBuff();    				
        			}
        			
        		}
        		break;
            case 7:        		
            	blurical.newsList.add(1, new News(companyName + "設施所造成的汙染變動", "受到近期事件影響 ，" + companyName + "的" + Technology.technologyCategory[finalCategory] + "設施所造成汙染變為原本的" + Math.round(this.finalValue*100) + "%" ));
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {      	
            			target.ownedTechnologyList.get(techName).dailyPollution *= this.finalValue;         
            			target.ownedTechnologyList.get(techName).dailyPollutionBuff *= this.finalValue;   
            			//target.ownedTechnologyList.get(techName).printBuff();     				
        			}
        			
        		}
        		break;
            case 8:       
            	blurical.newsList.add(1, new News(companyName + "設施所造成的名聲變動", "受到近期事件影響 ，" + companyName + "的" + Technology.technologyCategory[finalCategory] + "設施所造成名聲變為原本的" + Math.round(this.finalValue*100) + "%" )); 		
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {
            			target.ownedTechnologyList.get(techName).reputation *= this.finalValue;           
            			target.ownedTechnologyList.get(techName).reputationBuff *= this.finalValue;     
            			//target.ownedTechnologyList.get(techName).printBuff();
        			}
        			
        		}
        		break;
        		
    	}
    	
    	target.setDailyEarn();
    	target.setDailyPollution();    	
    	target.setBuyPrice();
            	
    }

}
