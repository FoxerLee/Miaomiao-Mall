package edu.tongji.cc.ServiceImpl;

import edu.tongji.cc.Service.PMService;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class PMServiceImpl implements PMService {

    @Override
    public Object getDataOfDayByMonth(int month, String product_id){
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from pm_statistics WHERE  product_id = \'" + product_id + "\' AND  month = " + month;
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
    public Object getDataOfMonth(String product_id){
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        // 为了提升性能，统计统计操作放在后端做
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from pm_statistics WHERE  product_id = \'" + product_id + "\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            // 初始化12个月
            int[] data = new int[12];
            for(int i = 0; i < data.length; i++)
                data[i] = 0;
            // 累加每个月的所有天的销售量
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
