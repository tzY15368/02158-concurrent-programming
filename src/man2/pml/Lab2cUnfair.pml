/* DTU Course 02158 Concurrent Programming
 *   Lab 2
 *   spin5.pml
 *   Skeleton PROMELA model of mutual exlusion by coordinator
 */

#define N 4

bool enter[N];  /* Request to enter flags */
bool ok[N];     /* Entry granted flags    */
int okeyed = 0; /* Flag for whether Coordinator has ok'd a process */

int incrit = 0; /* For easy statement of mutual exlusion */

/*
 * Below it is utilised that the first N process instances will get 
 * pids from 0 to (N-1).  Therefore, the pid can be directly used as 
 * an index in the flag arrays.
 */

active [N] proctype P()
{
	do
	::	/* First statement is a dummy to allow a label at start */
		skip; 

entry:	
		enter[_pid] = true;
		/*await*/ ok[_pid] ->

crit:	/* Critical section */
		incrit++;
		assert(incrit == 1);
		incrit--;
  	
exit: 
		/* Your code here */
		okeyed--;
		enter[_pid] = false;
		ok[_pid] = false;

		/* Non-critical setion (may or may not terminate) */
		//do :: true -> skip :: break od

	od
}

active proctype Coordinator()
{
	int i;
	do
	::
		/*  Your code here instead of skip*/
		/*  Technique for fairness will be a queue . int head = 0 queue[head] = _pid */
		for (i: 0 .. (N-1) ) {
			if 
				:: enter[i]  && incrit==0 && okeyed ==0 -> ok[i] = true; okeyed++;
				:: !(enter[i]  && incrit==0 && okeyed==0) -> skip
			fi 
		}
	od
}

ltl fairness { []( (P@entry) -> <> (P@crit) ) }