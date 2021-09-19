import os
# javac man1/Search.java &&
cmd = "java man1.Search -R 1 -d log-p4.txt -W 4 man1/100.txt fairing"

base_range = list(range(1,21))
r = 20
mult = 5
for i in range(1,10):
    r = r + mult*i
    base_range.append(r)

for nthreads in base_range:
    for ntasks in base_range:
        print(nthreads,ntasks)
        os.system(f"{cmd} {ntasks} {nthreads}")

"""
for nthreads in range(1,40):
    for i in range(1,21):
        print(i)
        os.system(cmd+" "+str(i)+" "+str(nthreads))

    threads = 20
    mult = 5
    for i in range(1,10):
        threads = threads + mult*i
        print(threads)
        os.system(cmd+" "+str(threads)+" "+str(nthreads))
"""