package com.yxtt.hold;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CountQuery {
	// ����������ڸ�ʽ
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static Date currentDate = new Date();
    
	public static String CountQuery(String userID, String slots) {
		String userId = userID.substring(userID.indexOf(".",userID.indexOf(".")+1 )+1);
		String date, dateType, countType, reback = null;
		Count count = new Count();
		String thisDate = sdf.format(currentDate);
		System.out.println("���ѯ�����"+DataBaseCon.queryForm(userId));
		if(DataBaseCon.queryForm(userId)) {
			if(DataBaseCon.queryDate(userId, thisDate)) {
				
			}else {
				DataBaseCon.insertThisDate(userId, thisDate);
			}
			if(slots.equals("null")) {
				//DataBaseCon.queryCount(userId, thisDate);
				count = DataBaseCon.queryCount(userId, thisDate);
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "�����컹û���ˣ����ȼ����ٽ��в�ѯ��";
				else
					reback = "����"+myFilter(count)+"�������Բ�ѯ�����˵������˵�";
			}else {
			dateType = DataProcess.extractionValue(slots, "dataType", "value", "", 2);
			switch(dateType) {
			case "����":
				count = DataBaseCon.queryCount(userId, thisDate);
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "�����컹û���ˣ����ȼ����ٽ��в�ѯ��";
				else
					reback = "����"+myFilter(count)+"�������Բ�ѯ�����˵������˵�";
				break;
			case "����":
				count = searchThisCount(userId,"week");
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "�������û���ˣ����ȼ����ٽ��в�ѯ��";
				else
				reback = "������"+myFilter(count);
				break;
			case "����":
				count = searchThisCount(userId,"month");
				if(Integer.valueOf(count.getSumCount())==0)
					reback = "�������û���ˣ����ȼ����ٽ��в�ѯ��";
				else
					reback = "������"+myFilter(count);
				break;
			case "����":
				break;
			}
		}
		}else {
			reback = "����û���ˣ����ȼ����ٽ��в�ѯ��";
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
			str1 = "���ֹ�����" + entertainmentCount + "Ԫ��";
		}
		if(Integer.valueOf(studyCount) == 0) {
			str2 = "";
		}else {
			str2 = "ѧϰ������" + studyCount + "Ԫ��";
		}
		if(Integer.valueOf(clothCount) == 0) {
			str3 = "";
		}else {
			str3 = "���Ź�����" + clothCount + "Ԫ��";
		}
		if(Integer.valueOf(travelCount) == 0) {
			str4 = "";
		}else {
			str4 = "���й�����" + travelCount + "Ԫ��";
		}	
		if(Integer.valueOf(eatCount) == 0) {
			str5 = "";
		}else {
			str5 = "ʳ�޹�����" + eatCount + "Ԫ��";
		}
		reback = str1 + str2 + str3 + str4 + str5 + "����" + sumCount + "Ԫ��";
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
     * �������ڻ�������ܵ����� 
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
            //fdate.setTime(fTime + (a * 24*3600000)); //һ�ܴ����տ�ʼ�㣬��ʹ�ô˷�ʽ
            fdate.setTime(fTime + ((a-1) * 24*3600000)); //һ�ܴ���һ��ʼ�㣬��ʹ�ô˷�ʽ
            list.add(a-1, fdate);
        }
        return list;
    }
    /**
     * �������ڻ�������µ����� 
     * @param mdate
     * @return
     */
    @SuppressWarnings("deprecation")
    public static List<Date> dateToMonth() {
     // ��ȡ��ǰ��ݡ��·ݡ�����
        Calendar cale = null;
        cale = Calendar.getInstance();
        // ��ȡ���µ�һ������һ��
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // ��ȡǰ�µĵ�һ��
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = sdf.format(cale.getTime());
        // ��ȡǰ�µ����һ��
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = sdf.format(cale.getTime());
        //System.out.println("���µ�һ������һ��ֱ��� �� " + firstday + " and " + lastday);
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
    * ���ݿ�ʼʱ��ͽ���ʱ�䷵��ʱ����ڵ�ʱ�伯��
    * 
    * @param beginDate
    * @param endDate
    * @return List
    */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
    List<Date> lDate = new ArrayList<Date>();
    lDate.add(beginDate);// �ѿ�ʼʱ����뼯��
    Calendar cal = Calendar.getInstance();
    // ʹ�ø����� Date ���ô� Calendar ��ʱ��
    cal.setTime(beginDate);
    boolean bContinue = true;
    while (bContinue) {
    	// ���������Ĺ���Ϊ�����������ֶ���ӻ��ȥָ����ʱ����
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	// ���Դ������Ƿ���ָ������֮��
    	if (endDate.after(cal.getTime())) {
    		lDate.add(cal.getTime());
    	} else {
    		break;
    	}
    }
    lDate.add(endDate);// �ѽ���ʱ����뼯��
    return lDate;
    }
}