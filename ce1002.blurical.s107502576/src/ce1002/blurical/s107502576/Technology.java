package ce1002.blurical.s107502576;

public class Technology {

    public String name;
    public String categoryName;
    
    public int level;
    public int maxLevel;    
    public int category;
    
    public int dailyEarn;
    public int dailyPollution;
    public int reputation;
    public int cost;
    
    public int defaultDailyEarn;
    public int defaultDailyPollution;
    public int defaultReputation;
    public int defaultCost;
    
    public double dailyEarnBuff;
    public double dailyPollutionBuff;
    public double reputationBuff;
    public double costBuff;
    
    public boolean unlocked;

    public static String[] technologyCategory = { "能源", "工業", "高科技", "綠色產業", "農林漁牧", "服務業" };

    public Technology(String name, int level, int dailyEarn, int dailyPollution, int reputation, int cost, int category) {
        
    	this.name = name;
    	
        this.level = level;
        this.maxLevel = 10;
        this.category = category;
        
        this.dailyEarn = dailyEarn;
        this.dailyPollution = dailyPollution;
        this.reputation = reputation;
        this.cost = cost;
        
        this.defaultDailyEarn = dailyEarn;
        this.defaultDailyPollution = dailyPollution;
        this.defaultReputation = reputation;
        this.defaultCost = cost;
        
        this.categoryName = technologyCategory[category];
        
        this.costBuff = 1;
        this.dailyEarnBuff = 1;
        this.dailyPollutionBuff = 1;
        this.reputationBuff = 1;
        
        this.unlocked = false;
        
    };
    
    public void printBuff() {
    	System.out.println(this.name + "\t" + this.costBuff + ", " + this.dailyEarnBuff + ", " + this.dailyPollutionBuff + ", " + this.reputationBuff);
    }

}
