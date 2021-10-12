#define N 2

int n1 = 0; //number of cars in alley that go one way
int n2 = 0; //number of cars in alley that go the other way

active [N] proctype carOneWay() {
    do
    :: /* First statement is a dummy to allow a label at start */
		skip; 
entry:	
    atomic{n2 == 0 -> n1++}
crit:
    /* critical section */
    skip;
exit:
    atomic{n1--;}   // not needed atomic statement in promella but used for readability
    /* non critical section*/
    do :: true -> skip :: break od
    od
}

active [N] proctype carOtherWay() {
    do
    :: /* First statement is a dummy to allow a label at start */
		skip; 
entry:	
    atomic{n1 == 0 -> n2++}
crit:
    /* critical section */
    skip;
exit:
    atomic{n2--;}   // not needed atomic statement in promella but used for readability
    /* non critical section*/
    do :: true -> skip :: break od
    od
}

active proctype Check() {
    assert( n1 == 0 || n2 == 0 ) 
}
