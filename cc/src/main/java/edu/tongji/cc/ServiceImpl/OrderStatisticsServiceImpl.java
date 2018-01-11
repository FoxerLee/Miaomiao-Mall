package edu.tongji.cc.ServiceImpl;

import edu.tongji.cc.Service.OrderStatisticsService;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    @Override
    public Object getDataByOfDay(int month, String address){
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miaomiao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from o_statistics WHERE  address = \'" + address + "\' AND month = \'" + month + "\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            int[] data = new int[31];
            for(int i = 0; i < data.length; i++)
                data[i] = 0;
            int a;
            while(rs.next()){
                try {
                    a = rs.getInt(3) - 1;
                    int has_saled = rs.getInt(4);
                    data[a] += has_saled;
//                    System.out.println(a);
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
            return data;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getDataOfMonth(String address){
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miaomiao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from o_statistics WHERE  address = \'" + address + "\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            int[] data = new int[12];
            for(int i = 0; i < data.length; i++)
                data[i] = 0;
            int a;
            while(rs.next()) {
                try {
                    a = rs.getInt(2) - 1;
//                    System.out.println(a);
//                    data[a] += 1;
                    int has_saled = rs.getInt(4);
                    data[a] += has_saled;
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
            return data;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
