package exercises.advancedfp

import exercises.advancedfp.Streams._

import scala.collection.mutable

object SpecialStreams extends App {
  val naturals = MyStream.from(1)(x => x + 1)
  //println(naturals.filter(x => ((x % 2 != 0) || (x==2)) && x != 1).take(20).toList())

  def primes(soFar: MyStream[Int]): MyStream[Int] =
    new Cons(soFar.head,primes(soFar.tail.filter(_ % soFar.head != 0)))

  // whether call byName is expensive?
  val primeStream = primes(MyStream.from(2)(x => x+1))
  println(primeStream.takeList(100))
  val fib = mutable.Map[Int,Int]()
  fib(1) = 1; fib(2) = 1
  def fibn(n:Int) =
    if (n == 1 || n == 2) 1
    else {
      fib(n) = fib(n-1) + fib(n-2)
      fib(n)
    }
  def fibonacci(first: BigInt, second: BigInt): MyStream[BigInt] =
    new Cons(first,fibonacci(second,first + second))
  val fibStream = naturals.map(n => fibn(n))
  println(fibonacci(1,1).takeList(10))

  def fibonacci_non_rec(first: BigInt, second: BigInt): MyStream[BigInt] =
    MyStream.from((first, second)) { case (curr, next) => (next, curr + next) }
    .map(_._1)
}
