import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._

case class Pay(id: Long, name: String, money: Double)

class HiActor extends Actor {

    def receive = {
        case "Hi" => println("Hello")
        case Pay(id, name, money) => {
            if (money < 10) {
                println(s"this money is less than 10, [$id $name $money]")
                sender ! Pay(id, name, money + 2.5)
            } else {
                println(s"this money is more than 10, [$id $name $money]")
            }
        }
    }

}

class HoActor extends Actor {

    def receive = {
        case "Hi" => println("Hello")
        case Pay(id, name, money) => {
            if (money < 10) {
                println(s"HoActor: this money is less than 10, [$id $name $money]")
                sender ! Pay(id, name, money + 2.5)
            } else {
                println(s"this money is more than 10, [$id $name $money]")
            }
        }
    }

}

object HiActor extends App {
    val system = ActorSystem("HelloWorld")

    val actor1 = system.actorOf(Props[HiActor])
    actor1 ! "Hi"
    actor1 ! Pay(111111111111L, "zz", 1.5)
    
    val actor2 = system.actorOf(Props[HoActor])
    actor2 ! "Ho"
    actor2 ! Pay(222222222222L, "zz", 3)
}

