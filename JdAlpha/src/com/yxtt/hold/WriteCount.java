package com.yxtt.hold;

import java.util.Calendar;

public class WriteCount {
	public static String WriteCount(String slots, String userID) {
		String days,countType, userId;
		int money;
		
		if(DataProcess.extractionValue(slots, "countType","matched", "", 2).equals("false")) {
			return "��ʱ��֧�ּ�¼������࣬���ǽ��������͹���Ϊ���ࣺ���֡�ѧϰ�����š����С�ʳ�ޣ������˵�������ֻ���40Ԫ��";
		}else if(DataProcess.extractionValue(slots, "Days","matched", "", 2).equals("false")&&DataProcess.extractionValue(slots, "countType","matched", "", 2).equals("true")){
			countType = DataProcess.extractionValue(slots, "countType","value", "", 2);
			money = Integer.valueOf(DataProcess.extractionValue(slots, "number","value", "", 2));
			userId = userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1);
			System.out.println("days:null"+",countType:"+countType+",money:"+money+".");
			//System.out.println(userID.substring(userID.indexOf(".",userID.indexOf(".")+1)+1));
			//System.out.println(DataBaseCon.queryUserID(userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1)));
			// ��ȡ��ǰ��ݡ��·ݡ�����  
	        Calendar cale = null; 
	        String thisDate, countDate;//��ǰ�������������
	        cale = Calendar.getInstance();  
	        int year = cale.get(Calendar.YEAR);  
	        int month = cale.get(Calendar.MONTH) + 1; 
	        int day = cale.get(Calendar.DATE); 
	        if(month<10) {
	        	thisDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(day);
	        	if(day<10)
	        		thisDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(day);
	        }else {
	        	thisDate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
	        	if(day<10)
	        		thisDate = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(day);
	        }
	      		if(month<10) {
	      			countDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(day);
	            	if(day<10)
	            		countDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(day);
	            }else {
	            	countDate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
	            	if(day<10)
	            		countDate = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(day);
	            }
	      		switch (countType) {
				case "����":
					countType = "entertainment";
					break;
				case "ѧϰ":
					countType = "study";
					break;
				case "����":
					countType = "cloth";
					break;
				case "����":
					countType = "travel";
					break;
				case "ʳ��":
					countType = "eat";
					break;
				}
	      	DataBaseCon.queryUserID(userId);
			if(DataBaseCon.queryUserID(userId)) {
				DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
			}else {
				if(DataBaseCon.insertData("userlist", "userId", userId)) {
					System.out.println("�û���Ϣ����ɹ������漴�������û�������");
					if(DataBaseCon.newTable(userId))
						System.out.println("�û�������ɣ�����������ݡ�������");
					DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
				}
			}
			return "��Ϊ����¼���˵���";
		}else {
			days = DataProcess.extractionValue(slots, "Days","value", "", 2);
			countType = DataProcess.extractionValue(slots, "countType","value", "", 2);
			money = Integer.valueOf(DataProcess.extractionValue(slots, "number","value", "", 2));
			userId = userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1);
			System.out.println("days:"+days+",countType:"+countType+",money:"+money+".");
			//System.out.println(userID.substring(userID.indexOf(".",userID.indexOf(".")+1)+1));
			//System.out.println(DataBaseCon.queryUserID(userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1)));
			// ��ȡ��ǰ��ݡ��·ݡ�����  
	        Calendar cale = null; 
	        String thisDate, countDate;//��ǰ�������������
	        cale = Calendar.getInstance();  
	        int year = cale.get(Calendar.YEAR);  
	        int month = cale.get(Calendar.MONTH) + 1; 
	        int day = cale.get(Calendar.DATE); 
	        if(month<10) {
	        	thisDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(day);
	        	if(day<10)
	        		thisDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(day);
	        }else {
	        	thisDate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
	        	if(day<10)
	        		thisDate = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(day);
	        }
	      //�������
	      		switch(days) {
	      		case "ǰ��":
	      			day = day-2;
	      			break;
	      		case "����":
	      			day = day-1;
	      			break;
	      		case "����":
	      			break;
	      		
	      		}
	      		if(month<10) {
	      			countDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(day);
	            	if(day<10)
	            		countDate = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(day);
	            }else {
	            	countDate = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
	            	if(day<10)
	            		countDate = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(day);
	            }
	      		switch (countType) {
				case "����":
					countType = "entertainment";
					break;
				case "ѧϰ":
					countType = "study";
					break;
				case "����":
					countType = "cloth";
					break;
				case "����":
					countType = "travel";
					break;
				case "ʳ��":
					countType = "eat";
					break;
				}
			if(DataBaseCon.queryUserID(userId)) {
				DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
			}else {
				if(DataBaseCon.insertData("userlist", "userId", userId)) {
					System.out.println("�û���Ϣ����ɹ������漴�������û�������");
					if(DataBaseCon.newTable(userId))
						System.out.println("�û�������ɣ�����������ݡ�������");
					DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
				}
			}
			return "��Ϊ����¼���˵���";
		}
	}
}
