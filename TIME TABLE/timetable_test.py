

class grandmaster():
    def __init__(self):
        pass

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
        return day
