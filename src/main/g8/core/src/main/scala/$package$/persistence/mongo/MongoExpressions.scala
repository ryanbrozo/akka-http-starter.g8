package $package$.persistence.mongo

import java.util.UUID

import com.mongodb.casbah.Imports._

trait MongoExpressions extends SearchExpressions with SortExpressions

trait SearchExpressions {

  def entityIdFilter(id: UUID) = MongoDBObject("_id" -> id)
  def nameFilter(name: String) = MongoDBObject("name" -> name)

}

trait SortExpressions {

  type SortOrderType = Int

  def Ascending: SortOrderType = 1

  def Descending: SortOrderType = -1

  def naturalOrderingAscending = MongoDBObject("\$natural" -> Ascending)

  def naturalOrderingDescending = MongoDBObject("\$natural" -> Descending)

  def nameSort(sort: SortOrderType) = MongoDBObject("name" -> sort)
}
