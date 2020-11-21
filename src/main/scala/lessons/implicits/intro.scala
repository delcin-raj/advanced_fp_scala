package lessons.implicits

object intro extends App {
  val pair = "Delcin" -> "raj" // first encounter of implicit method. creates a tuple

  case class Person(name: String) {
    def greet = s"Hi $name"
  }
  implicit def strToPerson(string: String): Person = Person(string)
  println("Someonne".greet) // but greet method is not the member of a String class
  // How does it work?
  // The compiler searches for all the definitions in the scope! so that
  // String object can be converted to an object which has the method named greet
  // of course greet should not take any params
  // line 10 -> println(strToPerson("Someone").greet
  // compiler assumes that there is an unique implicit
  class A {
    def greet: Int = 5
  }
  // implicit def strToa(string: String): A = new A ambiguity

  // implicit params
}
