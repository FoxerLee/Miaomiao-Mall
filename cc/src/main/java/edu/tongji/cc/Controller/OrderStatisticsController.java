package edu.tongji.cc.Controller;

import edu.tongji.cc.Service.OrderStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orderstatistics")
public class OrderStatisticsController {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @GetMapping("/day")
    public Object getDataOfDay(@RequestParam(value = "month", defaultValue = "")String month,
                               @RequestParam(value = "address", defaultValue = "")String address){
        try{
            Map<String, Object> result = new HashMap<>();
            long start = System.currentTimeMillis();
            if(address.equals(""))
                result.put("data", null);
            else
                result.put("data", orderStatisticsService.getDataByOfDay(Integer.parseInt(month), address));
            long end = System.currentTimeMillis();
            result.put("time", ((double)(end - start))/1000);
            return result;
        }catch (Exception e){
            return 400;
        }
    }

    @GetMapping("/month")
    public Object getDataOfDay(@RequestParam(value = "address", defaultValue = "")String address){
        try{
            Map<String, Object> result = new HashMap<>();
            long start = System.currentTimeMillis();
            if(address.equals(""))
                result.put("data", null);
            else
                result.put("data", orderStatisticsService.getDataOfMonth(address));
            long end = System.currentTimeMillis();
            result.put("time", ((double)(end - start))/1000);
            return result;
        }catch (Exception e){
            return 400;
        }
    }
}
