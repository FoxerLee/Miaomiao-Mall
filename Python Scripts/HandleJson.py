# -*- coding: utf-8 -*-
import json
import re
import os
from lxml import html
import csv
import datetime
import mysql.connector
from multiprocessing import Process
import random

import sys
reload(sys)
sys.setdefaultencoding('utf-8')


class JSONObject:
    def __init__(self, d):
        self.__dict__ = d


def random_date():
    year = '2017'

    month = str(random.randint(1, 12))
    day = '1'
    if month == '1' or month == '3' or month == '5' or month == '7' or month == '8':
        month = '0' + month
        day = str(random.randint(1, 31))

    elif month == '10' or month == '12':
        day = str(random.randint(1, 31))
    elif month == '4' or month == '6' or month == '9':
        month = '0' + month
        day = str(random.randint(1, 30))
    elif month == '11':
        day = str(random.randint(1, 30))
    elif month == '2':
        month = '0' + month
        day = str(random.randint(1, 28))

    if 1 <= int(day) <= 9:
        day = '0' + day
    hour = str(random.randint(0, 23))
    if len(hour) == 1:
        hour = '0' + hour
    minute = str(random.randint(0, 59))
    if len(minute) == 1:
        minute = '0' + minute
    second = str(random.randint(0, 59))
    if len(second) == 1:
        second = '0' + second

    date = year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second

    return date

def run():
    # with open("/Users/liyuan/Documents/Cloud-Computing/数据/天猫双11商品活动数据.json", "r") as load_f:
    #     load_dict = json.load(load_f)
    #     print type(load_dict)
    file = open("/Users/liyuan/Documents/Cloud-Computing/数据/天猫双12商品活动数据.json", 'rb')

    csvFile = open("products12.csv", 'ab')
    w = csv.writer(csvFile)
    error = 0
    while 1:
        line = file.readline()
        if not line:
            break

        # load_dict = json.dumps(line)
    #     print line['_id']
        data = eval(line.decode('utf-8'))
        print data['商品ID']
        print datetime.datetime.now()
        # print data['卖家地址']
    #     datas = json.loads(line.decode('utf-8'), object_hook=JSONObject)
        date = random_date()
        try:
            w.writerow((data['商品ID'], data['原价'], data['标题'], data['会场'], data['服务保障'], data['现价'], date))
            csvFile.flush()
        except Exception, e:
            print e
            error += 1
        # print datas._id
        # print datas.
        # print type(load_dict)
    csvFile.close()
    print "Error number " + str(error)


def delete():
    csvFile = open("products12.csv", 'rb')
    r = csv.reader(csvFile)
    datas = [row for row in r]

    csvFile = open("new_products12.csv", 'ab')
    w = csv.writer(csvFile)

    for data in datas:
        if data[2].find(',') != -1:
            print data[2]
            continue
        else:
            w.writerow((data[0], data[1], data[2], data[3], data[4], data[5], data[6]))
            csvFile.flush()

    csvFile.close()


def cc():
    csvFile = open("new_products12g.csv", 'rb')
    r = csv.reader(csvFile)
    datas = [row for row in r]



    csvFile = open("ll_products11.csv", 'ab')
    w = csv.writer(csvFile)



    for data in datas:
        # if data[2].find(',') != -1:
        #     print data[2]
        #     continue
        # else:
        w.writerow((data[0], data[2], data[1], random.randint(0, 12938), random.randint(0, 12398), data[6]))
        csvFile.flush()

    csvFile.close()

    
if __name__ == '__main__':
    cc()
    # delete()
    # for i in range(1, 366):
    #     print "insert into p_daily (product_id, day, d_sales, d_price) values(\'1\',"+str(i)+", 100, 50.0);"