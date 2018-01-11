package edu.tongji.cc.Controller;


import com.google.gson.Gson;
import edu.tongji.cc.Model.Order;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONObject;

// redis producer
@RequestMapping("/product")
@RestController
public class ProductController {

    @GetMapping("/getProductInfo")
    @ResponseBody
    public Object getProductInfo(@RequestParam("id") String id){
        System.out.println(id);
        final String DB_URL = "jdbc:hive2://10.60.41.125:10000/miaomiao";
        final String USER = "hive";
        final String PASS = "hive";
        Connection conn = null;
        Statement stmt = null;
        try {

            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

            Jedis jedis = new Jedis("123.207.218.148", 6676);
            jedis.auth("DataWareHouse2017");

            // 是否在updates里面
            if(jedis.exists("updates")){
                if (jedis.hexists("updates", id)){
                    String string = jedis.hget("updates", id);
                    System.out.println(string);
                    if (string!=null){
                        Gson gson = new Gson();
                        hashMap = gson.fromJson(string, hashMap.getClass());
                        return hashMap;
                    }
                }
            }

            //是否在inserts里面
            if(jedis.exists("inserts")){
                if(jedis.hexists("inserts", id)){
                    String string = jedis.hget("inserts", id);
                    System.out.println(string);
                    if (string!=null){
                        Gson gson = new Gson();
                        hashMap = gson.fromJson(string, hashMap.getClass());
                        return hashMap;
                    }
                }
            }


            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from product WHERE  product_id = \'" + id.toString() + "\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            String pid;
            String name;
            Double current_price;
            Integer inventory;
            Integer has_saled;
            String classfication;
            String update_time;

            while(rs.next()){
                try {
                    pid = rs.getString(1);
                    name = rs.getString(2);
                    current_price = rs.getDouble(3);
                    inventory = rs.getInt(4);
                    has_saled = rs.getInt(5);
                    classfication = rs.getString(6);
                    update_time = rs.getString(7);
                    hashMap.put("product_id", pid);
                    hashMap.put("name", name);
                    hashMap.put("current_price", current_price);
                    hashMap.put("inventory", inventory);
                    hashMap.put("has_saled", has_saled);
                    hashMap.put("classfication", classfication);
                    hashMap.put("update_time", update_time);
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("查询成功");
            return hashMap;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/addProduct")
    @ResponseBody
    public Object addProduct(@RequestBody HashMap<Object, Object> requests){

        HashMap<Object, Object> responses = new HashMap<>();

        String product_id;
        String name;
        Double current_price;
        int inventory;
        int has_saled;
        String classification;
        String update_time;

        try {
            product_id = (String) requests.get("product_id");
            name = (String) requests.get("name");
            current_price = new Double((String) requests.get("current_price"));
            inventory = new Integer((String)requests.get("inventory"));
            has_saled = new Integer((String) requests.get("has_saled"));
            classification = (String) requests.get("classification");
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println("当前时间：" + sdf.format(d));
            update_time = sdf.format(d).toString();
        }catch (Exception e){
            responses.put("code", 1);
            e.printStackTrace();
            return responses;
        }


        Jedis jedis = new Jedis("123.207.218.148", 6676);
        jedis.auth("DataWareHouse2017");
        try{
//            System.out.println("连接成功");
            //设置 redis 字符串数据
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

            // 后端接口 插入数据到 redis
            hashMap.put("product_id",product_id);
//            System.out.println(product_id);
            hashMap.put("name", name);
            hashMap.put("current_price", current_price);
            hashMap.put("inventory", inventory);
            hashMap.put("has_saled", has_saled);
            hashMap.put("classification", classification);
            hashMap.put("update_time", update_time);

            JSONObject jsonObject= new JSONObject(hashMap);

            jedis.lpush("products", jsonObject.toString());
            jedis.hset("inserts", product_id, jsonObject.toString());

            System.out.println(jsonObject.toString());

            responses.put("code", 0);

            System.out.println("添加商品成功");

        }catch (Exception e){

            responses.put("code", 1);
            e.printStackTrace();

        }

        jedis.close();
//        System.out.println(responses);
        return responses;

    }

    @PostMapping("/updateProduct")
    @ResponseBody
    public Object updateProduct(@RequestBody HashMap<Object, Object> requests){
        HashMap<Object, Object> responses = new HashMap<>();

        String product_id;
        String name;
        Double current_price;
        int inventory;
        int has_saled;
        String classification;
        String update_time;

        try {
            product_id = (String) requests.get("product_id");
            name = (String) requests.get("name");
            current_price = new Double((String) requests.get("current_price"));
            inventory = new Integer((String)requests.get("inventory"));
            has_saled = new Integer((String) requests.get("has_saled"));
            classification = (String) requests.get("classification");
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("当前时间：" + sdf.format(d));
            update_time = sdf.format(d).toString();
        }catch (Exception e){
            responses.put("code", 1);
            e.printStackTrace();
            return responses;
        }

        Jedis jedis = new Jedis("123.207.218.148", 6676);
        jedis.auth("DataWareHouse2017");
        try{
//            System.out.println("连接成功");
            //设置 redis 字符串数据
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

            // 后端接口 插入数据到 redis
            hashMap.put("product_id",product_id);
//            System.out.println(product_id);
            hashMap.put("name", name);
            hashMap.put("current_price", current_price);
            hashMap.put("inventory", inventory);
            hashMap.put("has_saled", has_saled);
            hashMap.put("classification", classification);
            hashMap.put("update_time", update_time);

            JSONObject jsonObject= new JSONObject(hashMap);

            jedis.hset("updates",product_id, jsonObject.toString());

            System.out.println(jsonObject.toString());

            responses.put("code", 0);

            System.out.println("编辑商品成功");

        }catch (Exception e){

            responses.put("code", 1);
            e.printStackTrace();

        }

        jedis.close();
        System.out.println(responses);
        return responses;

    }

    @GetMapping("/deleteProduct")
    @ResponseBody
    public Object deleteProduct(@RequestParam("id") String id){
        HashMap<Object, Object> responses = new HashMap<>();


        Jedis jedis = new Jedis("123.207.218.148", 6676);
        jedis.auth("DataWareHouse2017");
        try{
            jedis.hset("deletes", id, "xxx");
            responses.put("code", 0);
        }catch (Exception e){
            responses.put("code", 1);
            e.printStackTrace();

        }
        jedis.close();
        System.out.println("删除成功");
        return responses;

    }

}
