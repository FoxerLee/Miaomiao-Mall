package edu.tongji.cc.Controller;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import java.util.HashMap;
import org.json.JSONObject;

// redis producer
@RequestMapping("/product")
@RestController
public class ProductController {

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
            current_price = (Double) requests.get("current_price");
            inventory = (Integer) requests.get("inventory");
            has_saled = (Integer) requests.get("has_saled");
            classification = (String) requests.get("classification");
            update_time = (String) requests.get("update_time");
        }catch (Exception e){
            responses.put("code", 1);
            return responses;
        }


        Jedis jedis = new Jedis("123.207.218.148", 6676);
        jedis.auth("DataWareHouse2017");
        try{
            System.out.println("连接成功");
            //设置 redis 字符串数据
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

            // 后端接口 插入数据到 redis
            hashMap.put("product_id",product_id);
            hashMap.put("name", name);
            hashMap.put("current_price", current_price);
            hashMap.put("inventory", inventory);
            hashMap.put("has_saled", has_saled);
            hashMap.put("classification", classification);
            hashMap.put("update_time", update_time);

            JSONObject jsonObject= new JSONObject(hashMap);

            jedis.lpush("products", jsonObject.toString());

            System.out.println(jsonObject.toString());

            responses.put("code", 0);

        }catch (Exception e){

            responses.put("code", 1);
            e.printStackTrace();

        }

        jedis.close();
        return responses;

    }

}
