package lessons.implicits

object PimpMyLibrary extends App {
  // for optimization extend AnyVal
  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value%2 == 0
    def sqrt: Double = math.sqrt(value)
  }
  println(43.isEven) // -> new RichInt(43).isEven
  println(54.sqrt)

  // For a type there can only be one implicit conversion
  // In the accessible scope


}
