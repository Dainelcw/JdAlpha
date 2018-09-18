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
		 //����Connection����
        static Connection con;
        static //����������
        String driver = "com.mysql.jdbc.Driver";
        //URLָ��Ҫ���ʵ����ݿ���mydata
        static String url = "jdbc:mysql://localhost:3306/jdalpha";
        //MySQL����ʱ���û���
        static String user = "root";
        //MySQL����ʱ������
        static String password = "";
        //������ѯ�����
	public static boolean newTable(String userId) {
		String sqlStr = "CREATE TABLE "+ userId + "(date Date, entertainment Int(55), study Int(55), cloth Int(55), travel Int(55), eat Int(55))";
		return dataSQL(sqlStr);
	}
	public static boolean insertCount(String countDate, String countType, int money, String thisDate, String userId) {
		
		boolean flag = queryDate(userId, thisDate);
		if(flag) {
			if(updateMyCount(userId, countDate, countType, String.valueOf(money)))
			System.out.println("�����Ѵ洢��");
		}else {
			
			flag = insertData(userId, "date", thisDate);	//����thisData��¼
			if(flag)
			System.out.println("������date��¼��");
			if(updateMyCount(userId, countDate, countType, String.valueOf(money)))
				System.out.println("�����Ѵ洢��");
		}
		return flag;
	}
	
	public static boolean insertThisDate(String userId,String thisDate) {
		String sqlStr = "insert into " + userId + "(date)" + "values('" + thisDate + "')";
		try {
            //������������
            Class.forName(driver);
            //1.getConnection()����������MySQL���ݿ⣡��
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = (Statement) con.createStatement();
            //Ҫִ�е�SQL���
            int resultSet = statement.executeUpdate(sqlStr);
            
            System.out.println(resultSet);
            con.close();
            
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
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
		//rebackStr = "����"+"���ֻ���"+entertainmentCount+"Ԫ"+"��ѧϰ����"+studyCount+"Ԫ"
		//+",���Ż���"+clothCount+"Ԫ"+"�����л���"+travelCount+"Ԫ"+"��ʳ�޻���"+eatCount+"Ԫ,����һ������"+mainMax+"Ԫ";
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
            //������������
            Class.forName(driver);
            //1.getConnection()����������MySQL���ݿ⣡��
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = (Statement) con.createStatement();
            //Ҫִ�е�SQL���
            ResultSet resultSet = statement.executeQuery(sqlStr);
            while(resultSet.next()) {
            	resultSet.getString("userId");
            	if(resultSet.getString("userId").equals(userId)) {
            		flag = true;
            	}
            }
            con.close();
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
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
            //������������
            Class.forName(driver);
            //1.getConnection()����������MySQL���ݿ⣡��
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = (Statement) con.createStatement();
            //Ҫִ�е�SQL���
            ResultSet resultSet = statement.executeQuery(sqlStr);
            //���������
            //System.out.println("thisdata��ֵΪ��"+thisDate);
            while(resultSet.next()) {
            	resultSet.getString("date");
            	//ѭ����ȡ�ĵ�������ֵ
            	//System.out.println("date��ֵΪ��"+resultSet.getString("date"));
            	if(resultSet.getString("date").toString().equals(thisDate)) {
            		flag = true;
            	}
            }
            con.close();
            //��־λ�Ĳ���ֵ
            //System.out.println("flag��ֵΪ��"+flag);
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
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
            //������������
            Class.forName(driver);
            //1.getConnection()����������MySQL���ݿ⣡��
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = (Statement) con.createStatement();
            //Ҫִ�е�SQL���
            ResultSet resultSet = statement.executeQuery(sqlStr);
            //System.out.println(resultSet.next());
            if(resultSet.next()) {
            	flag = true;
            }else {
            	flag = false;
            }
            con.close();
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
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
            //������������
            Class.forName(driver);
            //1.getConnection()����������MySQL���ݿ⣡��
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = (Statement) con.createStatement();
            //Ҫִ�е�SQL���
            flag = statement.execute(sqlStr);
            con.close();
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
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
            //������������
            Class.forName(driver);
            //1.getConnection()����������MySQL���ݿ⣡��
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = (Statement) con.createStatement();
            //Ҫִ�е�SQL���
            int resultSet = statement.executeUpdate(sqlStr);
            
            System.out.println(resultSet);
            con.close();
            
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
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
            //������������
            Class.forName(driver);
            //1.getConnection()����������MySQL���ݿ⣡��
            con = (Connection) DriverManager.getConnection(url,user,password);
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = (Statement) con.createStatement();
            //Ҫִ�е�SQL���
            resultSet = statement.executeUpdate(str);
            con.close();
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
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
	//�÷������ڲ�ѯĳһ������
	//----------------------------------------------
	 @SuppressWarnings("finally")
	public static String queryOne(String userId, String countType, String countDate, String ziduan)
	     {
	          ResultSet rs;
	          String sqlStr="select * from " + userId + " where "+ziduan+" ='"+countDate+"'";
	          String balance = null;
	          
	          try {
	              //������������
	              Class.forName(driver);
	              //1.getConnection()����������MySQL���ݿ⣡��
	              con = (Connection) DriverManager.getConnection(url,user,password);
	              //2.����statement���������ִ��SQL��䣡��
	              Statement statement = (Statement) con.createStatement();
	              //Ҫִ�е�SQL���
	              rs = statement.executeQuery(sqlStr);
	              rs.next(); //ָ���һ������
	              balance=rs.getString(countType);
	              con.close();
	              
	          } catch(ClassNotFoundException e) {   
	              //���ݿ��������쳣����
	              System.out.println("Sorry,can`t find the Driver!");   
	              e.printStackTrace();   
	              } catch(SQLException e) {
	              //���ݿ�����ʧ���쳣����
	              e.printStackTrace();  
	              }catch (Exception e) {
	              // TODO: handle exception
	              e.printStackTrace();
	          }finally{
	          	return balance;
	          }
	          
	     }
}