#define N 6

int up = 0;
int down = 0;

int upSem = 1;
int downSem = 1; 

int upCrit = 0;
int downCrit = 0;

int tmp;

inline P(s){ atomic{ s > 0 -> s = s-1}}
inline V(s){ atomic{ s = s+1}}
inline incr(s){ tmp = s + 1; s = tmp;}
inline decr(s) {tmp = s-1; s = tmp;}

inline enter(no){
	if
	:: no < 5 -> P(downSem);if
				:: down==0 -> P(upSem);
				fi;
				incr(down);
				V(downSem);
	:: no > 4 -> P(upSem);if
				:: up==0 -> P(downSem);
				fi;
				incr(up);
				V(upSem);
	fi;
}
inline leave(no){
	if 
	:: no < 5 -> decr(down);
			if
			:: down==0 -> V(upSem);
			fi;
	:: no > 4 -> decr(up);
			if 
			:: up==0 -> V(downSem);
			fi;
	fi;
}


// for PIDs < 5, goes down; >5: goes up
active [N] proctype go(){
	enter(_pid);
	
	if
	:: _pid < 5 ->incr(downCrit);
	:: _pid > 4 ->incr(upCrit);
	fi;
	
	if
	:: _pid < 5 -> decr(downCrit);
	:: _pid > 4 -> incr(upCrit);
	fi;
	
	leave(_pid);	
}

active proctype Check(){
	(upCrit > 0 && downCrit > 0) -> assert(false);
}
