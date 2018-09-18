package com.yxtt.hold;

public class MainQuery {
	public static String MainQuery(String slots) {
		
		String meatMatch,vegetableMatch,milkMatch,meatF,vegetableF,milkF,reBack=null;
		meatMatch = DataProcess.extractionValue(slots,"Meats","matched","",2);
		vegetableMatch = DataProcess.extractionValue(slots,"Vegetable","matched","",2);
		milkMatch = DataProcess.extractionValue(slots,"Milk","matched","",2);
		
		
		if(meatMatch.equals("true")) {
			meatF = DataProcess.extractionValue(slots,"Meats","value","",2);
			reBack = meatF +"的单价为"+DataBaseCon.queryOne("meatprice", "price", meatF, "name")+"元！";
		}else if(vegetableMatch.equals("true")) {
			vegetableF = DataProcess.extractionValue(slots,"Vegetable","value","",2);
			reBack = vegetableF +"的单价为"+DataBaseCon.queryOne("vegetableprice", "price", vegetableF, "name")+"元";
		}else if(milkMatch.equals("true")) {
			milkF = DataProcess.extractionValue(slots,"Milk","value","",2);
			reBack = milkF +"的单价为"+DataBaseCon.queryOne("milkprice", "price", milkF, "name")+"元！";
		}
		
		if(meatMatch.equals("false")&&vegetableMatch.equals("false")&&milkMatch.equals("false")) {
			/*meatF = DataProcess.extractionValue(slots,"Meats","value","",2);
			if(meatF!="Meats") {
				reBack = "您所查询的"+meatF+"还未收录，请换一个再问。";
			}
			vegetableF = DataProcess.extractionValue(slots,"Vegetable","value","",2);
			if(vegetableF!="Vegetable") {
				reBack = "您所查询的"+vegetableF+"还未收录，请换一个再问。";
			}*/
			milkF = DataProcess.extractionValue(slots,"Milk","value","",2);
			if(milkF!="Milk") {
				reBack = "抱歉，您所查询的"+milkF+"还未收录，我们会尽快收录。";
			}
		}
		return reBack;
		
	}
}
