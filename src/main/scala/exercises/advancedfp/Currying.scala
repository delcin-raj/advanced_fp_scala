package exercises.advancedfp
// It's all about functions with less number of params
object Currying extends App {
  // anonymous functions are actually objects and hence values
  val simpleAddFunction = (x: Int,y: Int) => x + y
  def simpleAddMethod(x: Int,y: Int): Int = x + y
  def curriedAddMethod(x: Int)(y: Int): Int = x + y

  // partially applied funtion is diiferent than partial functions
  val add7: Int => Int = y => simpleAddFunction(7,y)
  val add10 = (y: Int) => simpleAddFunction(10,y)
  val add12 = simpleAddFunction.curried(12)
  val add13 = simpleAddMethod(13,_: Int)

  val add2 = y => simpleAddMethod(2,y)
  // val add22 = simpleAddMethod.curried(22) does not works
  // complains that missing values for params
  val add22 = simpleAddMethod(2,_:Int) // ETA expansion results in anonymous functions

  val add0 = curriedAddMethod(0) _
  val add1 = (y: Int) => curriedAddMethod(1)(y)

  def ListFormatter(xs: List[Double],precision: String):List[String] = xs match {
    case Nil => Nil
    case x :: xs1 => precision.format(x) :: ListFormatter(xs1,precision)
  }
  val format4_2f = ListFormatter(_: List[Double],"%2.2f")
  println(format4_2f(List(100,1000,1.222222,3.1111)))

  // => byName
  // function value is not the substitute for byName param
  def f(n: => Int) = n + 1 // compiler does not do eta expansion for f

}
