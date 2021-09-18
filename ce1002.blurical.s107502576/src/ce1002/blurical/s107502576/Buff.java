package ce1002.blurical.s107502576;

import java.util.Random;

public class Buff {
	
	public int type;	//buff����(1~3���q��, 4~8�]�I��)
	public int category;	//��buff���]�I����(0~5, -1�H�������άO���q��, �����������ܴN����@�M)
	public int finalCategory;		//�p�Gcategory�O�H���A�h�C��apply���n���@��
	public double value;	//��ִN�O�t��(0�O�H��)
	public double finalValue;		//�p�Gvalue�O�H���A�h�C��apply���n���@��

    public Buff(int type, int category, double value) {
        
    	this.type = type;
        this.value = value;
        this.category = category;
        this.finalCategory = 0;
        
    }
    
    public void apply(String companyName) {
        
    	//��@�H�������]�I
    	
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
            	blurical.newsList.add(1, new News(companyName + "�`����ܰ�", "�������ƥ�v�T�A" + companyName + "���`���" + (this.finalValue > 0 ? "�W�[" : "���") + "�F" + Math.round(Math.abs((int) this.finalValue*blurical.days*0.1)) + "��"));
                break;
            case 2:
            	target.pollution += Math.round(this.finalValue*blurical.days*0.1);
            	blurical.newsList.add(1, new News(companyName + "�y�������V�ܰ�", "�������ƥ�v�T�A" + companyName + "�����V" + (this.finalValue > 0 ? "�W�[" : "���") + "�F" + Math.round(Math.abs((int) this.finalValue*blurical.days*0.1))));
                break;
            case 3:
            	double tempReputation = target.reputation;
            	target.reputation += Math.round(this.finalValue*blurical.days*0.01);
            	target.reputation = Math.max(0, target.reputation);		//�L�o�t��
            	blurical.newsList.add(1, new News(companyName + "�W�n�ܰ�", "�������ƥ�v�T�A" + companyName + "���W�n" + (this.finalValue > 0 ? "�W�[" : "���") + "�F" + (target.reputation == 0 ? Math.round(tempReputation) : Math.round(Math.abs((int) this.finalValue*blurical.days*0.01)))));
                break;
            case 4:
            	blurical.newsList.add(1, new News(companyName + "�]�I�����ܰ�", "�������ƥ�v�T �A" + companyName + "��" + Technology.technologyCategory[finalCategory] + "�]�I���Ŵ�֤F" + Math.abs((int) this.finalValue)));
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			//only close technology(�ɵ��ө_��)
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {
            			for(int i = 0; i < this.finalValue; i++) {
            				target.closeTechnology(target.ownedTechnologyList.get(techName));
            			}
        			}
        			
        		}
        		break;
            case 5:        	
            	blurical.newsList.add(1, new News(companyName + "�]�I�����ܰ�", "�������ƥ�v�T �A" + companyName + "��" + Technology.technologyCategory[finalCategory] + "�]�I�����ܬ��쥻��" + Math.round(this.finalValue*100) + "%" ));	
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {
            			target.ownedTechnologyList.get(techName).cost *= this.finalValue;           	
            			target.ownedTechnologyList.get(techName).costBuff *= this.finalValue;        
            			//target.ownedTechnologyList.get(techName).printBuff();    				
        			}
        			
        		}
        		break;
            case 6:        		
            	blurical.newsList.add(1, new News(companyName + "�]�I���J�ܰ�", "�������ƥ�v�T �A" + companyName + "��" + Technology.technologyCategory[finalCategory] + "�]�I���J�ܬ��쥻��" + Math.round(this.finalValue*100) + "%" ));
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {
            			target.ownedTechnologyList.get(techName).dailyEarn *= this.finalValue;         
            			target.ownedTechnologyList.get(techName).dailyEarnBuff *= this.finalValue;      
            			//target.ownedTechnologyList.get(techName).printBuff();    				
        			}
        			
        		}
        		break;
            case 7:        		
            	blurical.newsList.add(1, new News(companyName + "�]�I�ҳy�������V�ܰ�", "�������ƥ�v�T �A" + companyName + "��" + Technology.technologyCategory[finalCategory] + "�]�I�ҳy�����V�ܬ��쥻��" + Math.round(this.finalValue*100) + "%" ));
        		for(String techName:target.ownedTechnologyList.keySet()) {
        			
        			if(target.ownedTechnologyList.get(techName).category == this.finalCategory) {      	
            			target.ownedTechnologyList.get(techName).dailyPollution *= this.finalValue;         
            			target.ownedTechnologyList.get(techName).dailyPollutionBuff *= this.finalValue;   
            			//target.ownedTechnologyList.get(techName).printBuff();     				
        			}
        			
        		}
        		break;
            case 8:       
            	blurical.newsList.add(1, new News(companyName + "�]�I�ҳy�����W�n�ܰ�", "�������ƥ�v�T �A" + companyName + "��" + Technology.technologyCategory[finalCategory] + "�]�I�ҳy���W�n�ܬ��쥻��" + Math.round(this.finalValue*100) + "%" )); 		
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
