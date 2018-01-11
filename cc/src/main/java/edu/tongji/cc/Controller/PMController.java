package edu.tongji.cc.Controller;

import edu.tongji.cc.Service.PMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pm")
public class PMController {

    @Autowired
    private PMService pmService;

    /**
     * 获取日销量（根据月）
     * @param month
     * @param id
     * @return
     */
    @GetMapping("/day")
    public Object getDataByMonth(@RequestParam(value = "month", defaultValue = "1")String month,
                                 @RequestParam(value = "id", defaultValue = "")String id){
        Map<String, Object> result = new HashMap<>();
        try{
            long start = System.currentTimeMillis();
            if(id.equals(""))
                result.put("data", null);
            else{
                result.put("data", pmService.getDataOfDayByMonth(Integer.parseInt(month), id));
            }
            long end = System.currentTimeMillis();
            result.put("time", ((double)(end - start))/1000);
            result.put("code", 0);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 1);
        }
        return result;
    }


    /**
     * 月销量
     * @param id
     * @return
     */
    @GetMapping("/month")
    // 获取所有月数据
    public Object getDataofMonth(@RequestParam(value = "id", defaultValue = "")String id){
        Map<String, Object> result = new HashMap<>();
        try{
            long start = System.currentTimeMillis();
            if(id.equals(""))
                result.put("data", null);
            else{
                result.put("data", pmService.getDataOfMonth(id));
            }
            long end = System.currentTimeMillis();
            result.put("time", ((double)(end - start))/1000);
            result.put("code", 0);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 1);
        }
        return result;
    }
}
