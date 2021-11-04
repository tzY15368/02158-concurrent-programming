#define N 6

int up = 0;
int down = 0;

int eSem = 1;
int upSem = 0;
int downSem = 0; 

int upCrit = 0;
int downCrit = 0;

int upQueueLen = 0;
int downQueueLen = 0;

int tmp;

inline P(s){ atomic{ s > 0 -> s = s-1}}
inline V(s){ atomic{ s = s+1}}
inline incr(s){ tmp = s + 1; s = tmp;}
inline decr(s) {tmp = s-1; s = tmp;}

inline enter(self_dir, other_dir,  dirLen, dirSem){
	P(eSem);
	if 
	:: other_dir > 0 -> incr(dirLen); V(eSem); P(dirSem);
	:: other_dir < 1 -> skip;
	fi;

	incr(self_dir);
	
	if
	:: dirLen > 0 -> decr(dirLen); V(dirSem);
	:: dirLen < 1 -> V(eSem);
	fi;
}
inline leave(self_dir, dirLen, dirSem){
	P(eSem);
	decr(self_dir);
	if
	:: (self_dir==0) && (dirLen > 0) -> decr(dirLen); V(dirSem);
	:: (self_dir!=0) || (dirLen < 1) -> V(eSem);
	fi;
}


// for PIDs < 5, goes down; >=5: goes up
active [N] proctype go(){
	
	if
	:: _pid < 5 -> enter(down,up,downQueueLen, downSem); incr(downCrit); decr(downCrit); leave(down, upQueueLen, upSem);
	:: _pid > 4 -> enter(up, down, upQueueLen, upSem); incr(upCrit); decr(upCrit); leave(up, downQueueLen, downSem);
	fi;
	
}

active proctype Check(){
	(upCrit > 0 && downCrit > 0) -> assert(false);
}
