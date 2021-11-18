package main

import (
	"fmt"
	"runtime"
	"time"
)

//var wg sync.WaitGroup

var finished = make(chan string)

func p(name string) {
	//defer wg.Done()

	for i := 0; i < 3; i++ {
		time.Sleep(300 * time.Millisecond)
		fmt.Printf("I am %s\n", name)
	}
	//time.Sleep(1 * time.Second)
	finished <- name
}

func donald() {
	fmt.Println(runtime.GOMAXPROCS(0))
	go p("Huey")
	go p("Dewey")
	go p("Louie")
	i := 0
	// for {
	// 	var n, ok = <-finished
	// 	if !ok {
	// 		fmt.Println("not ok")
	// 		break
	// 	}
	// 	fmt.Println("name" + n)
	// 	i++
	// 	if i == 3 {
	// 		break
	// 	}
	// }
	fmt.Printf("done")
}
