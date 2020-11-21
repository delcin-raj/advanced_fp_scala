package exercises.implicits

object PimpMyLibrary extends App {
  implicit class RichString(val value: String) extends AnyVal {
    def asInt = value.toInt
    def encrypt: String = {
      def encryptChar(c: Char): Char =
        ((c.toInt-48+23)%26).toChar
      value map (encryptChar)
    }
    def /:(int: Int) = value.toInt / int
  }

  implicit class RichInt(val value: Int) extends AnyVal {
    def times(op:() => Unit): Unit =
      (1 to value) foreach (_=>op())

    def *[T](list: List[T]): List[T] = {
      def loop(rem: Int): List[T] =
        if (rem <= 0) Nil
        else list ++ loop(rem - 1)

      loop(value)
    }

  }
  // Usage of implicit methods are discouraged

}
