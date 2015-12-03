package $package$
package core
package actors

import java.util.UUID

import akka.actor.Actor
import $package$.persistence.TestDataMapper
import com.github.nscala_time.time.Imports._

object TestActor {

  case class Get(id: UUID)
  case class GetTop(start: Int, limit: Int)
}

class TestActor(val testMapper: TestDataMapper) extends Actor with TestOperations {

  import TestActor._

  def receive = {
    case GetTop(start, limit) =>
      sender ! testMapper.getTop(start, limit)

    case Get(id) =>
      testMapper.findOne(id) match {
        case Some(f) =>
          sender ! f
        case None =>
          sender ! NoSuchItem
      }
  }
}


