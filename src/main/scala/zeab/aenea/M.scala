package zeab.aenea

object M extends App {

  val x = Moose("bert", 9, 10)
  val y = XmlSerialize.xmlSerialize(x)
  println(y)

  val z = "<moose><prop>bert</prop><prop>9</prop><prop>10</prop></moose>"
  val a = XmlDeserialize.xmlDeserialize[Moose](z)
  println(a)

}


//https://leonard.io/blog/2017/01/an-in-depth-guide-to-deploying-to-maven-central/