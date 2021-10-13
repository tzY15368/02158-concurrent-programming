#define N 6

int up = 0;
int down = 0;

int eSem = 1
int upSem = 0;
int downSem = 0; 

int upCrit = 0;
int downCrit = 0;

int tmp;

int downQueueLen = 0;
int upQueueLen = 0;

inline P(s){ atomic{ s > 0 -> s = s-1}}
inline V(s){ atomic{ s = s+1}}
inline incr(s){ tmp = s + 1; s = tmp;}
inline decr(s) {tmp = s-1; s = tmp;}



inline enter(no){
        if
        :: no < 5 -> P(eSem); 
                if
                :: down > 0 -> incr(upQueueLen); V(eSem); P(upSem)
                :: else -> skip;
                fi;
                incr(up);
                if
                :: upQueueLen > 0 -> decr(upQueueLen); V(upSem);
                :: else -> V(eSem);
                fi;

        :: no > 4 -> P(eSem);
                if
                :: up > 0 -> incr(downQueueLen); V(eSem); P(downSem)
                :: else -> skip;
                fi;
                incr(down);
                if
                :: downQueueLen > 0 -> decr(downQueueLen); V(downSem);
                :: else -> V(eSem);
                fi;

        fi;
}

inline leave(no){
        P(eSem);
        if
        :: no < 5 -> decr(up);
                if
                :: (up == 0 && downQueueLen > 0) -> decr(downQueueLen); V(downSem);
                :: else -> V(eSem);
                fi

        :: no > 4 -> decr(down);
                if
                :: (down == 0 && upQueueLen > 0) -> decr(upQueueLen); V(upSem);
                :: else -> V(eSem);
                fi
        fi
}

active [N] proctype go(){
        do
        :: true -> 
            enter(_pid);
            if
            :: _pid < 5 -> downCrit++;
            :: _pid > 4 -> upCrit++;
            fi;
            
            if
            :: _pid < 5 -> downCrit--;
            :: _pid > 4 -> upCrit--;
            fi;

            leave(_pid);
        od
}

active proctype Check(){
	(upCrit > 0 && downCrit > 0) -> assert(false);
}

active proctype CheckBinary(){
    (upSem + downSem + eSem > 1) -> assert(false);
}