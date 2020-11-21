package exercises.advancedfp

import exercises.advancedfp.CustomSet.MySet

import scala.collection.immutable

object ListSet {
  class Empty[A] extends MySet[A] {
    override def apply(elem: A): Boolean = contains(elem)

    def contains(elem:A): Boolean = false
    def +(elem: A): MySet[A] = new Cons[A](elem,new Empty[A])
    def ++(anotherSet: MySet[A]): MySet[A] = anotherSet
    def isEmpty:  Boolean = true

    def map[B](f: A => B): MySet[B] = new Empty[B]
    def flatMap[B](f: A => MySet[B]): MySet[B] = new Empty[B]
    def filter(predicate: A => Boolean): MySet[A] = new Empty[A]
    def foreach(f: A=> Unit): Unit = ()

    def getElements: immutable.Set[A] = Set()

    override def -(elem: A): MySet[A] = new Empty[A]

    override def &(other: MySet[A]): MySet[A] = new Empty[A]

    override def --(other: MySet[A]): MySet[A] = new Empty[A]

    override def unary_! : MySet[A] =
      new PropertySet[A](_ => true)
  }

  class PropertySet[A](prop: A => Boolean) extends MySet[A] {

    override def apply(v1: A): Boolean =
      contains(v1)

    object Hard extends RuntimeException

    def contains(elem:A): Boolean = prop(elem)
    def +(elem: A): MySet[A] = new PropertySet[A](x => prop(x) || x == elem)
    def ++(anotherSet: MySet[A]): MySet[A] = new PropertySet[A](x => prop(x) || anotherSet.contains(x))
    def isEmpty:  Boolean = throw Hard

    def map[B](f: A => B): MySet[B] = throw Hard
    def flatMap[B](f: A => MySet[B]): MySet[B] = throw Hard
    def filter(predicate: A => Boolean): MySet[A] = new PropertySet[A](x => prop(x) || predicate(x))
    def foreach(f: A=> Unit): Unit = throw Hard

    def getElements: immutable.Set[A] = throw Hard

    //    def -(elem:A): MySet[A] = new PropertySet[A](x => prop(x) && x != elem)
    override def -(elem: A): MySet[A] = filter(x => x != elem)
    def &(other: MySet[A]): MySet[A] = filter(other)
    def --(other: MySet[A]): MySet[A] = filter(!other)

    def unary_! : MySet[A] = new PropertySet[A](x => !prop(x))
  }

  class Cons[A](head: A,tail: MySet[A]) extends MySet[A] {

    override def apply(v1: A): Boolean = contains(v1)

    def contains(elem:A): Boolean =
      elem == head || (tail contains elem)

    def +(elem: A): MySet[A] = {
      if (this contains elem) this
      else new Cons(elem,this)
    }

    def ++(anotherSet: MySet[A]): MySet[A] =
      (tail ++ anotherSet) + head

    def isEmpty:  Boolean = false

    def map[B](f: A => B): MySet[B] =
      new Cons[B](f(head),tail map f)

    def flatMap[B](f: A => MySet[B]): MySet[B] =
      f(head) ++ (tail flatMap f)

    def filter(predicate: A => Boolean): MySet[A] = {
      val filteredTail = tail filter predicate
      if (predicate(head)) filteredTail + head
      else filteredTail
    }

    def foreach(f: A=> Unit): Unit = {
      f(head)
      tail foreach f
    }

    def getElements: immutable.Set[A] =
      tail.getElements + head

    override def -(elem: A): MySet[A] =
      if (head == elem) tail
      else (tail - elem) + head

    override def &(other: MySet[A]): MySet[A] = {
//      def loop(set: MySet[A],acc: MySet[A]): MySet[A] = {
//        if (other contains head) loop(tail,acc + head)
//        else loop(tail,acc)
//      }
//      loop(this,new Empty[A])
      filter(other)
    }


    override def --(other: MySet[A]): MySet[A] = {
      //var res: MySet[A] = this
      //other foreach(e => res = res - e)
      //res
      filter(!other)
    }

    override def unary_! : MySet[A] =
      new PropertySet[A](x => !(this contains x))

  }
}
