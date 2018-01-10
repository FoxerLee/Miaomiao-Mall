package edu.tongji.cc.ServiceImpl;

import edu.tongji.cc.Service.CMService;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CMServiceImpl implements CMService{

    @Override
    public Object getDataOfDaysByMonth(int month, String classifiication_id){
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from cm_statistics WHERE  classification_id = \'" + classifiication_id + "\'" +
                    "AND month = " + month;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            int[] data = new int[31];
            for(int i = 0; i < data.length; i++)
                data[i] = 0;
            while(rs.next())
                data[rs.getInt("day") - 1] += 1;
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
    public Object getDataOfMonth(String classification_id){
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from cm_statistics WHERE  classification_id = \'" + classification_id + "\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            int[] data = new int[12];
            for(int i = 0; i < data.length; i++)
                data[i] = 0;
            while(rs.next())
                data[rs.getInt("month") - 1] += 1;
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
