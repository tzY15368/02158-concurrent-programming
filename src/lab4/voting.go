package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Votes struct {
	a, b int
}

func station(out chan<- Votes) {
	for i := 0; i < 10; i++ {
		//fmt.Println("started generating votes")
		time.Sleep(time.Duration(rand.Intn(2000)) * time.Millisecond)
		aVotes := rand.Intn(100)
		v := Votes{aVotes, 100 - aVotes}
		out <- v
		//fmt.Println("generated votes", v)
	}
	close(out)
}

func collector(in1, in2 <-chan Votes, out chan<- Votes) {
	for {
		closedCount := 0
		var v1, ok = <-in1
		if !ok {
			closedCount++
		}
		var v2, ok2 = <-in2
		if !ok2 {
			closedCount++
		}
		if closedCount == 2 {
			close(out)
			break
		}
		o := Votes{
			a: v1.a + v2.a,
			b: v1.b + v2.b,
		}
		out <- o
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())

	var voteChannels [8]chan Votes
	var collectorChannels [7]chan Votes
	for i := range voteChannels {
		voteChannels[i] = make(chan Votes)
	}
	for i := range collectorChannels {
		collectorChannels[i] = make(chan Votes)
	}

	for j := 0; j < 8; j++ {
		go func(i int) {
			station(voteChannels[i])
		}(j)
	}

	for j := 0; j < 7; j++ {
		go func(i int) {

			switch i {
			case 0:
				go collector(voteChannels[0], voteChannels[1], collectorChannels[i])
			case 1:
				go collector(voteChannels[2], voteChannels[3], collectorChannels[i])
			case 2:
				go collector(voteChannels[4], voteChannels[5], collectorChannels[i])
			case 3:
				go collector(voteChannels[6], voteChannels[7], collectorChannels[i])
			case 4:
				go collector(collectorChannels[0], collectorChannels[1], collectorChannels[i])
			case 5:
				go collector(collectorChannels[2], collectorChannels[3], collectorChannels[i])
			case 6:
				go collector(collectorChannels[4], collectorChannels[5], collectorChannels[i])
			}
		}(j)
	}

	var tally Votes
	for {
		e := false
		select {
		case <-time.Tick(2 * time.Second):
			fmt.Println("Current tally:", tally)
		case v, ok := <-collectorChannels[6]:
			if !ok {
				e = true
			}
			tally.a += v.a
			tally.b += v.b
		}
		if e {
			break
		}
	}

	tot := tally.a + tally.b

	if tot != 8000 {
		fmt.Println("Tally issue:", tot)
	}

	var winner string
	switch {
	case tally.a > tally.b:
		winner = "A"
	case tally.a < tally.b:
		winner = "B"
	default:
		winner = "undetermined"
	}
	fmt.Printf("All votes counted. And the winner is: %s\n", winner)
	if winner == "B" {
		fmt.Println("A: This must be FRAUD!!!")
	}
}
