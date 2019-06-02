package zeab.aenea

object M extends App {

  val x = Llama(List(("xx", 9, 8 , 2 , 3)))
  val y = XmlSerialize.xmlSerialize(x)
  println(y)

  val z = "<llama><x>xx</x><x>9</x><x>8</x><x>2</x><x>3</x></llama>"
  val a = XmlDeserialize.xmlDeserialize[Llama](z)
  println(a)

}


//https://leonard.io/blog/2017/01/an-in-depth-guide-to-deploying-to-maven-central/