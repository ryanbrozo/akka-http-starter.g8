package $package$.domain.models

import $package$.util.Imports._
import org.joda.time.DateTime
import org.scalatest._

class TestSpec extends FlatSpec with Matchers {

  "Test" should "not accept a blank name" in {

    an [IllegalArgumentException] should be thrownBy Test(
      generateId,
      ""
    )
  }
}
