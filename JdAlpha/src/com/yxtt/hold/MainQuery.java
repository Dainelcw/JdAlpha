package com.yxtt.hold;

public class MainQuery {
	public static String MainQuery(String slots) {
		
		String meatMatch,vegetableMatch,milkMatch,meatF,vegetableF,milkF,reBack=null;
		meatMatch = DataProcess.extractionValue(slots,"Meats","matched","",2);
		vegetableMatch = DataProcess.extractionValue(slots,"Vegetable","matched","",2);
		milkMatch = DataProcess.extractionValue(slots,"Milk","matched","",2);
		
		
		if(meatMatch.equals("true")) {
			meatF = DataProcess.extractionValue(slots,"Meats","value","",2);
			reBack = meatF +"�ĵ���Ϊ"+DataBaseCon.queryOne("meatprice", "price", meatF, "name")+"Ԫ��";
		}else if(vegetableMatch.equals("true")) {
			vegetableF = DataProcess.extractionValue(slots,"Vegetable","value","",2);
			reBack = vegetableF +"�ĵ���Ϊ"+DataBaseCon.queryOne("vegetableprice", "price", vegetableF, "name")+"Ԫ";
		}else if(milkMatch.equals("true")) {
			milkF = DataProcess.extractionValue(slots,"Milk","value","",2);
			reBack = milkF +"�ĵ���Ϊ"+DataBaseCon.queryOne("milkprice", "price", milkF, "name")+"Ԫ��";
		}
		
		if(meatMatch.equals("false")&&vegetableMatch.equals("false")&&milkMatch.equals("false")) {
			/*meatF = DataProcess.extractionValue(slots,"Meats","value","",2);
			if(meatF!="Meats") {
				reBack = "������ѯ��"+meatF+"��δ��¼���뻻һ�����ʡ�";
			}
			vegetableF = DataProcess.extractionValue(slots,"Vegetable","value","",2);
			if(vegetableF!="Vegetable") {
				reBack = "������ѯ��"+vegetableF+"��δ��¼���뻻һ�����ʡ�";
			}*/
			milkF = DataProcess.extractionValue(slots,"Milk","value","",2);
			if(milkF!="Milk") {
				reBack = "��Ǹ��������ѯ��"+milkF+"��δ��¼�����ǻᾡ����¼��";
			}
		}
		return reBack;
		
	}
}
