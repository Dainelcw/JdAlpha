package com.yxtt.hold;

import org.eclipse.jdt.internal.compiler.batch.Main;

import net.sf.json.JSONObject;

public class DataProcess {
	static String reback = null, welcome = "��ӭʹ�üѱ���������Զ���˵���˼ۡ����ˡ����߲��˵��������뿪�����˵���˳�", errorRequest = "�Ҳ�����������ʲô�������˵���˼ۡ����ˡ����˵���";
	static boolean shouldEndSession = false;
	static String priceTable="{\"vegetables\": {\"������\": \"1.99\",\"�ƹ�\": \"2.59\",\"�⽷\": \"1.99\", " + 
			"\"����\": \"1.29\", " + 
			"\"����\": \"1.59\", " + 
			"\"�ײ�\": \"1.29\", " + 
			"\"�۲�\": \"3.49\", " + 
			"\"�˻�\": \"3.49\", " + 
			"\"��޷\": \"4.99\", " + 
			"\"���ܲ�\": \"1.19\", " + 
			"\"���\": \"2.29\", " + 
			"\"����\": \"1.29\" " + 
			"}, " + 
			"\"milk\": { " + 
			"\"ţ��\": \"2.25\", " + 
			"\"����\": \"1.25\", " + 
			"\"������\": \"1.59\" " + 
			"}, " + 
			"\"meat\": { " + 
			"\"�廨��\": \"9.90\", " + 
			"\"ţ��\": \"39.9\", " + 
			"\"����\": \"9.9\", " + 
			"\"����\": \"3.79\", " + 
			"\"����\": \"11.9\"," + 
			"\"�Ź�\": \"15.9\"" + 
			"}," + 
			"\"fruits\": {" + 
			"\"ƻ��\": \"5.99\"," + 
			"\"��\": \"2.59\"," + 
			"\"����\": \"5.59\"," + 
			"\"����\": \"7.99\"," + 
			"\"�㽶\": \"2.99\"" + 
			"}" + 
			"}";
	
	public static String dataprocess(String param) {
		String requestType, requestIsNew;
		requestType = extractionValue(param, "request","type", "", 2);
		requestIsNew = extractionValue(param, "session","isNew", "", 2);
		if(requestType.equals("IntentRequest")) {
			switch(extractionValue(param, "request","intent", "name", 3)) {
				case "Add.Alpha.CancelIntent":
					reback = welcome;
				break;
				case "Alpha.HelpIntent":
					reback = "��ӭʹ�üѱ�����İ������ѱ�������һ�������ڷ����͵�����Ӧ�ã�����������Բ�ѯ�˼ۣ����˵������˵��Ȳ������Ͽ���һ�԰ɣ�";
					break;
				case "Alpha.CancelIntent":
					reback = "���˳����ڴ����´�ʹ�ã��ѱ��ټ�!";
					break;
				case "MainQuery":
					reback = MainQuery.MainQuery(extractionValue(param, "request","intent", "slots", 3));
					break;	
				case "Effect":
					reback = EffectFun(extractionValue(param, "request","intent", "slots", 3),param);
					System.out.println(reback);
					break;
				case "CountQuery":
					reback = CountQuery.CountQuery(extractionValue(param, "session","user", "userId", 3),extractionValue(param, "request","intent", "slots", 3));
					break;
				case "WriteCount":
					reback = WriteCount.WriteCount(extractionValue(param, "request","intent", "slots", 3),extractionValue(param, "session","user", "userId", 3));
					break;
			}
			System.out.println(extractionValue(param, "request","intent", "name", 3));
		}else if(requestType.equals("LaunchRequest")){
			if(requestIsNew.equals("true"))
				reback = welcome;
			else
				reback = errorRequest;
		}
		
		String backinfo = "{\"contexts\":{},\"directives\":[],\"response\":{\"output\":{\"type\":\"PlainText\",\"text\":\""+reback+"\"}},\"shouldEndSession\":"+shouldEndSession+",\"version\":\"1.0\"}";
		return backinfo;
	}
	
	
	private static String EffectFun(String slots, String param) {
		String effectName = extractionValue(slots, "myEffect", "value", "", 2);
		System.out.println(effectName);
		if(effectName.equals("�ҵ��˵�")) {
			return CountQuery.CountQuery(extractionValue(param, "session","user", "userId", 3),"null");//�Ȳ�ѯ���ݿ�Ȼ����з���
		}else if(effectName.equals("��Ҫ����")) {
			return "����˵�����ʲô���˶���Ǯ�������֪�����˹�����˵�˱�����";
		}else if(effectName.equals("���˼�")) {
			return "������1.99Ԫһ��ƹ�2.59Ԫһ�����1.29Ԫһ��ȣ������ֱ����ĳ�ֲ�Ʒ�ļ۸���ײ˵ļ۸����в˼۾���Դ�������ռ���������������죬��������ο��������Ե���Ϊ׼��";
		}else if(effectName.equals("������")) {
			return "�õģ��ڴ����´�ʹ�ã��ټ���";
		}else if(effectName.equals("�˱�����")) {
			return "���ǽ��������͹���Ϊ���ࣺ���֡�ѧϰ�����š����С�ʳ�ޣ������˵�������ֻ���40Ԫ";
		}else {
			return errorRequest;
		}
		//return extractionValue;
		
	}
	
	public static String extractionValue(String obj, String key1, String key2, String key3, int layer){
    	String outData = null;
    	//try {
    		if(layer==1){
        		JSONObject jsonObject = new JSONObject().fromObject(obj);
                Object data=jsonObject.get(key1);
        		outData = data.toString();
        	} else if (layer == 2){
    			JSONObject jsonObject = JSONObject.fromObject(obj);
                Object data=jsonObject.get(key1);
    			jsonObject = JSONObject.fromObject(data.toString());
                data=jsonObject.get(key2);
        		outData = data.toString();
        	} else if (layer == 3){
        		JSONObject jsonObject = new JSONObject().fromObject(obj);
                Object data=jsonObject.get(key1);
                jsonObject = new JSONObject().fromObject(data.toString());
                data=jsonObject.get(key2);
                jsonObject = new JSONObject().fromObject(data.toString());
                data=jsonObject.get(key3);
        		outData = data.toString();
        	}
    	//}catch (Exception e) {
		//	outData = "��ѽ��û���ҵ�����Ҫ�ģ����������ԡ�";
		//}
    	
    	return outData;
    }

}
