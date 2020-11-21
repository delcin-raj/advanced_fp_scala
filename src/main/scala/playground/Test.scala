package playground

object Test extends App {
  Json.Obj(Map(
    "foo" -> Json.Num(0),
    "bar" -> Json.Arr(Json.Bool(true), Json.Bool(false))
  ))
}
