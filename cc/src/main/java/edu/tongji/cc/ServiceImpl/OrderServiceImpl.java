package edu.tongji.cc.ServiceImpl;

import edu.tongji.cc.Model.Order;
import edu.tongji.cc.Service.OrderService;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class OrderServiceImpl implements OrderService {

    @Override
    public Object getSummaryById(String id){
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from r_order WHERE  product_id = \'" + id + "\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            Order order = null;
            while(rs.next()){
                order = new Order();
                order.setBuyer(rs.getString("buyer"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                order.setSales_price(rs.getDouble("sales_price"));
                order.setProduct_id(id);
            }
            rs.close();
            stmt.close();
            conn.close();
            return order;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
