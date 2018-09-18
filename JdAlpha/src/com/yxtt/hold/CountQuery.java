package com.yxtt.hold;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CountQuery {
	// 定义输出日期格式
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static Date currentDate = new Date();
    
	public static String CountQuery(String userID, String slots) {
		String userId = userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1);
		String date, dateType, countType, reback = null;
		Count count = new Count();
		String thisDate = sdf.format(currentDate);
		System.out.println("表查询结果："+DataBaseCon.queryForm(userId));
		if(DataBaseCon.queryForm(userId)) {
			if(DataBaseCon.queryDate(userId, thisDate)) {
				
			}else {
				DataBaseCon.insertThisDate(userId, thisDate);
			}
			if(slots.equals("null")) {
				//DataBaseCon.queryCount(userId, thisDate);
				count = DataBaseCon.queryCount(userId, thisDate);
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "您今天还没记账，请先记账再进行查询。";
				else
					reback = "今天"+myFilter(count)+"您还可以查询本周账单或本月账单";
			}else {
			dateType = DataProcess.extractionValue(slots, "dataType", "value", "", 2);
			switch(dateType) {
			case "今天":
				count = DataBaseCon.queryCount(userId, thisDate);
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "您今天还没记账，请先记账再进行查询。";
				else
					reback = "今天"+myFilter(count)+"您还可以查询本周账单或本月账单";
				break;
			case "本周":
				count = searchThisCount(userId,"week");
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "您最近还没记账，请先记账再进行查询。";
				else
				reback = "您本周"+myFilter(count);
				break;
			case "本月":
				count = searchThisCount(userId,"month");
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "您最近还没记账，请先记账再进行查询。";
				else
					reback = "您本月"+myFilter(count);
				break;
			case "本年":
				break;
			}
		}
		}else {
			reback = "您还没记账，请先记账再进行查询。";
		}
		System.out.println("reback:"+reback);
		return reback;
	}
	
	private static String myFilter(Count count) {
		String entertainmentCount, studyCount, clothCount, travelCount, eatCount, sumCount, reback, str1, str2, str3,str4, str5;
		entertainmentCount = count.getEntainmentCount();
		studyCount = count.getStudyCount();
		clothCount = count.getClothCount();
		travelCount = count.getTravelCount();
		eatCount = count.getEatCount();
		sumCount = count.getSumCount();
		if(Integer.valueOf(entertainmentCount) == 0) {
			str1 = "";
		}else {
			str1 = "娱乐共消费" + entertainmentCount + "元；";
		}
		if(Integer.valueOf(studyCount) == 0) {
			str2 = "";
		}else {
			str2 = "学习共消费" + studyCount + "元；";
		}
		if(Integer.valueOf(clothCount) == 0) {
			str3 = "";
		}else {
			str3 = "衣着共消费" + clothCount + "元；";
		}
		if(Integer.valueOf(travelCount) == 0) {
			str4 = "";
		}else {
			str4 = "出行共消费" + travelCount + "元；";
		}	
		if(Integer.valueOf(eatCount) == 0) {
			str5 = "";
		}else {
			str5 = "食宿共消费" + eatCount + "元；";
		}
		reback = str1 + str2 + str3 + str4 + str5 + "共计" + sumCount + "元。";
		System.out.println("filter:"+reback);
		return reback;
		
	}
	
	
	private static Count searchThisCount(String userId,String type) {
		Count rebackCount = new Count();
		rebackCount.setEntainmentCount("0");
		rebackCount.setStudyCount("0");
		rebackCount.setClothCount("0");
		rebackCount.setEatCount("0");
		rebackCount.setTravelCount("0");
		rebackCount.setSumCount("0");
		
		Count tempCount = new Count();
		List<Date> days = null;
		if(type.equals("week"))
			days = dateToWeek(currentDate);
		else if(type.equals("month"))
			days = dateToMonth();
        for(Date date : days) {
            
            if(DataBaseCon.queryDate(userId, sdf.format(date))) {
            	System.out.println(sdf.format(date));
            	tempCount = DataBaseCon.queryCount(userId, sdf.format(date));
            	//System.out.println(Integer.valueOf(rebackCount.getSumCount()));
            	//System.out.println(tempCount.getSumCount());
            	rebackCount.setEntainmentCount(String.valueOf(Integer.valueOf(rebackCount.getEntainmentCount())+Integer.valueOf(tempCount.getEntainmentCount())));
            	rebackCount.setStudyCount(String.valueOf(Integer.valueOf(rebackCount.getStudyCount())+Integer.valueOf(tempCount.getStudyCount())));
            	rebackCount.setClothCount(String.valueOf(Integer.valueOf(rebackCount.getClothCount())+Integer.valueOf(tempCount.getClothCount())));
            	rebackCount.setEatCount(String.valueOf(Integer.valueOf(rebackCount.getEatCount())+Integer.valueOf(tempCount.getEatCount())));
            	rebackCount.setTravelCount(String.valueOf(Integer.valueOf(rebackCount.getTravelCount())+Integer.valueOf(tempCount.getTravelCount())));
            	rebackCount.setSumCount(String.valueOf(Integer.valueOf(rebackCount.getSumCount())+Integer.valueOf(tempCount.getSumCount())));
            }	
        }
		return rebackCount;
	}


	/**
     * 根据日期获得所在周的日期 
     * @param mdate
     * @return
     */
    @SuppressWarnings("deprecation")
    public static List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24*3600000;
        for(int a = 1; a <= 7; a++) {
            fdate = new Date();
            //fdate.setTime(fTime + (a * 24*3600000)); //一周从周日开始算，则使用此方式
            fdate.setTime(fTime + ((a-1) * 24*3600000)); //一周从周一开始算，则使用此方式
            list.add(a-1, fdate);
        }
        return list;
    }
    /**
     * 根据日期获得所在月的日期 
     * @param mdate
     * @return
     */
    @SuppressWarnings("deprecation")
    public static List<Date> dateToMonth() {
     // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        // 获取当月第一天和最后一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = sdf.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = sdf.format(cale.getTime());
        //System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
        Date dBegin = null, dEnd = null;
        try {
        	dBegin = sdf.parse(firstday);
        	dEnd = sdf.parse(lastday);
        } catch (ParseException e) {
        	e.printStackTrace();
        }
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
        /*for(int i=0;i<listDate.size();i++){
        	System.out.println(sdf.format(listDate.get(i)));
        }*/
        return listDate;
    }
    
    /**
    * 根据开始时间和结束时间返回时间段内的时间集合
    * 
    * @param beginDate
    * @param endDate
    * @return List
    */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
    List<Date> lDate = new ArrayList<Date>();
    lDate.add(beginDate);// 把开始时间加入集合
    Calendar cal = Calendar.getInstance();
    // 使用给定的 Date 设置此 Calendar 的时间
    cal.setTime(beginDate);
    boolean bContinue = true;
    while (bContinue) {
    	// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	// 测试此日期是否在指定日期之后
    	if (endDate.after(cal.getTime())) {
    		lDate.add(cal.getTime());
    	} else {
    		break;
    	}
    }
    lDate.add(endDate);// 把结束时间加入集合
    return lDate;
    }
}