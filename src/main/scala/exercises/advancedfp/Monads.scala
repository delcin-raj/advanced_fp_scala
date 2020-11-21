package exercises.advancedfp

object Monads extends App {
  trait Monad[T] {
    def apply(a: T): Monad[T]
    def flatMap[B](f: T => Monad[B]): Monad[B]
//    def map[B](f: T => B): Monad[B] =
//      this.flatMap(x => Monad(f(x)))

    def flatten(m: Monad[Monad[T]]): Monad[T] =
      m.flatMap(x => x)
  }

  class Lazy[+A](value: => A) {
    lazy val onetime = value
    def get: A = onetime
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(value)
  }
  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value)
  }
  lazy val expr: Int = {
    println("Effect")
    55
  }
//  println(lazyInst.get)
//  println(lazyInst.get)
//  println(lazyInst.get)
  // why byname is not useful in the context of constructor and new keyword?
  // why gaurding new Lazy(value) actually makes it lazy?
  //println(lazyInst.get)
//  val flatInst = lazyInst.flatMap(x =>Lazy( 10*x))
//  val flatInst2 = lazyInst.flatMap(x =>Lazy( 20*x))
//  println(flatInst)
//  println(flatInst2)
}
