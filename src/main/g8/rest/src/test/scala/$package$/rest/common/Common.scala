package $package$.rest.common

import $package$.rest.core._

trait Common {

  val noSuchItemException = AppException(NoSuchItemAppError.message)
}
