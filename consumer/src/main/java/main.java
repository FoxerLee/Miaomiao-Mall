import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.HashMap;

public class main {


    public static void main(String[] args){
        Jedis jedis = new Jedis("123.207.218.148", 6676);
        jedis.auth("DataWareHouse2017");
        while (true){
            //获取订单数据
            try {
                String product;
                if (jedis.exists("products")){
                    product = jedis.lpop("products");
                }else {
                    System.out.println("no");
//                    jedis.close();
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                if(product != null){
                    Gson gson = new Gson();
                    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

                    hashMap = gson.fromJson(product, hashMap.getClass());

                    String product_id = hashMap.get("product_id").toString();

                    String name = hashMap.get("name").toString();

                    Double current_price = (Double) hashMap.get("current_price");

                    Double inventory_d = (Double) hashMap.get("inventory");
                    int inventory = inventory_d.intValue();

                    Double has_saled_d = (Double) hashMap.get("has_saled");
                    int has_saled = has_saled_d.intValue();

                    String classification = (String) hashMap.get("classification");
//                    int classification = classification_d.intValue();

                    String update_time = hashMap.get("update_time").toString();

                    System.out.println(hashMap.toString());

                    // 插入hive
                    insert(product_id, name, current_price, inventory, has_saled, classification, update_time);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
//            jedis.close();
        }

    }
    public static void insert(String product_id, String name, double current_price,
                              int inventory, int has_saled, String classification, String update_time) {
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Connection con = DriverManager.getConnection("jdbc:hive2://10.60.41.125:10000/miaomiao", "hadoop", "hadoop");
            try {
                if (con.isClosed()) {
                    System.out.println("close");
                } else {
                    System.out.println("open");
                    String sql = "insert into product_t (product_id, name, current_price, inventory, has_saled, classification, update_time) values(?,?,?,?,?,?,?)";
//                stmt.execute("insert into product (product_id, day, d_sales, d_price) values('1',17, 100, 50.0)");
                    PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
                    pstmt.setString(1, product_id);
                    pstmt.setString(2, name);
                    pstmt.setDouble(3, current_price);
                    pstmt.setInt(4, inventory);
                    pstmt.setInt(5, has_saled);
                    pstmt.setString(6, classification);
                    pstmt.setString(7, update_time);


                    pstmt.execute();

                    System.out.println("ok");

                    pstmt.close();
                    con.close();
                }
            }catch (Exception e){
                con.close();
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
