import uuid
from flask import *
from database import *

api=Blueprint('api',__name__)
import demjson



@api.route('/login',methods=['get','post'])
def login():
    data={}

    username = request.args['username']
    password = request.args['password']
    q="SELECT * from login where username='%s' and password='%s'" % (username,password)
    res = select(q)
    print('resssssssssssssssssssssss',res)
    li=res[0]['login_id']
    utype=res[0]['usertype']
    if utype=="teacher":
        qry="""SELECT * FROM teacher_attendance
WHERE staff_id = (SELECT staff_id FROM staff WHERE login_id = '%s') AND datetime = CURDATE()"""%(li)

        res4=select(qry)
        print("res4444444444444444444444444444",res4)
        if not res4:
            
            l="insert into teacher_attendance values(null,(select staff_id from staff where login_id='%s'),curdate())"%(li)
            insert(l)
            
        
    if res :
        data['status']  = 'success'
        data['data'] = res
        data['method']='login'
    else:
        data['status']	= 'failed'
        data['method']='login'
    return  demjson.encode(data)



@api.route('/viewprofile',methods=['get','post'])

def viewprofile():
    lid=request.args['log_id']

    data={}
    
    sel="SELECT * FROM student INNER JOIN course  USING(course_id) INNER JOIN department USING(department_id) WHERE student_id='%s'"%(lid)
    res = select(sel)
    print(res)
    if res:
        
        data['status']	='success'
        data['data']=res
        
    return str(data)

@api.route('/attendance_view',methods=['get','post'])
def attendance_view():
    data={}
    lid=request.args['log_id']
    sel="SELECT * FROM attendance INNER JOIN student USING(student_id) INNER JOIN course USING(course_id) INNER JOIN department USING(department_id) WHERE student_id='%s'"%(lid)
    res = select(sel)
    print(res)
    
    if res:
        data['status'] = 'success'
        data['data']  = res
        
    return str(data)
 


@api.route('/mark_vew',methods=['get','post'])
def mark_vew():
    data={}
    lid=request.args['log_id']
    sel="""SELECT *
FROM student
	INNER JOIN marks ON student.student_id = marks.student_id
	INNER JOIN staff ON marks.staff_id = staff.login_id
	INNER JOIN assign_subject ON staff.staff_id = assign_subject.staff_id
	INNER JOIN SUBJECT ON assign_subject.subject_id = SUBJECT.subject_id
WHERE student.student_id='%s'"""%(lid)
    res = select(sel)
    print(res)
    
    if res:
        data['status'] = 'success'
        data['data']  = res
        
    return str(data)


@api.route("/User_Complaint")
def User_Complaint():
	data={}
	lid=request.args['log_id']
	complaints=request.args['complaints']
	q="insert into complaints values(null,'%s','%s','pending',now())"%(lid,complaints)
	print(q)
	res=insert(q)

	if res:
		data['status']='success'
	else:
		data['status']='failed'
	data['method']='User_Complaint'

	return str(data)


@api.route('/viewcomp',methods=['get','post'])
def viewcomp():
    data={}
    lid=request.args['log_id']
    sel="SELECT * FROM complaints INNER JOIN student USING(student_id) WHERE student_id='%s' ORDER BY DATETIME DESC"%(lid)
  
    
    res=select(sel)
    data['status']='success'
    data['method']='viewcomp'
    data['data']=res
    
    return str(data)


@api.route("/course")
def course():
    data={}
    qry="select * from course"
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
        data['method']='course'
    else:
        data['status']='failed'
        data['method']='course'
    return str(data)

@api.route("/batch")
def batch():
    data={}
    qry="select * from batches"
    res=select(qry)

    if res:
        data['statuss']='ok'
        data['data']=res
        data['method']='batch'
   
    return str(data)

###########################################################teacherrrrrrrrrrrrrrrrrrrrrrrrrrrr##############################

# @api.route("/user")
# def user():
# 	data={}

# 	fname=request.args['fname']
# 	lname=request.args['lname']
# 	latitude=request.args['latitude']
# 	longitude=request.args['longitude']
# 	phone=request.args['phone']
# 	email=request.args['email']
# 	hname=request.args['housename']
# 	place=request.args['place']
# 	pincode=request.args['pincode']
# 	district=request.args['district']
# 	uname=request.args['username']
# 	pwd=request.args['password']

# 	q="insert into login values(null,'%s','%s','pending')"%(uname,pwd)
# 	print(q)
# 	id=insert(q)

# 	q="insert into users values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,latitude,longitude,phone,email,hname,place,pincode,district)
# 	insert(q)
# 	data['status']='success'
# 	return str(data)







