package exercises.advancedfp

import exercises.advancedfp.ListSet.Empty

import scala.collection.immutable
object CustomSet {

  trait MySet[A] extends (A => Boolean){
    def contains(elem:A): Boolean
    def +(elem: A): MySet[A]
    def ++(anotherSet: MySet[A]): MySet[A]
    def isEmpty:  Boolean

    def map[B](f: A => B): MySet[B]
    def flatMap[B](f: A => MySet[B]): MySet[B]
    def filter(predicate: A => Boolean): MySet[A]
    def foreach(f: A=> Unit): Unit

    def getElements: immutable.Set[A]
    def -(elem:A): MySet[A]
    def &(other: MySet[A]): MySet[A]
    def --(other: MySet[A]): MySet[A]

    def unary_! : MySet[A]
  }

//  class EmptySet[A] extends MySet[A] {

//    override def apply(v1: A): Boolean = false


//    def contains(elem:A): Boolean = false
//    def +(elem: A): MySet[A] = new NonEmptySet[A](Set(elem))
//    def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

//    def map[B](f: A => B): MySet[B] = new EmptySet[B]
//    def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
//    def filter(predicate: A => Boolean): MySet[A] = new EmptySet[A]
//    def foreach(f: A=> Unit): Unit = new NothingToDoException
//  }
//  class NothingToDoException extends RuntimeException

  class CustomSet[A](elements: Set[A]) extends MySet[A]{
    override def apply(v1: A): Boolean = this contains v1


    def contains(elem:A): Boolean = elements contains elem
    def +(elem: A): MySet[A] = new CustomSet[A](elements + elem)
    def ++(anotherSet: MySet[A]): MySet[A] = new CustomSet[A](elements ++ anotherSet.getElements)

    override def isEmpty: Boolean = elements.isEmpty

    def map[B](f: A => B): MySet[B] = new CustomSet[B](elements map f)
    def flatMap[B](f: A => MySet[B]): MySet[B] =
      (elements map f).fold(new Empty[B])(_ ++ _)


    def filter(predicate: A => Boolean): MySet[A] = new CustomSet[A](elements filter predicate)
    def foreach(f: A=> Unit): Unit = elements foreach f

    override def getElements: Set[A] = elements

    override def -(elem: A): MySet[A] = new CustomSet(elements filter (x => x != elem))

    override def &(other: MySet[A]): MySet[A] =
      new CustomSet[A](elements intersect other.getElements)

    override def --(other: MySet[A]): MySet[A] =
      new CustomSet[A](elements diff other.getElements)

    override def unary_! : MySet[A] = this
  }

  object MySet{
    def apply[A](values: A*): MySet[A] = {
      new CustomSet[A](values.toSet)
    }
  }

}