/*
   DTU 02158 Concurrent Programming
   Mandatory Assignment 4
   Fall 2021
*/

package main

import (
	"fmt"

	"github.com/tzy15368/02158cp/queue"
)

func main() {
	q := queue.NewBoundedQueue(19, nil)

	q.StartConsumers(2, func(item interface{}) { fmt.Println(item) })

	q.Produce("A")
	q.Produce("B")
	q.Produce("C")

	q.Stop()

}
