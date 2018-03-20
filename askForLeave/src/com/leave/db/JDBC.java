package com.leave.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.leave.config.Config;


/**
 * 
 * @author lucheng
 * 数据库连接及相关操作
 */
public class JDBC {
    
    private final static String dbDriver=Config.get("db.drivername");
    private final static String url= Config.get("db.url");
    private final static String Name=Config.get("db.username");
    private final static String Password=Config.get("db.password");
    //private static Connection con = null;
    
    /*public JDBC(){
        try {
        	//System.out.println(dbDriver);
            Class.forName(dbDriver);
            System.out.println("数据库驱动加载成功......");
        } catch (ClassNotFoundException e) {
            //加载数据库失败
            System.out.println("数据库驱动加载失败......");
        }
    }*/
    //获取连接
    public static Connection getConnection(){
        Connection con = null;
        try {
        	Class.forName(dbDriver);
            //System.out.println(dbDriver+"\n"+url+"\n"+Name+"\n"+Password);
            con= DriverManager.getConnection(url,Name,Password);
            con.setAutoCommit(true);
            System.out.println("连接成功......");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("数据库连接失败......");
        }
        return con;
    }
    //进行增、删、改的操作,动态编译
    public static boolean executeUpdate(String sql,Object ...params){
        Connection con = getConnection();
        if(con == null){
            return false;
        }
        PreparedStatement pstm=null;
        try {
            pstm = con.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                pstm.setObject(j, params[j-1]);
            }
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
        finally{
            try {
                close(con,pstm,null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }   
    //查询操作
    public static List<List> list(String sql,Object ...params){
        Connection con = getConnection();
        if(con == null){
            return null;
        }
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<List> resultList = new LinkedList<List>();
        
        try {
            pstm = con.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                pstm.setObject(j, params[j-1]);
            }
            rs = pstm.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns =rsmd.getColumnCount();
            //System.out.println("查询操作成功......");
            List list = new LinkedList();
            for(int i = 1; i <= numberOfColumns; i++){
            	list.add(rsmd.getColumnName(i));
            }
            resultList.add(list);
            while(rs.next()){
                list = new LinkedList();
                for(int i =1 ;i<=numberOfColumns ;i++){
                    list.add(rs.getObject(i));
                }
                resultList.add(list);
            }
            return resultList;
        } catch (SQLException e) {
            return null;
        }
        finally{
            try {
                //System.out.println("关闭连接......");
                close(con, pstm, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //查询操作
    public static List<Map<String,Object>> executeQuery(String sql,Object ...params){
        Connection con = getConnection();
        if(con == null){
            return null;
        }
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        
        try {
            pstm = con.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                pstm.setObject(j, params[j-1]);
            }
            rs = pstm.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns =rsmd.getColumnCount();
            //System.out.println("查询操作成功......");
            while(rs.next()){
                Map<String,Object> rsTree = new HashMap<String,Object>();
                for(int i =1 ;i<=numberOfColumns ;i++){
                    rsTree.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                resultList.add(rsTree);
            }
            return resultList;
        } catch (SQLException e) {
            return null;
        }
        finally{
            try {
                //System.out.println("关闭连接......");
                close(con, pstm, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //关闭连接对象
    public static void close(Connection con, PreparedStatement pstat, ResultSet res) throws SQLException{
        if(res != null){
            res.close();
        }
        if(pstat != null){
            pstat.close();
        }
        if(con != null){
            con.close();
        }
    }
    //关闭连接对象
    /*public static void close(Object ...params){
        
        for (Object obj : params)
        {
            Class c = obj.getClass();
            try {
                Method m = c.getMethod("close");
                if(null != m)
                {
                    m.invoke(obj);
                }
            } catch (NoSuchMethodException | 
                    SecurityException | 
                    IllegalAccessException | 
                    IllegalArgumentException | 
                    InvocationTargetException e) {
                
            }
            
            
        }
    }*/
    
    //测试
    public static void main(String ...args) {
    	getConnection();
        String sql = "insert into leave_user(user_name,user_password) values(?,?)";
       // JDBC.executeUpdate(sql,"张3","zyy");
       // sql = "update userinfo set user_password='123456' where user_name='root'";
        //JDBC.executeUpdate(sql);
        //sql = "delete from userinfo where user_name like '张%' ";
        //JDBC.executeUpdate(sql);
        sql = "select * from user";
        
        /*List<Map<String,Object> > list = JDBC.executeQuery(sql);
        for (Map<String, Object> userMap : list) {
            System.out.println("用户名：" + userMap.get("user_name") + " ");
            System.out.println("密码：" + userMap.get("user_password"));
        }*/
        
    }
}
