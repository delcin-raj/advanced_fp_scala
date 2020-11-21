package exercises.advancedfp
import scala.collection.immutable.Set
import scala.collection.mutable

object PartialFunction extends App {
  val domain = Set("hi","who are you","what are avialable in store")
  val pf = new PartialFunction[String,String] {
    override def apply(v1: String): String = v1 match {
      case "hi" => "hello"
      case "who are you" => "computer I am"
      case "what are avialable in store" => "what is a store"
    }


    override def isDefinedAt(x: String): Boolean =
      domain contains x
  }

  scala.io.Source.stdin.getLines().foreach(line => println(pf(line)))
}
