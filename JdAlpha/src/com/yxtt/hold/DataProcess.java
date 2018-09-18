package com.yxtt.hold;

import org.eclipse.jdt.internal.compiler.batch.Main;

import net.sf.json.JSONObject;

public class DataProcess {
	static String reback = null, welcome = "欢迎使用佳宝服务，你可以对我说：菜价、记账、或者查账单，若想离开请对我说，退出", errorRequest = "我不明白你想做什么，你可以说报菜价、记账、查账单等";
	static boolean shouldEndSession = false;
	static String priceTable="{\"vegetables\": {\"西红柿\": \"1.99\",\"黄瓜\": \"2.59\",\"尖椒\": \"1.99\", " + 
			"\"土豆\": \"1.29\", " + 
			"\"茄子\": \"1.59\", " + 
			"\"白菜\": \"1.29\", " + 
			"\"芹菜\": \"3.49\", " + 
			"\"菜花\": \"3.49\", " + 
			"\"蒜薹\": \"4.99\", " + 
			"\"胡萝卜\": \"1.19\", " + 
			"\"大葱\": \"2.29\", " + 
			"\"甘蓝\": \"1.29\" " + 
			"}, " + 
			"\"milk\": { " + 
			"\"牛奶\": \"2.25\", " + 
			"\"豆奶\": \"1.25\", " + 
			"\"花生奶\": \"1.59\" " + 
			"}, " + 
			"\"meat\": { " + 
			"\"五花肉\": \"9.90\", " + 
			"\"牛肉\": \"39.9\", " + 
			"\"鸡肉\": \"9.9\", " + 
			"\"鸡蛋\": \"3.79\", " + 
			"\"猪肉\": \"11.9\"," + 
			"\"排骨\": \"15.9\"" + 
			"}," + 
			"\"fruits\": {" + 
			"\"苹果\": \"5.99\"," + 
			"\"梨\": \"2.59\"," + 
			"\"柑橘\": \"5.59\"," + 
			"\"葡萄\": \"7.99\"," + 
			"\"香蕉\": \"2.99\"" + 
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
					reback = "欢迎使用佳宝服务的帮助，佳宝服务是一款致力于服务型的语音应用，在这里你可以查询菜价，记账单，查账单等操作，赶快试一试吧！";
					break;
				case "Alpha.CancelIntent":
					reback = "已退出，期待您下次使用，佳宝再见!";
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
		if(effectName.equals("我的账单")) {
			return CountQuery.CountQuery(extractionValue(param, "session","user", "userId", 3),"null");//先查询数据库然后进行反馈
		}else if(effectName.equals("我要记账")) {
			return "请您说今天干什么花了多少钱，如果不知道记账规则请说账本规则。";
		}else if(effectName.equals("报菜价")) {
			return "西红柿1.99元一斤，黄瓜2.59元一斤，土豆1.29元一斤等，你可以直接问某种菜品的价格，如白菜的价格，所有菜价均来源于网络收集，因各地有所差异，这里仅供参考，具体以当地为准！";
		}else if(effectName.equals("不用了")) {
			return "好的，期待您下次使用！再见！";
		}else if(effectName.equals("账本规则")) {
			return "我们将消费类型归纳为五类：娱乐、学习、衣着、出行、食宿，你可以说今天娱乐花了40元";
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
		//	outData = "哎呀，没有找到你所要的，请重新试试。";
		//}
    	
    	return outData;
    }

}
