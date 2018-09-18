package com.yxtt.hold;

import java.util.Calendar;

public class WriteCount {
	public static String WriteCount(String slots, String userID) {
		String days,countType, userId;
		int money;
		
		if(DataProcess.extractionValue(slots, "countType","matched", "", 2).equals("false")) {
			return "暂时不支持记录这个分类，我们将消费类型归纳为五类：娱乐、学习、衣着、出行、食宿，你可以说今天娱乐花了40元。";
		}else if(DataProcess.extractionValue(slots, "Days","matched", "", 2).equals("false")&&DataProcess.extractionValue(slots, "countType","matched", "", 2).equals("true")){
			countType = DataProcess.extractionValue(slots, "countType","value", "", 2);
			money = Integer.valueOf(DataProcess.extractionValue(slots, "number","value", "", 2));
			userId = userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1);
			System.out.println("days:null"+",countType:"+countType+",money:"+money+".");
			//System.out.println(userID.substring(userID.indexOf(".",userID.indexOf(".")+1)+1));
			//System.out.println(DataBaseCon.queryUserID(userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1)));
			// 获取当前年份、月份、日期  
	        Calendar cale = null; 
	        String thisDate, countDate;//当前日期与记账日期
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
				case "娱乐":
					countType = "entertainment";
					break;
				case "学习":
					countType = "study";
					break;
				case "衣着":
					countType = "cloth";
					break;
				case "出行":
					countType = "travel";
					break;
				case "食宿":
					countType = "eat";
					break;
				}
	      	DataBaseCon.queryUserID(userId);
			if(DataBaseCon.queryUserID(userId)) {
				DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
			}else {
				if(DataBaseCon.insertData("userlist", "userId", userId)) {
					System.out.println("用户信息插入成功！下面即将创建用户表。。。");
					if(DataBaseCon.newTable(userId))
						System.out.println("用户表创建完成，下面插入数据。。。。");
					DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
				}
			}
			return "已为您记录到账单。";
		}else {
			days = DataProcess.extractionValue(slots, "Days","value", "", 2);
			countType = DataProcess.extractionValue(slots, "countType","value", "", 2);
			money = Integer.valueOf(DataProcess.extractionValue(slots, "number","value", "", 2));
			userId = userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1);
			System.out.println("days:"+days+",countType:"+countType+",money:"+money+".");
			//System.out.println(userID.substring(userID.indexOf(".",userID.indexOf(".")+1)+1));
			//System.out.println(DataBaseCon.queryUserID(userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1)));
			// 获取当前年份、月份、日期  
	        Calendar cale = null; 
	        String thisDate, countDate;//当前日期与记账日期
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
	      //词语分析
	      		switch(days) {
	      		case "前天":
	      			day = day-2;
	      			break;
	      		case "昨天":
	      			day = day-1;
	      			break;
	      		case "今天":
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
				case "娱乐":
					countType = "entertainment";
					break;
				case "学习":
					countType = "study";
					break;
				case "衣着":
					countType = "cloth";
					break;
				case "出行":
					countType = "travel";
					break;
				case "食宿":
					countType = "eat";
					break;
				}
			if(DataBaseCon.queryUserID(userId)) {
				DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
			}else {
				if(DataBaseCon.insertData("userlist", "userId", userId)) {
					System.out.println("用户信息插入成功！下面即将创建用户表。。。");
					if(DataBaseCon.newTable(userId))
						System.out.println("用户表创建完成，下面插入数据。。。。");
					DataBaseCon.insertCount(countDate, countType, money, thisDate, userId);
				}
			}
			return "已为您记录到账单。";
		}
	}
}
