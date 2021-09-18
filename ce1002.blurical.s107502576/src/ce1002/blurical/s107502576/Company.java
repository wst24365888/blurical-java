package ce1002.blurical.s107502576;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ce1002.blurical.s107502576.controllers.MainController;

public class Company {

    public String name;
    
    public int money;
    public int dailyEarn;
    public int pollution;
    public int dailyPollution;
    public int reputation;
    public int buyPrice;
    
    public int defaultDailyEarn;
    public int defaultDailyPollution;
    
    public boolean isBought;

    public ArrayList<String> ownedTechnologyName = new ArrayList<String>();

    public ArrayList<String> ownedCompanyName = new ArrayList<String>();
    
	public HashMap<String, Technology> ownedTechnologyList = new HashMap<String, Technology>();	//��µ��禡�ƭȭȥ�

    public Company(String name, int money, int dailyEarn, int pollution, int dailyPollution, int reputation, HashMap<String, Technology> ownedTechnologyList) {
    	
        this.name = name;
        this.money = money;
        this.dailyEarn = dailyEarn;
        this.pollution = pollution;
        this.dailyPollution = dailyPollution;
        this.reputation = reputation;
        this.buyPrice = this.money;
        this.ownedTechnologyList = ownedTechnologyList;
        
        this.defaultDailyEarn = this.dailyEarn;
        this.defaultDailyPollution = this.dailyPollution;
        
        this.isBought = false;
        
    }
    
    public void grow() {
    	
        if (this.isBought) return;

        //���q�H���R��ިƥ�
        
        if (!this.name.equals(blurical.companyList.keySet().toArray()[0]) && new Random().nextInt(100) < blurical.level) {
        	
        	int randTech = new Random().nextInt(ownedTechnologyName.size());
        	
        	if(this.ownedTechnologyList.get(ownedTechnologyName.get(randTech)).unlocked) {
            	buyTechnology(this.ownedTechnologyList.get(ownedTechnologyName.get(randTech)));        		
        	}
        	
        }
        
        //�^�X����

        this.dailyEarn += this.reputation / 200;
        this.defaultDailyEarn += this.reputation / 200;
        
        this.pollution += this.dailyPollution;
        
        if (this.pollution > blurical.maxPollution) {            
            this.money -= 100 * (this.pollution - blurical.maxPollution) / blurical.maxPollution;	//�p�G���V�j��W�w�W���h�������
        }
        
        this.money += this.dailyEarn;
        
        if(blurical.days % 5 == 0) {
            this.reputation += 1;        	
        }
        
        //���q�H���D�J��o�ƥ�
        
        if (new Random().nextInt(1000) < 1 + blurical.year - 2019) {
        	
        	int randEvent = new Random().nextInt(blurical.eventsList.size());
        	
        	blurical.eventsList.get(randEvent).happen(this.name);
        	
        }
        
        //����겣

        setBuyPrice();
        
        //���vĲ�o���q����
        
        if (this.money <= 0 && !this.name.equals(blurical.companyList.keySet().toArray()[0])) {
        	
        	//�}���@�w�n�Q�R��
        	for(var companyName:blurical.companyList.keySet()) {
            	
            	if(companyName.equals(this.name)) {
            		continue;
            	}
            	
            	Company target = blurical.companyList.get(companyName);
            	
                if (!target.isBought && !target.name.equals(blurical.companyList.keySet().toArray()[0])) {
                	
                	target.money += this.buyPrice;
                	target.buyCompany(this);   //*this�O�ۤv�o�����q
                	
                	MainController.pause = true;
                	blurical._helpPane.setVisible(true);
                	blurical._newNews.setVisible(true);
                	blurical.newsList.add(0, new News("���q���ʨƥ�", target.name + "�R�U�F" + this.name + "�C"));
                	
                    break;
                }
                
            }
        	
        } else if (this.reputation >= 1800 && !this.name.equals(blurical.companyList.keySet().toArray()[0])) {
        	
            for(var companyName:blurical.companyList.keySet()) {
            	
            	if(companyName.equals(this.name)) {
            		continue;
            	}
            	
                int rd = new Random().nextInt(100); 
            	
            	Company target = blurical.companyList.get(companyName);
            	
                if (!target.isBought && !target.name.equals(blurical.companyList.keySet().toArray()[0])) {
                	if((double)rd < (double)target.reputation/1800 && target.money > this.buyPrice*3) {
                		
	                	target.buyCompany(this);   //*this�O�ۤv�o�����q
	                	
	                	MainController.pause = true;
	                	blurical._helpPane.setVisible(true);
	                	blurical._newNews.setVisible(true);
	                	blurical.newsList.add(0, new News("���q���ʨƥ�", target.name + "�R�U�F" + this.name + "�C"));
	                	
	                    return;                		
	                    
                	}
                }
                
            }
        }
        
    }
    
    public void setBuyPrice() {
    	
        //��l���ʶR���B
        this.buyPrice = this.money;
        
        for(var techName:this.ownedTechnologyName)
        {
            this.buyPrice += this.ownedTechnologyList.get(techName).level * this.ownedTechnologyList.get(techName).cost * this.ownedTechnologyList.get(techName).costBuff * 3 / 10;
        }
        
    }
    