@api.route('/staffreg',methods=['get','post'])
def staffreg():
    data={}
    print("/////////////////////////////////////////////////////////////////")
    name=request.form['name']
    place=request.form['place']
    image=request.files['image']
    path="static/images/"+str(uuid.uuid4())+ image.filename
    image.save(path)
    print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",image)

    district=request.form['district']
    quali=request.form['quali']
    email=request.form['email']
    phone=request.form['phone']
    username=request.form['username']
    password=request.form['password']
    
    print("**************************************************",name)
    lid=request.form['log_id']
    print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",lid)
 
	# q="UPDATE work_requests SET status='Finished' WHERE request_id='%s'"%(requset_id)
	# update(q)'
    print(course,"0000000000000000000000000000000000000000000000")
    q1="insert into login values(null,'%s','%s','teacher')"%(username,password)
    uid=insert(q1)
    q="insert into staff values(null,'%s','%s','%s','%s','%s','%s','%s','%s','pending')"%(uid,name,place,district,quali,path,email,phone)
    id=insert(q)    
    if id>0:
        data['status'] = 'success'
        
    else:
        data['status'] = 'failed'
    data['method'] = 'staffreg'
    return str(data)

@api.route('/userreg',methods=['get','post'])
def userreg():
    data={}
    print("/////////////////////////////////////////////////////////////////")
    name=request.form['name']
    course=request.form['course']
    image=request.files['image']
    path="static/images/"+str(uuid.uuid4())+ image.filename
    image.save(path)
    print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",image)

    dob=request.form['dob']
    gender=request.form['gender']
    plc=request.form['plc']
    post=request.form['post']
    pin=request.form['pin']
    dis=request.form['dis']
    email=request.form['email']
    phn=request.form['phn']
    unme=request.form['uname']
    psd=request.form['psd']


    print("**************************************************",name,course)
    lid=request.form['log_id']
    print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",lid)
 
	# q="UPDATE work_requests SET status='Finished' WHERE request_id='%s'"%(requset_id)
	# update(q)'
    print(course,"0000000000000000000000000000000000000000000000")
    q1="insert into login values(null,'%s','%s','student')"%(unme,psd)
    uid=insert(q1)
    q="insert into student values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(uid,name,course,dob,gender,plc,post,pin,dis,email,phn,path)
    id=insert(q)    
    if id:
        data['status'] ="success"
        
    else:
        data['status'] = 'failed'
    data['method'] = 'user_pets'
    return str(data)




@api.route('/teacher_viewprofile',methods=['get','post'])

def teacher_viewprofile():
    lid=request.args['log_id']

    data={}
    
    sel="SELECT * FROM staff  WHERE login_id='%s'"%(lid)
    res = select(sel)
    print(res)
    if res:
        
        data['status']	='success'
        data['data']=res
        
    return str(data)

@api.route('/staff_stud_view',methods=['get','post'])

