package com.yxtt.hold;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.sun.prism.impl.Disposer.Record;

public class DataBaseCon {
	static boolean flag;
		 //声明Connection对象
        static Connection con;
        static //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        static String url = "jdbc:mysql://localhost:3306/jdalpha";
        //MySQL配置时的用户名
        static String user = "root";
        //MySQL配置时的密码
        static String password = "";
        //遍历查询结果集
	public static boolean newTable(String userId) {
		String sqlStr = "CREATE TABLE "+ userId + "(date Date, entertainment Int(55), study Int(55), cloth Int(55), travel Int(55), eat Int(55))";
		return dataSQL(sqlStr);
	}
	public static boolean insertCount(String countDate, String countType, int money, String thisDate, String userId) {
		
		boolean flag = queryDate(userId, thisDate);
		if(flag) {
			if(updateMyCount(userId, countDate, countType, String.valueOf(money)))
			System.out.println("数据已存储！");
		}else {
			
			flag = insertData(userId, "date", thisDate);	//建立thisData记录
			if(flag)
			System.out.println("已生成date记录！");
			if(updateMyCount(userId, countDate, countType, String.valueOf(money)))
				System.out.println("数据已存储！");
		}
		return flag;
	}
	
	public static boolean insertThisDate(String userId,String thisDate) {
		String sqlStr = "insert into " + userId + "(date)" + "values('" + thisDate + "')";
		try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = (Statement) con.createStatement();
            //要执行的SQL语句
            int resultSet = statement.executeUpdate(sqlStr);
            
            System.out.println(resultSet);
            con.close();
            
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        	
        	return true;
        }
		
	}
	
	public static Count queryCount(String userId, String date) {
		Count count = new Count();
		String entertainmentCount, studyCount, clothCount, travelCount, eatCount, sumCount;
		entertainmentCount = queryOne(userId, "entertainment", date, "date");
		studyCount = queryOne(userId, "study", date, "date");
		clothCount = queryOne(userId, "cloth", date, "date");
		travelCount = queryOne(userId, "travel", date, "date");
		eatCount = queryOne(userId, "eat", date, "date");
		if(entertainmentCount == null)
			entertainmentCount ="0";
		if(studyCount == null) {
			studyCount = "0";
		}
		if(clothCount == null) {
			clothCount = "0";
		}
		if(travelCount == null) {
			travelCount = "0";
		}
		if(eatCount == null) {
			eatCount = "0";
		}
		int mainMax = Integer.valueOf(entertainmentCount) + Integer.valueOf(studyCount)
		+ Integer.valueOf(clothCount) + Integer.valueOf(travelCount) + Integer.valueOf(eatCount) ;
		//rebackStr = "今天"+"娱乐花了"+entertainmentCount+"元"+"，学习花了"+studyCount+"元"
		//+",衣着花了"+clothCount+"元"+"，出行花了"+travelCount+"元"+"，食宿花了"+eatCount+"元,今天一共花了"+mainMax+"元";
		sumCount = String.valueOf(mainMax);
		count.setEntainmentCount(entertainmentCount);
		count.setStudyCount(studyCount);
		count.setClothCount(clothCount);
		count.setTravelCount(travelCount);
		count.setEatCount(eatCount);
		count.setSumCount(sumCount);
		//System.out.println("sumcount:"+count.getSumCount());
		return count;
	}

	public static boolean queryUserID(String userId) {
		String sqlStr = "select * from userlist" + " where '" + userId + "'";
		boolean flag = false;
		try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = (Statement) con.createStatement();
            //要执行的SQL语句
            ResultSet resultSet = statement.executeQuery(sqlStr);
            while(resultSet.next()) {
            	resultSet.getString("userId");
            	if(resultSet.getString("userId").equals(userId)) {
            		flag = true;
            	}
            }
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        }
		return flag;
	}
	
	
	public static boolean queryDate(String userId, String thisDate) {
		String sqlStr = "select * from " + userId + " where '" + thisDate + "'";
		boolean flag = false;
		try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = (Statement) con.createStatement();
            //要执行的SQL语句
            ResultSet resultSet = statement.executeQuery(sqlStr);
            //当天的日期
            //System.out.println("thisdata的值为："+thisDate);
            while(resultSet.next()) {
            	resultSet.getString("date");
            	//循环获取的到的日期值
            	//System.out.println("date的值为："+resultSet.getString("date"));
            	if(resultSet.getString("date").toString().equals(thisDate)) {
            		flag = true;
            	}
            }
            con.close();
            //标志位的布尔值
            //System.out.println("flag的值为："+flag);
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        }
		 return flag;
	}
	
	public static boolean queryForm(String userId) {
		String sqlStr = "SELECT COUNT(*) FROM " + userId;
		boolean flag = false;
		try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = (Statement) con.createStatement();
            //要执行的SQL语句
            ResultSet resultSet = statement.executeQuery(sqlStr);
            //System.out.println(resultSet.next());
            if(resultSet.next()) {
            	flag = true;
            }else {
            	flag = false;
            }
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            }finally{
        }
		return flag;
		
	}

	public static boolean dataSQL(String sqlStr) {
		try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = (Statement) con.createStatement();
            //要执行的SQL语句
            flag = statement.execute(sqlStr);
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
		return flag;
	}
	
	
	@SuppressWarnings("finally")
	public static boolean insertData(String biao, String ziduan, String valueStr) {
		
		String sqlStr = "insert into " + biao + "(" + ziduan + ")" + "values('" + valueStr + "')";
		try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = (Statement) con.createStatement();
            //要执行的SQL语句
            int resultSet = statement.executeUpdate(sqlStr);
            
            System.out.println(resultSet);
            con.close();
            
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        	
        	return true;
        }
	}
	/*
	UPDATE   Customers

	SET   cust_email = ' kim@qq.com'

	WHERE  cust_id = '10000005';*/
	public static boolean updateMyCount(String userId, String countDate, String countType, String money) {
		String str = "UPDATE " + userId + " SET " + countType + " = '" + money + "' WHERE date = '" + countDate + "';";
		int resultSet = 0;
		try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = (Statement) con.createStatement();
            //要执行的SQL语句
            resultSet = statement.executeUpdate(str);
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        }
		if(resultSet == 0)
			return false;
		else 
			return true;
	}

	//----------------------------------------------
	//该方法用于查询某一项消费
	//----------------------------------------------
	 @SuppressWarnings("finally")
	public static String queryOne(String userId, String countType, String countDate, String ziduan)
	     {
	          ResultSet rs;
	          String sqlStr="select * from " + userId + " where "+ziduan+" ='"+countDate+"'";
	          String balance = null;
	          
	          try {
	              //加载驱动程序
	              Class.forName(driver);
	              //1.getConnection()方法，连接MySQL数据库！！
	              con = (Connection) DriverManager.getConnection(url,user,password);
	              //2.创建statement类对象，用来执行SQL语句！！
	              Statement statement = (Statement) con.createStatement();
	              //要执行的SQL语句
	              rs = statement.executeQuery(sqlStr);
	              rs.next(); //指向第一条数据
	              balance=rs.getString(countType);
	              con.close();
	              
	          } catch(ClassNotFoundException e) {   
	              //数据库驱动类异常处理
	              System.out.println("Sorry,can`t find the Driver!");   
	              e.printStackTrace();   
	              } catch(SQLException e) {
	              //数据库连接失败异常处理
	              e.printStackTrace();  
	              }catch (Exception e) {
	              // TODO: handle exception
	              e.printStackTrace();
	          }finally{
	          	return balance;
	          }
	          
	     }
}