    public void setDailyEarn() {
    	
        //��l�Ƥ馬�J
        this.dailyEarn = this.defaultDailyEarn;
        
        for(var techName:this.ownedTechnologyName)
        {
            this.dailyEarn += this.ownedTechnologyList.get(techName).level * this.ownedTechnologyList.get(techName).defaultDailyEarn * this.ownedTechnologyList.get(techName).dailyEarnBuff;
        }
        
    }
    
    public void setDailyPollution() {
    	
        //��l�Ƥ馾�V
        this.dailyPollution = this.defaultDailyPollution;
        
        for(var techName:this.ownedTechnologyName)
        {
            this.dailyPollution += this.ownedTechnologyList.get(techName).level * this.ownedTechnologyList.get(techName).defaultDailyPollution * this.ownedTechnologyList.get(techName).dailyPollutionBuff;
        }
        
    }

    public void buyTechnology(Technology technology) {
    	
    	//System.out.println(this.name + " has bought " + technology.name + ".");
    	
        if (technology.level < technology.maxLevel && this.money >= technology.cost) {
        	
            this.money -= technology.cost*technology.costBuff;
            this.dailyEarn += technology.defaultDailyEarn*technology.dailyEarnBuff;
            this.dailyPollution += technology.defaultDailyPollution*technology.dailyPollutionBuff;
            this.reputation += technology.defaultReputation*technology.reputationBuff;
            
            technology.level += 1;
            technology.cost *= 1.1*technology.costBuff;
            technology.dailyEarn += technology.defaultDailyEarn*technology.dailyEarnBuff;
            technology.dailyPollution += technology.defaultDailyPollution*technology.dailyPollutionBuff;
            technology.reputation += technology.defaultReputation*technology.reputationBuff;
        }
        
        this.setBuyPrice();
        
    }
    
    public void closeTechnology(Technology technology) {
    	
    	//System.out.println(this.name + " has bought " + technology.name + ".");
    	
        if (technology.level > 0) {
        	
            this.dailyEarn -= technology.defaultDailyEarn*technology.dailyEarnBuff;
            this.dailyPollution -= technology.defaultDailyPollution*technology.dailyPollutionBuff;
            this.reputation -= technology.defaultReputation*technology.reputationBuff;
            
            technology.level -= 1;
            technology.cost /= 1.1*technology.costBuff;
            technology.dailyEarn -= technology.defaultDailyEarn*technology.dailyEarnBuff;
            technology.dailyPollution -= technology.defaultDailyPollution*technology.dailyPollutionBuff;
            technology.reputation -= technology.defaultReputation*technology.reputationBuff;        
        }
        
        this.setBuyPrice();
        
    }
    
    public void buyCompany(Company company) {
    	
    	//System.out.println(this.name + " has bought " + company.name + ".");
    	
    	company.isBought = true;
    	
    	blurical.companyButtonData.get(company.name).setOpacity(0.5);

        this.money -= company.buyPrice;
        this.dailyEarn += company.dailyEarn;
        this.pollution += company.pollution;
        this.dailyPollution += company.dailyPollution;
        this.reputation += company.reputation;
        
        this.defaultDailyEarn += company.defaultDailyEarn;
        this.defaultDailyPollution += company.defaultDailyPollution;
        
        company.buyPrice = 0;
        company.dailyEarn = 0;
        company.pollution = 0;
        company.dailyPollution = 0;
        company.reputation = 0;
        
        ArrayList<String> additionalTechNames = new ArrayList<String>();
        
        //�e�@�����q��technology buff�|���Ʈ���
        
        for(String techName:company.ownedTechnologyName) {
        	
        	if(this.ownedTechnologyName.contains(techName)) {
        		
        		Technology target = this.ownedTechnologyList.get(techName);
        		
        		target.maxLevel += company.ownedTechnologyList.get(techName).maxLevel;
        		
        		//�������buyTechnology
        		
        		for(int i = 0; i < company.ownedTechnologyList.get(techName).level; i++) { 
        			target.level += 1;
        			target.cost *= 1.1;
        			target.dailyEarn += target.defaultDailyEarn;
        			target.dailyPollution += target.defaultDailyPollution;
        			target.reputation += target.defaultReputation;           			
        		}
        		
        	} else {
        		
        		additionalTechNames.add(techName);	//�p�G�����[�bthis.ownedTechnologyName���ܷ|�ɭP�M���ɪ��ץX���D(�W�[�F)
        		
        		Technology target = this.ownedTechnologyList.get(techName);
        		
        		target.unlocked = true;
        		
        		target.maxLevel += company.ownedTechnologyList.get(techName).maxLevel - 10;
        		
        		//�������buyTechnology
        		
        		for(int i = 0; i < company.ownedTechnologyList.get(techName).level; i++) { 
        			target.level += 1;
        			target.cost *= 1.1;
        			target.dailyEarn += target.defaultDailyEarn;
        			target.dailyPollution += target.defaultDailyPollution;
        			target.reputation += target.defaultReputation;           			
        		}
        		
        	}
        	
        }
        
        this.ownedTechnologyName.addAll(additionalTechNames);
        
        this.setDailyEarn();
        this.setDailyPollution();        
        this.setBuyPrice();
    	
    }

}
