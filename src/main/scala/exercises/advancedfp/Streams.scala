package exercises.advancedfp

import scala.annotation.tailrec

object Streams extends App {
  trait MyStream[+A] {
    class NoContent extends RuntimeException
    def isEmpty: Boolean
    def head: A
    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B]
    def ++[B >: A](other: => MyStream[B]): MyStream[B]

    def foreach(f: A=> Unit): Unit
    def map[B](f: A => B): MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]
    def filter(f: A => Boolean): MyStream[A]

    def take(n: Int): MyStream[A]
    def takeList(n: Int): List[A]

    @tailrec
    final def toList[B >: A] (acc: List[B] = Nil): List[B] =
      if (isEmpty) acc
      else tail.toList(head :: acc)

  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] =
      new Cons(start, MyStream.from(generator(start))(generator))
  }

  object Empty extends MyStream[Nothing] {
    def isEmpty: Boolean = true
    def head: Nothing = throw new NoContent
    def tail: MyStream[Nothing] = throw new NoContent

    def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, Empty)
    def ++[B >: Nothing](other: => MyStream[B]): MyStream[B] = other

    def foreach(f: Nothing => Unit): Unit = ()
    def map[B](f: Nothing => B): MyStream[B] = Empty
    def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = Empty
    def filter(f: Nothing => Boolean): MyStream[Nothing] = Empty

    def take(n: Int): MyStream[Nothing] = Empty
    def takeList(n: Int): List[Nothing] = Nil
  }

  // call by name is very important for lazy evaluation
  class Cons[+A](element: A,tail_x: => MyStream[A]) extends MyStream[A] {

    def isEmpty: Boolean = false
    // def head: A = element // override head as value so that it will be available for use everywhere else
    override val head: A = element
    // def tail: MyStream[A] = tail_x
    override lazy val tail: MyStream[A] = tail_x

    def #::[B >: A](element: B): MyStream[B] = {
      //lazy val postponed = new Cons[B](element,this)
      //postponed
      new Cons(element,this)
    }
    //def #::[B >: A](element: B): MyStream[B] = new Cons(element,tail_x)
    def ++[B >: A](other: => MyStream[B]): MyStream[B] = {
      // since #:: is lazily evaluated we don't have to declare a variable
      // lazy val res = other ++ tail_x
      lazy val res = other ++ tail
      new Cons(head , res) // ignoring the right ordering
    }
    // def ++[B >: A](other: MyStream[B]): MyStream[B] = new Cons(head,other ++ tail_x

    def foreach(f: A=> Unit): Unit = {
      f(head)
      tail.foreach(f)
    }

    def map[B](f: A => B): MyStream[B] = {
      lazy val tail_n = tail map f
      new Cons[B](f(head),tail_n)
    }

    def flatMap[B](f: A => MyStream[B]): MyStream[B] = {
      lazy val res = tail flatMap f
      f(head) ++ res
    }

    def filter(f: A => Boolean): MyStream[A] =
      if (f(head)) {
        lazy val res = tail filter f
//        head #:: res
        new Cons[A](head,res)
      } else {
        lazy val res = tail filter f
        res
      }

    def take(n: Int): MyStream[A] =
//      if (n > 0) head #:: (tail take (n-1)) does not work for large n. Should enquire why?
      if (n > 0) new Cons[A](head,tail take (n-1))
      else Empty

    def takeList(n: Int): List[A] =
      take(n).toList()
  }

  val naturals = MyStream.from(0)(x => x + 1)
  val first10 = naturals.take(1000000)
//  val concat = first10 ++ naturals
//  println(naturals.head)
//  println(naturals.tail.head)
//  println(naturals.tail.tail.head)
//  val strange = -1 #:: naturals
//  println(strange.head)
//  println(first10.toList())
//  first10.foreach(println)
  val even = naturals filter (x => x % 2 == 0)
  println(even.take(10).toList())
  val odd = naturals map (x => 2*x + 1)
  val divBy4 = even filter (x => x % 4 == 0)
  // naturals filter (x => x < 10) not a good way
//  val flatmaptest = {
//    naturals.flatMap(x => new Cons[Int](x,new Cons[Int](x+1,Empty))).
//      take(10).foreach(println)
//  }
//  val lessthan10 = naturals.filter (x => x < 10).take(10).toList()
  println(naturals.filter(_<=10).take(10).toList())

}
