package main

import (
	"fmt"
	"sync"
	"time"
)

var wg sync.WaitGroup
var ch1 = make(chan int)
var ch2 = make(chan int)
var ch3 = make(chan int)

func p1() {
	defer wg.Done()
	time.Sleep(1 * time.Second)
	ch2 <- 1
	<-ch1
	fmt.Println("proceeding")
}

func p2() {
	defer wg.Done()
	time.Sleep(2 * time.Second)
	ch3 <- 1
	<-ch2
	fmt.Println("proceeding")
}

func p3() {
	defer wg.Done()
	time.Sleep(0)
	ch1 <- 1
	<-ch3
	fmt.Println("proceeding")
}

func main() {
	wg.Add(3)
	go p1()
	go p2()
	go p3()
	wg.Wait()

}
