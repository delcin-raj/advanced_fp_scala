package exercises.implicits

object TypeClasses extends App {
  // Equality type class
  trait Equality[T] {
    def equality(x: T,y: T): Boolean
  }
  class Rational(val x: Double,val y: Double)
  object Rational extends Equality[Rational] {
    def equality(x: Rational,y: Rational) =
      x.x == y.x && x.y == y.y
  }
}
