//
//import models.{Redemption, Transaction}
//import zeab.aenea.XmlDeserializer._
//import zeab.aenea.XmlSerializer._
//
//object Main extends App {
//
//  //val x = Transaction("someid", "anotherthing", Vector.empty).asXml(Map("isvectorwrapped" -> "true"))
//  //val x = Transaction("someid", "anotherthing", Vector(Redemption("asd", 333), Redemption("wqq", 999))).asXml(Map("isvectorwrapped" -> "true"))
//  val x = Transaction("someid", "anotherthing", Vector.empty).asXml(Map("isvectorwrapped" -> "true"))
//  println(x)
//
//  //val y = "<transaction><transactionId>someid</transactionId><somethingElse>anotherthing</somethingElse><redemptions/></transaction>".fromXml[Transaction]()
//  val y = "<transaction><transactionId>someid</transactionId><somethingElse>anotherthing</somethingElse><redemptions><name>asd</name><amount>333.0</amount></redemptions><redemptions><name>wqq</name><amount>999.0</amount></redemptions></transaction>".fromXml[Transaction]()
//  //val y = "<transaction><transactionId>someid</transactionId><somethingElse>anotherthing</somethingElse><redemptions><redemption><name>asd</name><amount>333.0</amount></redemption><redemption><name>wqq</name><amount>999.0</amount></redemption></redemptions></transaction>".fromXml[Transaction](Map("isvectorwrapped" -> "true"))
//  //val y = "<transaction><transactionId>someid</transactionId><somethingElse>anotherthing</somethingElse><redemptions><redemption><name>asd</name><amount>333.0</amount></redemption><redemption><name>wqq</name><amount>999.0</amount></redemption></redemptions></transaction>".fromXml[Transaction]()
//  println(y)
//
//
//}
