package lessons.intro

object AdvancedPatternMatching extends App {
  // Available constructs
  /*
  * constants
  * wildcards
  * case classes
  * tuples
  * some special magic like above
  * */
  // But how do they work actually?
  // how to make a class compatible with case matching?
  class Person(val name: String,val age: Int)
  // we need an singleton object with proper unapply method

  object Person {
    // deconstructing
    // unapply method could be overloaded
    def unapply(arg: Person): Option[(String, Int)] = Some((arg.name,arg.age))
  }
  val bob = new Person("bob",13)
  val greeting: String = bob match {
    case Person(str, i) => s"greeting to $str of age $i"
  }
  println(greeting)
  // finish pattern matching exercise

  // infix patterns -> algebraic product datatype
  case class Or[A,B](a:A,b:B)
}
