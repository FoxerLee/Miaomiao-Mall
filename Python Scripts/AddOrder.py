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
import HandleJson

import sys
reload(sys)
sys.setdefaultencoding('utf-8')


def run():

    csvFile = open('new_products11.csv', 'rb')
    r = csv.reader(csvFile)
    ids = [row[0] for row in r]

    csvFile = open('new_products11.csv', 'rb')
    r = csv.reader(csvFile)
    prices = [row[5] for row in r]

    csvFile = open('cc_fake.csv', 'rb')
    r = csv.reader(csvFile)
    fakes = [row for row in r]
    len_fakes = len(fakes)
    len_ids = len(ids)
    print ids[0]
    print prices[0]

    # c = open("order.csv", 'ab')
    # w = csv.writer(c)

    order_id = 834678

    miao = 0
    order_count = 0
    orders = []
    for i in range(len(ids)):

        if miao > 8000000:
            c = open("/Users/liyuan/Documents/Cloud-Computing/Miaomiao-Mall/data_order/order_"+str(order_count)+".csv", 'ab')
            w = csv.writer(c)
            w.writerows(orders)
            orders = []
            order_count += 1
            miao = 0
            c.close()

        count = 0
        if float(prices[i]) <= 100.0:
            count = random.randint(2052, 10249)
        elif 100.0 < float(prices[i]) <= 512.0:
            count = random.randint(205, 1632)
        elif 512 < float(prices[i]) <= 2048.0:
            count = random.randint(89, 462)
        else:
            count = random.randint(11, 209)

        for j in range(count):
            fake = fakes[random.randint(0, len_fakes-1)]
            random_miao = random.randint(0, len_ids-1)
            date = HandleJson.random_date()
            orders.append((order_id, ids[random_miao], prices[random_miao], fake[0], fake[1], fake[2], date))
            order_id += 1
            miao += 1
            print order_id
            print datetime.datetime.now()


if __name__ == '__main__':
    run()