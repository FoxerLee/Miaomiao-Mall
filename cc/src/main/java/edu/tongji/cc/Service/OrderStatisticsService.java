package edu.tongji.cc.Service;

public interface OrderStatisticsService {

    Object getDataByOfDay(int month, String address);

    Object getDataOfMonth(String address);
}
