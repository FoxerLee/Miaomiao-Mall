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


def product():
    for i in range(0, 19):
        csvFile = open("/Users/liyuan/Documents/Cloud-Computing/Miaomiao-Mall/data_order/all_order_"+str(i)+".csv", 'rb')
        r = csv.reader(csvFile)
        datas = [row for row in r]

        c1 = open("/Users/liyuan/Documents/Cloud-Computing/Miaomiao-Mall/data_order/day_order_" + str(i) + ".csv",
                       'ab')
        w1 = csv.writer(c1)

        c2 = open("/Users/liyuan/Documents/Cloud-Computing/Miaomiao-Mall/data_order/month_order_" + str(i) + ".csv",
                  'ab')
        w2 = csv.writer(c2)

        for j in range(len(datas)):
            all_count = int(datas[j][2])

            for month in range(1, 13):
                month_count = 0
                if month == 1 or month == 3 or month == 5 or month == 7 or month == 8:
                    for day in range(1, 32):
                        day_count = random.randint(0, 1+random.randint(-1, 5))

                        if all_count < 0:
                            w1.writerow((datas[j][0], month, day, 0))
                            c1.flush()
                            month_count += 0

                        else:
                            w1.writerow((datas[j][0], month, day, day_count))
                            c1.flush()
                            month_count += day_count
                        all_count -= day_count

                if month == 10 or month == 12:
                    for day in range(1, 32):
                        day_count = random.randint(0, 4+random.randint(-4, 11))

                        if all_count < 0:
                            w1.writerow((datas[j][0], month, day, 0))
                            c1.flush()
                            month_count += 0

                        else:
                            w1.writerow((datas[j][0], month, day, day_count))
                            c1.flush()
                            month_count += day_count
                        all_count -= day_count

                if month == 4 or month == 6:
                    for day in range(1, 31):
                        day_count = random.randint(0, 1 + random.randint(-1, 3))

                        if all_count < 0:
                            w1.writerow((datas[j][0], month, day, 0))
                            c1.flush()
                            month_count += 0

                        else:
                            w1.writerow((datas[j][0], month, day, day_count))
                            c1.flush()
                            month_count += day_count
                        all_count -= day_count

                if month == 9 or month == 11:
                    for day in range(1, 31):
                        day_count = random.randint(0, 3 + random.randint(-2, 6))

                        if all_count < 0:
                            w1.writerow((datas[j][0], month, day, 0))
                            c1.flush()
                            month_count += 0

                        else:
                            w1.writerow((datas[j][0], month, day, day_count))
                            c1.flush()
                            month_count += day_count
                        all_count -= day_count

                if month == 2:
                    for day in range(1, 29):
                        day_count = random.randint(0, 2 + random.randint(-1, 3))

                        if all_count < 0:
                            w1.writerow((datas[j][0], month, day, 0))
                            c1.flush()
                            month_count += 0

                        else:
                            w1.writerow((datas[j][0], month, day, day_count))
                            c1.flush()
                            month_count += day_count
                        all_count -= day_count

                w2.writerow((datas[j][0], month, month_count))
                c2.flush()

            print datetime.datetime.now()
            print datas[j][0]

        c2.close()
        c1.close()
        csvFile.close()


def ly_address():

    file = open("dist/pca-code.json", 'rb')
    # line = file.readline()
    # datas = eval(line.decode('utf-8'))
    # print datas
    c = open('o_statistics.csv', 'ab')
    w = csv.writer(c)
    while 1:
        line = file.readline()
        if not line:
            break
        datas = eval(line.decode('utf-8'))
        # datas = json.loads(line.decode('utf-8'), object_hook=JSONObject)

        # for name in datas['name']:
        #     print name
        for i in range(len(datas)):
            one = datas[i]['name']

            # print type(datas[i]['childs'])
            # childs = eval(datas[i]['childs'])

            for child in datas[i]['childs']:
                # child = eval(child)
                # print child['code']

                two = child['name']

                # address = one + two
                # address = one + two + three
                # print address
                # w.writerow((address, miao['code']))

            for month in range(1, 13):
                month_count = 0
                if month == 1 or month == 3 or month == 5 or month == 7 or month == 8:
                    for day in range(1, 32):
                        day_count = random.randint(0, 47197)

                        w.writerow((one, month, day, day_count))
                        c.flush()
                        month_count += day_count

                if month == 10 or month == 12:
                    for day in range(1, 32):
                        day_count = random.randint(0, 59612)

                        w.writerow((one, month, day, day_count))
                        c.flush()
                        month_count += day_count

                if month == 4 or month == 6:
                    for day in range(1, 31):
                        day_count = random.randint(0, 73474)

                        w.writerow((one, month, day, day_count))
                        c.flush()
                        month_count += day_count

                if month == 9 or month == 11:
                    for day in range(1, 31):
                        day_count = random.randint(0, 63437)

                        w.writerow((one, month, day, day_count))
                        c.flush()
                        month_count += day_count

                if month == 2:
                    for day in range(1, 29):
                        day_count = random.randint(0, 83759)

                        w.writerow((one, month, day, day_count))
                        c.flush()
                        month_count += day_count


if __name__ == '__main__':
    # product()
    ly_address()