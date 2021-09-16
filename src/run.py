import os
# javac man1/Search.java &&
cmd = "java man1.Search -R 10 -d log.txt -W 4 man1/100.txt fairing"
for i in range(1,21):
    print(i)
    os.system(cmd+" "+str(i))

threads = 20
mult = 5
for i in range(1,10):
    threads = threads + mult*i
    print(threads)
    os.system(cmd+" "+str(threads))