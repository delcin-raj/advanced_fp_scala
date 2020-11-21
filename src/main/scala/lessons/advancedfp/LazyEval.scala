package lessons.advancedfp

object LazyEval extends App {
  // val x: Int = throw new RuntimeException -> runtime error
  lazy val y: Int = throw new RuntimeException // does not crash
//  println(x) // crashes

  lazy val x: Int = {
    Thread.sleep(1000)
    println("Hello")
    3
  }
  println(x)
  println(x)

  // examples of implications

  def sideEffectCondition: Boolean = {
    println("boo")
    true
  }
  def simpleCondition = false
  println(if(simpleCondition && sideEffectCondition) "yes" else "no")

  // befor explaining this one should explain the difference between def and val
  // Usage of byName param
  def byName(x: => Int) = Thread.sleep(1000); println(x)
  def f: Int = {
    println(5)
    5
  }
  def withoutName(x: Int) = Thread.sleep(1000); println(x)
  byName(f)
  withoutName(f)

  def expensive = {
    Thread.sleep(1000)
    println("slept")
    56
  }
//  println(expensive)
//  println(expensive)
  lazy val shortCircuit = expensive
  println(shortCircuit)
  println(shortCircuit)

  // read about withFilter


}
