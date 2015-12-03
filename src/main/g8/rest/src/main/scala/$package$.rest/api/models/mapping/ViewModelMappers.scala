package $package$.rest.api.models.mapping

import $package$.domain.models._
import $package$.rest.api.models._

object ViewModelMappers {

  type ViewModelMapper[A, B] = A => B

  def mapper[A, B](implicit viewModelMapper: ViewModelMapper[A, B]) = viewModelMapper

  implicit object TestToTestViewMapper extends ViewModelMapper[Test, TestView] {

    def apply(v: Test) =
      TestView(
        v.id,
        v.name
      )
  }
}
