package lessons.intro

object DarkSugars {
  // #1 methods with single param
  def single(x: Int) = x*2
  val value = single{
    // write some code
    45
  }
  // Try class has an apply method with single param

  // #2 single abstract method
  trait Action {
    def act(x: Int,y: Int): Int // single abstract method
  }
  val anInstance:Action = (x: Int,y: Int) => x*y //annotation important
  // why it is useful?
  // Java way of using runnable
  val thread = new Thread(new Runnable {
    override def run(): Unit = println("java thread")
  })

  // scala way
  val sweetThread = new Thread(() => println("scala thread"))

  // #3 the :: and #:: methods are special -> postfix methods
  // scala spec -> last character determines it's associativity

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this // Actual implementation
  }
  val myStream = 1 -->: 2 -->: 4 -->: new MyStream[Int]

  // # 4 multi-word method naming `func name`

  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name gossiped $gossip")
  }

  // #5 infix types
  class Composite[A,B]
  val composite: Int Composite String = ???

  // #6 update() much like apply for mutable containers
  val anArry = Array(1,2,3,4)
  anArry(3) = 100 // anArray.update(3,100)

  // #7 setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0
    def member: Int = internalMember
    def member_=(value: Int): Unit = internalMember = value
  }
  val aMutableContainer = new Mutable
  aMutableContainer.member = 5 // aMutableContainer.member_= 5

}
