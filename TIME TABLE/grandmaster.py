import random
import jackal_copy
class grandmaster():
    def __init__(self):
        self.jk=jackal_copy.jackal_copy()
    def getday(self,num):
        num=int(num)
        if num==0:
            day="Monday"
        if num==1:
            day="Tuesday"
        if num ==2:
            day="Wednesday"
        if num==3:
            day="Thursday"
        if num==4:
            day="Friday"
        if num==5:
            day="Saturday"
        return day

    def getrandomfirst(self,list):
        mn = random.choice(list)
        list.remove(mn)
        return list,mn
    def getrandomsecond(self,list,day,hour,sem):
        oldid,day1=self.jk.getoldstid(day,hour,str(int(sem)-2))
        oldid=int(oldid)
        stid=random.choice(list)
        if oldid == stid:
            stid=self.getrandomsecond(list,day,hour,sem)
        else:
            list.remove(stid)
        print("stiddddddddd")
        print(stid)
        return list,stid,day1
    # def getrandomthird(self,list,day,hour,sem,dept):
    #     print(sem)
    #     print("semsemmeiii")
    #     oldid1,day1=self.jk.getoldstidgt(day,hour,str(int(sem)-2),dept)
    #     oldid2,day1 = self.jk.getoldstidgt(day, hour, str(int(sem) - 4),dept)
    #     oldid2=int(oldid2)
    #     oldid1=int(oldid1)
    #     stid= random.choice(list)
    #     if oldid1==0 and oldid2==0:
    #         list.remove(stid)
    #         return list, stid, day1
    #     else:
    #         if oldid1==oldid2 or oldid1==stid or oldid2==stid:
    #             stid=self.getrandomthird(list,day,hour,sem,dept)

    def getrandomthird(self, list, day, hour, sem,dept):
        subid = random.choice(list)
        stid = int(self.jk.getstid(subid))
        oldid1, day1 = self.jk.getoldstidforthirdyear(day, hour, str(int(sem) - 2), str(stid),dept)
        oldid2, day1 = self.jk.getoldstidforthirdyear(day, hour, str(int(sem) - 4), str(stid),dept)
        oldid2 = str(oldid2)

        oldid1 = str(oldid1)
        print("oldid=" + str(oldid1) + ",oldid2=" + str(oldid2) + "stid=" + str(stid))

        if oldid1 == str(stid) or oldid2 == str(stid):
            print("yes")
            stid = self.getrandomthird(list, day, hour, sem)
        else:
            list.remove(subid)
        print("list" + str(list))
        print("subid" + str(subid))
        print("day1" + str(day1))
        return list, subid, day1

import mysql.connector
class Db:

    def __init__(self):
        self.cnx = mysql.connector.connect(host="localhost",user="root",password="",database="time_table")
        self.cur = self.cnx.cursor()

    def selectall(self,qry):
        self.cur.execute(qry)
        return self.cur.fetchall()

    def selectone(self,qry):
        self.cur.execute(qry)
        return self.cur.fetchone()
