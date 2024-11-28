import random

lis=[]
for i in range(0,9):
    while True:
        ii=random.randint(0, 9)
        if ii not in lis:
            lis.append(ii)
            break
print (lis)