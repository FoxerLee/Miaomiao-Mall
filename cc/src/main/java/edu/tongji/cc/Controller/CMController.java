package edu.tongji.cc.Controller;

import edu.tongji.cc.ServiceImpl.CMServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cm")
public class CMController {

    @Autowired
    private CMServiceImpl cmService;

    /**
     * 获取某个月的折线图数据
     * @param id
     * @param month
     * @return
     */
    @GetMapping("/day")
    public Object getDataOfDayByMonth(@RequestParam(value = "id",defaultValue = "")String id,
                                 @RequestParam(value = "month", defaultValue = "1")String month){
        try{
            Map<String, Object> result = new HashMap<>();
            long start = System.currentTimeMillis();
            if(id.equals(""))
                result.put("data",null);
            else
                result.put("data", cmService.getDataOfDaysByMonth(Integer.parseInt(month), id));
            long end = System.currentTimeMillis();
            result.put("time", ((double)(end - start))/1000);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return 400;
        }
    }

    /**
     * 获取月销量数据
     * @param id
     * @return
     */
    @GetMapping("/month")
    public Object getDataOfMonth(@RequestParam(value = "id", defaultValue = "")String id){
        try{
            Map<String, Object> result = new HashMap<>();
            long start = System.currentTimeMillis();
            if(id.equals(""))
                result.put("data", null);
            else{
                result.put("data", cmService.getDataOfMonth(id));
            }
            long end = System.currentTimeMillis();
            result.put("time", ((double)(end - start))/1000);
            return result;
        }catch (Exception e){
            return 400;
        }
    }
}
