package lessons.implicits

object OrganizingImplicits extends App {
  // Demo
  val test = List(1,4,3,5,3,33,43,3,92,-1)
  println(test.sorted)
  implicit val reverse = new Ordering[Int] {
    override def compare(x: Int, y: Int): Int =
      y - x
  }
  // implicits have priorities
  // implicits within a scope has to be unique
  // There is order of precedence
  // So that an implicit argument can have multiple candidate values
  // But in different scopes and hence no ambiguity
  println(test.sorted)
  /*
  * implicits:
  *   -var/val
  *   -object
  *   - accessor methods = defs with no parameters
  * */
  // Exercise
  case class Person(name: String,age: Int)
  implicit val personOrdering = new Ordering[Person] {
    override def compare(x: Person, y: Person): Int = {
 //     if (x.name == y.name) 0
 //     else if (x.name < y.name) -1
 //     else 1
      Ordering.String.compare(x.name,y.name)
    }
  }
  // implicits should be defined before use just like in c
  val persons = List(Person("xyz",2),Person("bb",32),Person("kgb",65))
  println(persons.sorted)

  /*
  * Implicit Scope
  *   - normal scope = Local scope
  *   - imported scope
  *   - companions of all types involved in the method signature
  * */
  // What to do if there are multiple good candidates for the implicit param?
  // Do packaging in objects i.e for each candidate create an object

}
