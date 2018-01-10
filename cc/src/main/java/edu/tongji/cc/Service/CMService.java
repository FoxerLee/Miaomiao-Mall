package edu.tongji.cc.Service;

/**
 * 类别统计服务
 */
public interface CMService {

    Object getDataOfDaysByMonth(int month, String classifiication_id);

    Object getDataOfMonth(String classification);
}
