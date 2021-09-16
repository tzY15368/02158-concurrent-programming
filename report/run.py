import os
cmd = "javac man1/Search.java && java man1.Search -R 10 -d log3.txt -W 4 man1/100-0.txt fairing"
for i in range(0,300):
    print(i)
    os.system(cmd+" "+str(i))