package lessons.advancedfp

object Currying extends App {
  // curried adder
  // usage of function values
  val superAdder: Int => Int => Int =
  x => y => x+y

  val add10 = superAdder(10) // Int => Int
  println(add10(4))

  // lifting = ETA expansion
  // functions != methods due to jvm limitation
  // when using methods in higher order functions the compiler converts
  // the methods to function values(lambdas) to work properly

  // the below definition is a method and cannot be treated as values
  def curriedAdder(x: Int)(y: Int): Int = x + y
  // val add10 = curriedAdder(10) will result in a compile time error
  // as the jvm is unable to distinguish between function definition and values
  val add15: Int => Int = curriedAdder(10) //works
  val add16 = curriedAdder(10) _
  // In both cases eta expansion works


  // anonymous functions are function values

  // power of ETA expansion for underscores
  def concatenator(a:String,b:String,c:String) = a + b+ c // most general function

  // a more concrete version
  val greeting = concatenator("Hello " , _:String , " how are you")
  println(greeting("Delcin"))

}
