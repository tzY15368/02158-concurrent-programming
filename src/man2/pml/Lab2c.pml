/* DTU Course 02158 Concurrent Programming
 *   Lab 2
 *   spin5.pml
 *   Skeleton PROMELA model of mutual exlusion by coordinator
 */

#define N 4

bool enter[N];  /* Request to enter flags */
bool ok[N];     /* Entry granted flags    */
int okeyed = 0; /* Flag for whether Coordinator has ok'd a process */

bool enqueued[N] = 0;

int queue[N];
int queueHead = 0; /* the next available spot in queue*/
int dequeueResult;

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

/* enqueue and dequeue assumed atomic or threadsafe implementation*/
inline ENQUEUE(x) {
	atomic{
		queue[queueHead]=x;
		queueHead++;
	}
}


inline DEQUEUE(){
	atomic {
		int j;
		dequeueResult = queue[0];
		for (j:1 .. queueHead) {
			queue[j-1]=queue[j];
		}
		queueHead--;
	}
}

active proctype Coordinator()
{
	int i;
	do
	:: 
		/*  Your code here instead of skip*/
		for (i: 0 .. (N-1) ) {
			if 
				:: enter[i] && incrit==0 && (okeyed == 0) /* && (enqueued[i] == 0) */-> ENQUEUE(i);
												enqueued[i]=1;
				:: !(enter[i]  && incrit==0 && (okeyed==0) /* && (enqueued[i] == 0) */ ) -> skip
			fi 
		}
		DEQUEUE();
		ok[dequeueResult]= true;
		okeyed++;

	od
}

ltl fairness { []( (P@entry) -> <> (P@crit) ) }