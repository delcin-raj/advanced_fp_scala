package lessons.implicits

object TypeClasses extends App {
  // Back to 101 about compilers
  // Compilers are tasked with finding the right sequence of operations
  // So we have to organize the code accordingly so that the compiler
  // can do its job
  trait HTMLwritable {
    def toHtml: String
  }
  // HTMLwritable is a type class
  case class User(name: String,age: Int,email: String) extends HTMLwritable {
    override def toHtml: String =
      s"<div>$name ($age yo) <a href=$email/></div>"
  }
  User("fed",43,"dev@dev.com").toHtml // works fine for smaller cases
  /*
  * Disadvantages
  * 1. Works only for the types we write (WDIM)
  * 2. One implementation of quite a number of them
  * */
  object HTMLSerializerPM {
    def serializeToHTML(value: Any) = value match {
      case User(a,b,c) =>
      case _ =>
    }
  }
  /*
  * Lost type safety
  * need to modify this code everytime
  * still one implementation
  * */
  // Still not a better design

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }
  // traits can also have companion objects
  // I guess every type can have a companion object
  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(value: User): String =
      s"<div>$value.name ($value.age yo) <a href=$value.email/></div>"
  }

  /*
  * We can define serializer for other types
  * */
  import java.util.Date
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(value: Date): String =
      s"<div>${value.toString()}</div>"
  }
  // Advantage of multiple serializers
  object AnonymousUserSerializer extends HTMLSerializer[User] {
    override def serialize(value: User): String =
      s"<div>${value.name}</div>"
  }
  // A type class specifies the set of functions should be implemented
  // They are extended and implemented by instances(object)
  // what is the difference between a normal class and the type class?
  // Type classes parameterize over types(classes)
  /**
   * Equality
   **/

  // part 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    // an even better design is to allow users to get hold of the
    // implicit value so they can access all the methods defined in
    // Trait[T]

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }
  // The idea is that we define a companion object O to trait T on type A
  // Then within that companion object we create all the definitions in T
  // With extra curried implicit param which is expects the value(Object)
  // of type T
  // since that object has all the methods defined on T on type A
  // If we define an object of Type T[A] and declare it implicit
  // within the appropriate scope then the compiler will
  // fetch that object automatically
  // That is given an object of type A we can easily call methods
  // defined in trait T[A] if an implicit object of type T[A]
  // is declared in the appropriate scope
  HTMLSerializer[User]

  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)
  }
  // we can do
  // user.toHTML(user serializer)
  // if the param of toHTML is made is implicit
  // we can do
  // user.toHTML

  // The order
  /**
   * Define type class
   * Create implicit Instance of type class
   * Define implicit classes for enrichment which can make user of
   *  implicit instances
   * */
}
