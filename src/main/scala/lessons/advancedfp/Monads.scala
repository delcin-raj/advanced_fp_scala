package lessons.advancedfp

object Monads extends App {

  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }
  object Attempt {
    // The role of apply is to create a new instance of
    // Attempt
    def apply[A](a: => A): Attempt[A] =
      try {
        // a gets evaluated during function call
        Success(a)
      } catch {
        case e: Throwable => Fail(e)
      }
  }

  case class Fail(e: Throwable) extends Attempt[Nothing] {
    override def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  case class Success[A](a: A) extends Attempt[A] {
    override def flatMap[B](f: A => Attempt[B]): Attempt[B] =
      try {
        f(a)
      } catch {
        case e: Throwable => Fail(e)
      }
  }
}