def staff_stud_view():
    lid=request.args['log_id']

    data={}
    
    # sel="SELECT * FROM student INNER JOIN course  USING(course_id) INNER JOIN department USING(department_id) "
    sel = "SELECT * FROM assign_subject INNER JOIN staff USING(staff_id) INNER JOIN SUBJECT USING(subject_id) INNER JOIN course USING(course_id)INNER JOIN student USING(course_id) INNER JOIN department USING(department_id)  WHERE login_id='%s'"%(lid)
    res = select(sel)
    print("*************************************************************************",res,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
    if res:
        
        data['status']	='success'
        data['data']=res
    else:
        data['status']	='failed'
        
    return str(data)



@api.route("/studentdrop")
def studentdrop():
    data={}
    lid=request.args['log_id']

    q="SELECT * FROM assign_subject INNER JOIN staff USING(staff_id) INNER JOIN SUBJECT USING(subject_id) INNER JOIN course USING(course_id)INNER JOIN student USING(course_id) INNER JOIN department USING(department_id) WHERE login_id='%s'"%(lid)
    print(q)
    res=select(q)

    data['status']='success'
    data['method']='viewstud'
    data['data']=res

    return str(data)


@api.route("/perioddrop")
def perioddrop():
    data={}

    q="SELECT * FROM  periods"
    res=select(q)
    print(res)

    data['status']='success'
    data['method']='viewperiod'
    data['data']=res

    return str(data)



@api.route('/attendacemark')
def attendacemark():
    data={}
    lid=request.args['log_id']
    s_id=request.args['s_id']
    period=request.args['period']
    status=request.args['status']
    
    op="insert into  attendance value(null,'%s','%s','%s','%s',curdate())"%(s_id,lid,period,status)
    
    obj=insert(op)
    
    data['status']='success'
    data['method']='sennt'
    data['data']=obj

    return str(data)






@api.route("/markstudentdrop")
def markstudentdrop():
    data={}
    lid=request.args['log_id']

    q="SELECT * FROM assign_subject INNER JOIN staff USING(staff_id) INNER JOIN SUBJECT USING(subject_id) INNER JOIN course USING(course_id)INNER JOIN student USING(course_id) INNER JOIN department USING(department_id) WHERE login_id='%s'"%(lid)
    print(q)
    res=select(q)

    data['status']='success'
    data['method']='markstud'
    data['data']=res

    return str(data)


@api.route('/cemarkmanage')
def cemarkmanage():
    data={}
    lid=request.args['log_id']
    s_id=request.args['s_id']
    mark = request.args['mark']
    op="insert into  marks value(null,'%s','%s','%s',now())"%(s_id,lid,mark)
    
    obj=insert(op)
    
    data['status']='success'
    data['method']='managece'
    data['data']=obj

    return str(data)


@api.route('/attendancemarkofstud',methods=['get','post'])

def attendancemarkofstud():
    lid=request.args['log_id']

    data={}
    
    # sel="SELECT * FROM student INNER JOIN course  USING(course_id) INNER JOIN department USING(department_id) "
    sel = "SELECT * FROM assign_subject INNER JOIN staff USING(staff_id) INNER JOIN SUBJECT USING(subject_id) INNER JOIN course USING(course_id)INNER JOIN student USING(course_id) INNER JOIN department USING(department_id)  WHERE login_id='%s'"%(lid)
    print(sel)
    res = select(sel)
    print("*************************************************************************",res,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
    if res:
        
        data['status']	='success'
        data['data']=res
        
    return str(data)




@api.route('/addmark', methods=['get', 'post'])
def addmark():
    data = {}
    
    term = request.form['terms']
    attendance_count = int(request.form['attendance_count'])  # Get the attendance count

    # Assuming you have a default total working days value
    total_working_days = 100  # You can replace this with your desired value

    avg = (attendance_count / total_working_days) * 100

    if avg >= 90:
        qry="insert into marks values(null,'%s','%s','%s',curdate(),'%s')"%(id,session['t_id'],20,term)
        insert(qry)            
        return '''<script>alert('CE marks added successfully');window.location='teacher_manage_ecmark'</script>'''
    elif avg >= 80 and avg <= 89:
        qry="insert into marks values(null,'%s','%s','%s',curdate(),'%s')"%(id,session['t_id'],18,term)
        insert(qry)
        return '''<script>alert('CE marks added successfully');window.location='teacher_manage_ecmark'</script>'''
    elif avg >= 70 and avg <= 79:
        qry="insert into marks values(null,'%s','%s','%s',curdate(),'%s')"%(id,session['t_id'],17,term)
        insert(qry)
        return '''<script>alert('CE marks added successfully');window.location='teacher_manage_ecmark'</script>'''
    elif avg >= 60 and avg <= 69:
        qry="insert into marks values(null,'%s','%s','%s',curdate(),'%s')"%(id,session['t_id'],16,term)
        insert(qry)
        return '''<script>alert('CE marks added successfully');window.location='teacher_manage_ecmark'</script>'''
    elif avg >= 50 and avg <= 59:
        qry="insert into marks values(null,'%s','%s','%s',curdate(),'%s')"%(id,session['t_id'],15,term)
        insert(qry)
        return '''<script>alert('CE marks added successfully');window.location='teacher_manage_ecmark'</script>'''
    else:
        qry="insert into marks values(null,'%s','%s','%s',curdate(),'%s')"%(id,session['t_id'],14,term)
        insert(qry)
        return '''<script>alert('CE marks added successfully');window.location='teacher_manage_ecmark'</script>'''

    return render_template("attendance_form.html", data=data)




@api.route("/semdrop")
def semdrop():
    data={}

    q="SELECT * FROM semseter "
    print(q)
    res=select(q)

    data['status']='success'
    data['method']='semview'
    data['data']=res

    return str(data)



@api.route("/std_att")
def std_att():
    data={}
    stids=request.args['stids']

    q=" SELECT *,COUNT(`attendance_id`) AS stat FROM `attendance` WHERE `student_id`='%s' "%(stids)
    print(q)
    res=select(q)

    data['status']='success'
    data['method']='std_att'
    data['data1']=res[0]['stat']

    return str(data)


@api.route("/publishattentmark")
def publishattentmark():
    data={}
    stids=request.args['stud_id']
    lid=request.args['log_in']
    mk=request.args['mark']
    sem=request.args['sem']

    q="insert into attendance_mark values(null,'%s','%s','%s',now(),'%s')"%(stids,lid,mk,sem)
    
    print(q)
    res=insert(q)

    data['status']='ok'
    data['method']='publishattentmark'
    

    return str(data)














