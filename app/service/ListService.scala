package service

import database.{ServiceError, ErrorService}
import shared._
import dao._
import tables._

/**
 * @author loki
 */
object ListService extends ErrorService {

  /**
   * Find polish list of words
   * @param page Polish page request
   * @return
   */
  def findPolish(page : SortPolishList) : Either[ServiceError, List[PolishWord]] = withError {
    implicit session =>
    PolishWordTable.pagePolishList(Page(page.page,page.size,getSort(page.find)))
  }

  /**
   * Find serbian list of words
   * @param page Serbian page request
   * @return
   */
  def findSerbian(page: SortSerbianList): Either[ServiceError, List[SerbianWord]] = withError {
    implicit session =>
      SerbianWordTable.pageSerbianList(Page(page.page, page.size, getSort(page.find)))
  }

  /**
   * Change sorting string
   * @param sort Sort type
   * @return
   */
  private def getSort(sort:String) : String = {
    if(sort != "%"){
      sort + "%"  // TODO: check if we should add % before sort string
    }else{
      sort
    }
  }

}
