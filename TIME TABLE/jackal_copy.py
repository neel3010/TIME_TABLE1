from DBConnection import Db
from grandmaster import grandmaster
from flask import jsonify
class jackal_copy():
    def __init__(self):
        self.d=Db()
    def logman(self,day,sub_id,hour):
        qry="select * from timetable"
        qry=self.d.select(qry)
        if len(qry)==0:
            print("11111111111111111111111111")
            qr="insert into timetable(subject_id,day,hour)values('"+str(sub_id)+"','"+str(day)+"','"+str(hour)+"')"
            print(qr)
            self.d.insert(qr)
        else:
            print("222222222222222222222222222")
            qry = "select * from timetable WHERE day='"+str(day)+"' and subject_id='" + str(sub_id) + "'"
            self.d.selectOne(qry)
            if qry is None:
                qr = "insert into timetable(subject_id,day,hour)values('" + str(sub_id) + "','" + str(day) + "','" + str(hour) + "')"
                print(qr)
                self.d.insert(qr)
            else:
                qr = "insert into timetable(subject_id,day,hour)values('" + str(sub_id) + "','" + str(
                    day) + "','" + str(hour) + "')"
                print(qr)
                self.d.insert(qr)
                pass


    def logman_new(self,day,sub_id,hour, cid, sem):
        qry="select * from subject where subject_id='"+str(sub_id)+"'"
        qry=self.d.selectOne(qry)
        subname=qry['subject_name']


        if "lab" in subname.lower():    #   checking whether subject is lab
            print("Lab", hour)
            qry2 = self.d.selectOne(
                "select count(time_table_id) as cnt from timetable where subject_id='" + str(sub_id) + "'")
            if qry2['cnt'] == 6:  # limiting subjects with 4 class per week(3*2periods=6)
                return hour
            if hour<6:          # for continous 2 periods
                for i in range(2):
                    qr = "insert into timetable(subject_id,day,hour)values('" + str(sub_id) + "','" + str(
                        day) + "','" + str(
                        hour) + "')"
                    print(qr)
                    self.d.insert(qr)
                    hour = hour + 1
                return hour
            else:
                return hour
        else:    #   checking whether subject is theory
            print("Theory", hour)
            qry2 = self.d.selectOne(
                "select count(time_table_id) as cnt from timetable where subject_id='" + str(sub_id) + "'")
            if qry2['cnt'] == 4:  # limiting subjects with 4 class per week
                return hour
            #   check total theory subjects allocated per day
            qr3=self.d.select("select * from timetable inner join subject on timetable.subject_id=subject.subject_id "
                                 "where subject.course_id='"+str(cid)+"' and subject.semester='"+str(sem)+"' and day='"+day+"'")
            theory_cnt=0
            for i  in qr3:
                if 'lab' not in i['subject_name'].lower():
                    theory_cnt+=1

            if theory_cnt<4:       # limiting 4 theory subjects per day
                qr = "insert into timetable(subject_id,day,hour)values('" + str(sub_id) + "','" + str(day) + "','" + str(
                    hour) + "')"
                print(qr)
                self.d.insert(qr)
                hr=hour+1
                return hr
            else:
                return hour



    def getlogman(self,sem):
        qr="select timetable.*,subject.semester as sem from subject inner join timetable on timetable.subject_id=subject.subject_id where subject.semester='"+sem+"'"
        print(qr,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        res=self.d.select(qr)
        return res
    def getlogmonday(self,sem,cid):
        qr="select timetable.*,subject.semester,subject.subject_name from timetable,subject where timetable.subject_id=subject.subject_id and subject.semester='"+sem+"' and timetable.day='Monday' and subject.course_id='"+cid+"' order by timetable.hour"
        print(qr)
        res=self.d.select(qr)
        print(res)
        return res
    def getlogtuesday(self,sem,cid):
        qr="select timetable.*,subject.semester,subject.subject_name from timetable,subject where timetable.subject_id=subject.subject_id and subject.semester='"+sem+"' and timetable.day='Tuesday' and subject.course_id='"+cid+"' order by timetable.hour"
        res=self.d.select(qr)
        return res
    def getlogwedday(self,sem,cid):
        qr="select timetable.*,subject.semester,subject.subject_name from timetable,subject where timetable.subject_id=subject.subject_id and subject.semester='"+sem+"' and timetable.day='Wednesday' and subject.course_id='"+cid+"' order by timetable.hour"
        res=self.d.select(qr)
        return res
    def getlogthurday(self,sem,cid):
        qr="select timetable.*,subject.semester,subject.subject_name from timetable,subject where timetable.subject_id=subject.subject_id and subject.semester='"+sem+"' and timetable.day='Thursday' and subject.course_id='"+cid+"' order by timetable.hour"
        res=self.d.select(qr)
        return res
    def getlogfriday(self,sem,cid):
        qr="select timetable.*,subject.semester,subject.subject_name from timetable,subject where timetable.subject_id=subject.subject_id and subject.semester='"+sem+"' and timetable.day='Friday' and subject.course_id='"+cid+"' order by timetable.hour"
        res=self.d.select(qr)
        return res
    # def getlogsatday(self,sem,cid):
    #     qr="select timetable.*,subject.semester,subject.subject_name from timetable,subject where timetable.subject_id=subject.subject_id and subject.semester='"+sem+"' and timetable.day='Saturday' and subject.course_id='"+cid+"' order by timetable.hour"
    #     res=self.d.select(qr)
    #     return res
    def getlogdaystaff(self,sem,day):
        qr=" select timetable.*,subject.semester,staff.staff_name from timetable inner join subject on timetable.subject_id=subject.subject_id inner join sub_allocate on sub_allocate.sub_id=subject.subject_id inner join staff on sub_allocate.staff_id=staff.staff_id where subject.semester='"+sem+"' and timetable.day='"+day+"' order by hour"
        res=self.d.select(qr)
        return res
    def getlogdaysubstaff(self,sem,day):
        qr=" select timetable.*,subject.semester,staff.staff_name,subject.subject_name from timetable inner join subject on timetable.subject_id=subject.subject_id inner join sub_allocate on sub_allocate.sub_id=subject.subject_id inner join staff on sub_allocate.staff_id=staff.staff_id where subject.semester='"+sem+"' and timetable.day='"+day+"' order by hour"
        res=self.d.select(qr)
        return res
    def getsubid(self,sem,cid):
        qr="select subject.subject_id from subject where course_id='"+cid+"' and semester='"+sem+"'"
        res = self.d.select(qr)
        return res

    def getlabsubid(self, sem, cid):
        qr = "select subject.subject_id from subject where course_id='" + cid + "' and semester='" + sem + "'"
        res = self.d.select(qr)
        return res

    def getstid(self,subid):
        qr = "select staff_id from sub_allocate where sub_id='"+str(subid)+"'"
        res = self.d.selectOne(qr)
        return str(res['staff_id'])

    def getoldstid(self,day,hour,sem):
        id="0"
        gm=grandmaster()
        days=gm.getday(day)
        # print(days)
        qry=" select sub_allocate.staff_id from timetable inner join sub_allocate on timetable.subject_id=sub_allocate.sub_id inner join subject on subject.subject_id=sub_allocate.sub_id where timetable.day='"+str(days)+"' and timetable.hour='"+str(hour)+"' and subject.semester='"+str(sem)+"'"
        res=self.d.selectOne(qry)
        if res is not None:
            return str(res['staff_id']),days
        else:
            return '0',days

    def getoldstidgt(self, day, hour, sem,dep):
        id = "0"
        gm = grandmaster()
        days = gm.getday(day)
        qry = " select sub_allocate.staff_id from timetable inner join sub_allocate on timetable.subject_id=sub_allocate.sub_id inner join subject on subject.subject_id=sub_allocate.sub_id where timetable.day='" + str(days) + "' and timetable.hour='" + str(hour) + "' and subject.semester='" + str(sem) + "'"
        res = self.d.selectOne(qry)
        # print(res)
        # print(type(res))
        if res is not None:
            return str(res['staff_id']), days
        else:
            return '0', days
    def getlogmondaystaff(self, sem, cid):
        qr = "select timetable.*,subject.semester_id,subject.subject_name,staff.staff_name from timetable,subject,sub_allocate,staff where subject.subject_id=timetable.subject_id and subject.semester_id='" + sem + "' and timetable.day='Monday' and subject.course_id='" + cid + "' and sub_allocate.staff_id=staff.staff_id and sub_allocate.sub_id=subject.subject_id order by timetable.hour"
        # print(qr)
        res = self.d.select(qr)
        # print(res)
        return res

    def getlogtuesdaystaff(self, sem, cid):
        qr = "select timetable.*,subject.semester_id,subject.subject_name,staff.staff_name from timetable,subject,sub_allocate,staff where subject.subject_id=timetable.subject_id and subject.semester_id='" + sem + "' and timetable.day='Tuesday' and subject.course_id='" + cid + "' and sub_allocate.staff_id=staff.staff_id and sub_allocate.sub_id=subject.subject_id order by timetable.hour"
        res = self.d.select(qr)
        return res

    def getlogweddaystaff(self, sem, cid):
        qr = "select timetable.*,subject.semester_id,subject.subject_name,staff.staff_name from timetable,subject,sub_allocate,staff where subject.subject_id=timetable.subject_id and subject.semester_id='" + sem + "' and timetable.day='Wednesday' and subject.course_id='" + cid + "' and sub_allocate.staff_id=staff.staff_id and sub_allocate.sub_id=subject.subject_id order by timetable.hour"
        res = self.d.select(qr)
        return res

    def getlogthurdaystaff(self, sem, cid):
        qr = "select timetable.*,subject.semester_id,subject.subject_name,staff.staff_name from timetable,subject,sub_allocate,staff where subject.subject_id=timetable.subject_id and subject.semester_id='" + sem + "' and timetable.day='Thursday' and subject.course_id='" + cid + "'   and sub_allocate.staff_id=staff.staff_id and sub_allocate.sub_id=subject.subject_id order by timetable.hour"
        res = self.d.select(qr)
        return res

    def getlogfridaystaff(self, sem, cid):
        qr = "select timetable.*,subject.semester_id,subject.subject_name,staff.staff_name from timetable,subject,sub_allocate,staff where subject.subject_id=timetable.subject_id and subject.semester_id='" + sem + "' and timetable.day='Friday' and subject.course_id='" + cid + "'   and sub_allocate.staff_id=staff.staff_id and sub_allocate.sub_id=subject.subject_id order by timetable.hour"
        res = self.d.select(qr)
        return res

    # def getlogsatdaystaff(self, sem, cid):
    #     qr = "select timetable.*,subject.semester,subject.subject_name,staff.staff_name from timetable,subject,sub_allocate,staff where subject.subject_id=timetable.subject_id and subject.semester='" + sem + "' and timetable.day='Saturday' and subject.course_id='" + cid + "'   and sub_allocate.staff_id=staff.staff_id and sub_allocate.sub_id=subject.subject_id order by timetable.hour"
    #     res = self.d.select(qr)
    #     return res
    def getrandomday(self):
        import random
        list=["Monday","Tuesday","Wednesday","Thursday","Friday"]
        mn = random.choice(list)
        list.remove(mn)
        kl = [1, 4]
        kk=random.choice(kl)
        return mn,kk

    def getoldstidforthirdyear(self, day, hour, sem, staffid,dep):
        id = "0"
        gm = grandmaster()
        days = gm.getday(day)
        qry = " select sub_allocate.staff_id from timetable inner join sub_allocate on timetable.subject_id=sub_allocate.sub_id " \
              "inner join subject on subject.subject_id=sub_allocate.sub_id where timetable.day='" + str(
            days) + "' and timetable.hour='" + str(hour) + "' and subject.semester='" + str(
            sem) + "' and sub_allocate.staff_id='" + staffid + "'"
        # print(qry)
        res = self.d.selectOne(qry)
        # print("res")
        # print(res)
        if res is None:
            return "oo", days
        else:
            return "0", days







