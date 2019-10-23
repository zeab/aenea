package zeab.aenea

import org.scalatest.{Assertion, FunSuite}

class MasterSpec extends FunSuite{

  def compareErrorResults(expected: String, actual: Either[Throwable, String]): Assertion ={
    actual match {
      case Right(_) => fail(s"$expected does not equal $actual")
      case Left(ex) => assert(ex.getMessage == expected, ex)
    }
  }

  def compareResults(expected: String, actual: Either[Throwable, String]): Assertion ={
    actual match {
      case Right(xml) => assert(xml == expected, s"$expected does not equal $xml")
      case Left(ex) => fail(ex)
    }
  }

}
