package zeab.aenea

import org.scalatest.{Assertion, FunSuite}

class MasterSuite extends FunSuite{

  def compareErrorResults(expected: String, actual: Either[Throwable, String]): Assertion ={
    actual match {
      case Right(_) => fail(s"$expected does not equal $actual")
      case Left(ex) =>
        val hhh = ex.getCause.toString
        val jj = ex.getCause.getMessage
//        val gg = ex.getCause.getCause.toString
        assert(ex.getCause.getMessage == expected, ex)
    }
  }

  def compareResults(expected: String, actual: Either[Throwable, String]): Assertion ={
    actual match {
      case Right(xml) => assert(xml == expected, s"$expected does not equal $xml")
      case Left(ex) => fail(ex)
    }
  }

  def compareObj[T](actual: Either[Throwable, T], expected: T): Assertion ={
    actual match{
      case Right(value) => assert(value == expected)
      case Left(ex) => fail(ex)
    }
  }

  def validXml(key: String, value: String): String =
    s"<my${key}Class><my$key>$value</my$key></my${key}Class>"

}
