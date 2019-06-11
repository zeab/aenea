package zeab.aenea

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO

object Mine extends App {

  def f(a:Int): (Int, String) ={
    val result = a * 2
    (result, s"\nf result: $result")
  }

  def g(a:Int): (Int, String) ={
    val result = a * 2
    (result, s"\ng result: $result")
  }

  def h(a:Int): (Int, String) ={
    val result = a * 2
    (result, s"\nh result: $result")
  }

  def bind(fun: Int => (Int, String), tup: Tuple2[Int, String]): (Int, String) = {
    val (intResult, stringResult) = fun(tup._1)
    (intResult, tup._2 + stringResult)
  }

  val fResult = f(100)
  val gResult = bind(g, fResult)
  val hResult = bind(h, gResult)

  println(s"result: ${hResult._1}, debug: ${hResult._2}")
}
