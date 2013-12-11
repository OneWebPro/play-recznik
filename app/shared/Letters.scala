package shared

/**
 * @author loki
 */
object Letters  extends Enumeration{

  type Letters = Value

  /**
   * Č č
   * Ć ć
   * Đ đ
   * Š š
   * Ž ž
   **/

  val C = Value(0, "Č")

  val c = Value(1, "č")

  val CC = Value(2, "Ć")

  val cc = Value(3, "ć")

  val D = Value(4, "Đ")

  val d = Value(5, "đ")

  val S = Value(6, "Š")

  val s = Value(7, "š")

  val Z = Value(8, "Ž")

  val z = Value(9, "ž")

}
