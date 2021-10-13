#define N 3
#define semaphore byte   /* define a sempahore */

int n1 = 0; //number of cars in alley that go one way
int n2 = 0; //number of cars in alley that go the other way

semaphore e = 1;  
semaphore s1 = 0;  // semaphore for carOneWay
semaphore s2 = 0;  // semaphore for carOtherWay

int d1 = 0;  // number of carOneWay delayed
int d2 = 0;  // number of carOtherWay dealyed

/*  P and V functions for mutex semaphores  */
inline V(s) {s++;}
inline P(s) {atomic{ s>0 ; s--}}

inline SIGNAL() {
    if :: n2 == 0 && d1 > 0 -> d1--; V(s1)  // awaken a carOneWay
       :: n1 == 0 && d2 > 0 -> d2--; V(s2)  // awaken a carOtherWay
       :: ! ( (n2 == 0 && d1) || (n1 == 0 && d2) )-> V(e)
    fi
}

active [N] proctype carOneWay() {
    do
    :: skip; 
entry:	
    /* atomic{n2 == 0 -> n1++} */
    P(e);
    if :: n2 > 0 -> d1++; V(e); P(s1) fi 
    n1++;
    SIGNAL();
crit:
    /* critical section */
    skip;
exit:
    atomic{n1--;}  
    P(e);
    n1--;
    SIGNAL;

    /* non critical section*/
    do :: true -> skip :: break od
    od
}



active [N] proctype carOtherWay() {
    do
    :: skip; 
entry:	
    /* atomic{n1 == 0 -> n2++} */
    P(e);
    if :: n1 > 0 -> d2++; V(e); P(s2)  fi 
    n2++;
    SIGNAL();
crit:
    /* critical section */
    skip;
exit:
    atomic{n2--;}  
    P(e);
    n2--;
    SIGNAL;

    /* non critical section*/
    do :: true -> skip :: break od
    od
}



active proctype Check() {
    assert( e + s1 +s2 == 1) 
}