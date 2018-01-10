package edu.tongji.cc.Controller;

import edu.tongji.cc.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/summary")
    public Object getSummary(@RequestParam(value = "id")String id){
        try{
            Map<String, Object> result = new HashMap<>();
            long start = System.currentTimeMillis();
            if(id.equals(""))
                result.put("data", null);
            else
                result.put("data", orderService.getSummaryById(id));
            long end = System.currentTimeMillis();
            result.put("time", ((double)(end - start))/1000);
            return result;
        }catch (Exception e){
            return 400;
        }

    }
}
