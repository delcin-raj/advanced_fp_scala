package lessons.advancedfp

object PartialFunction extends App {
  val afunc = (s:Int) => s

  val fussyfunc = (x: Int) =>
    if (x == 1) 54
    else if (x == -91) 43
    else throw FunctionNotApplicableException
  object FunctionNotApplicableException extends RuntimeException

  val `a nicer fussyfunc` = (x: Int) => x match {
    case 1 => 54
    case -91 => 43
    case _ => throw FunctionNotApplicableException
  }

  // cleaner method
  // define a function for values only at 1 and -91
  // parital functions are based on pattern matching
  // PartialFunctions extends Functions i.e it is a subtype of Function

  val `a partial func`: PartialFunction[Int, Int] = {
    case 1 => 54
    case -91 => 43
  }

  println(`a partial func`(1))
//  println(`a partial func`(0)) error
  val lifted = `a partial func`.lift // [Int,Int] => [Int, Option[Int]]
  println(lifted(0))

  // combining two partial functions
  val pfchain = `a partial func`.orElse[Int,Int]{
    case 55 => 45
    case 1 => 0 // overriding the already defined value is not possible
  }
  println(pfchain(55))
  println(pfchain(-91))
  println(pfchain(1))

  // HOFs accept partial fucntions as well
  val aMapped = List(1,2,3).map {
    case 1 => 2
    case 2 => 4
    case 3 => 4 // if the function is not defined at any point in the collection
      // that will throw an error
  }
  /*
  * PF can only have one parameter type
  * */

}
