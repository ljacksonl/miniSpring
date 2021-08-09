import mysql.NameBuilder;
import org.junit.Test;

import java.sql.*;
import java.util.Random;

public class testJDBC {
    private String url = "jdbc:mysql://192.168.4.50:3306/test1?rewriteBatchedStatements=true" ;
    private String user = "root" ;
    private String password = "dddd" ;
    @Test
    public void Test(){
        Connection conn = null ;
        PreparedStatement pstm = null ;
        ResultSet rt = null ;
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT IGNORE INTO person(ID,name,age,content) VALUES(?,?,?,?)" ;
            //预编译,存储过程
            pstm = conn.prepareStatement(sql);
            ////获得系统的时间
            Long startTime = System.currentTimeMillis();
            Random rand = new Random();
            NameBuilder nameBuilder = new NameBuilder();
            int a,b,c,d;
                for (int i = 1; i <= 1000; i++) {
                    pstm.setInt(1,i);
                    pstm.setString(2, nameBuilder.build());
                    a = rand.nextInt(81);
                    b = rand.nextInt(81);
                    c = rand.nextInt(81);
                    d = rand.nextInt(81);
                    pstm.setInt(3, a);
                    pstm.setString(4, "xxxxxxxxxx_" + "188" + a + "88" + b + c + "66" + d);
                    pstm.addBatch();
                }
                pstm.executeBatch();
            Long endTime = System.currentTimeMillis();
            System.out.println( "OK,用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (pstm!= null ){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (conn!= null ){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Test
    public void Test1(){
        Connection conn = null ;
        PreparedStatement pstm = null ;
        ResultSet rt = null ;
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT IGNORE INTO person(name,age,content) VALUES(?,?,?)" ;
            pstm = conn.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            Random rand = new Random();
            NameBuilder nameBuilder = new NameBuilder();
            int a,b,c,d;
            for ( int i = 1 ; i <= 100000 ; i++) {
                pstm.setString(1, nameBuilder.build());
                a = rand.nextInt(81);
                b = rand.nextInt(81);
                c = rand.nextInt(81);
                d = rand.nextInt(81);
                pstm.setInt(2, a);
                pstm.setString(3, "xxxxxxxxxx_" + "188" + a + "88" + b + c + "66" + d);
                pstm.addBatch();
            pstm.executeUpdate();
            }
            Long endTime = System.currentTimeMillis();
            System.out.println( "OK,用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (pstm!= null ){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (conn!= null ){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
