package service

import pl.onewebpro.database._
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
  def findPolish(page: SortPolishList): Either[ServiceError, ResultPage[PolishWord]] = withError {
    implicit session =>
      val search = getSort(page.find)
      ResultPage(
        page.page,
        PolishWordTable.pagePolishList(Page(page.page, page.size, search)),
        Math.round(PolishWordTable.findByLetter(search).list.length / page.size)
      )
  }

  /**
   * Find serbian list of words
   * @param page Serbian page request
   * @return
   */
  def findSerbian(page: SortSerbianList): Either[ServiceError, ResultPage[SerbianWord]] = withError {
    implicit session =>
      val search = getSort(TranslationService.translate(page.find))
      ResultPage(
        page.page,
        SerbianWordTable.pageSerbianList(Page(page.page, page.size, search)),
        Math.round(SerbianWordTable.findByLetter(search).list.length / page.size)
      )
  }

  /**
   * Change sorting string
   * @param sort Sort type
   * @return
   */
  def getSort(sort: String): String = {
    if (sort != "%") {
      sort.toLowerCase + "%"
    } else {
      sort.toLowerCase
    }
  }

}
