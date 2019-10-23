package zeab.aenea.deserialize

//Imports
import zeab.aenea.MasterTest
import zeab.aenea.XmlDeserializer._
import zeab.aenea.modelsfortest.singleclasses.primitives._

class XmlDeserializerPrimitiveClassesTest extends MasterTest {

  test("Double: Deserialize") {
    val xml: String = validXml("Double", 1.1.toString)
    val obj: Either[Throwable, MyDoubleClass] = xml.fromXml[MyDoubleClass]()
    val expected: MyDoubleClass = MyDoubleClass(1.1)
    compareObj(obj, expected)
  }

  test("Float: Deserialize") {
    val xml: String = validXml("Float", 6.1.toString)
    val obj: Either[Throwable, MyFloatClass] = xml.fromXml[MyFloatClass]()
    val expected: MyFloatClass = MyFloatClass(6.1F)
    compareObj(obj, expected)
  }

  test("Long: Deserialize") {
    val xml: String = validXml("Long", 6.toString)
    val obj: Either[Throwable, MyLongClass] = xml.fromXml[MyLongClass]()
    val expected: MyLongClass = MyLongClass(6L)
    compareObj(obj, expected)
  }

  test("Int: Deserialize") {
    val xml: String = validXml("Int", 8.toString)
    val obj: Either[Throwable, MyIntClass] = xml.fromXml[MyIntClass]()
    val expected: MyIntClass = MyIntClass(8)
    compareObj(obj, expected)
  }

  test("Short: Deserialize") {
    val xml: String = validXml("Short", 8.toString)
    val obj: Either[Throwable, MyShortClass] = xml.fromXml[MyShortClass]()
    val expected: MyShortClass = MyShortClass(8)
    compareObj(obj, expected)
  }

  test("Boolean Serialize") {
    val xml: String = validXml("Boolean", false.toString)
    val obj: Either[Throwable, MyBooleanClass] = xml.fromXml[MyBooleanClass]()
    val expected: MyBooleanClass = MyBooleanClass(false)
    compareObj(obj, expected)
  }

  test("String Serialize") {
    val xml: String = validXml("String", "llama")
    val obj: Either[Throwable, MyStringClass] = xml.fromXml[MyStringClass]()
    val expected: MyStringClass = MyStringClass("llama")
    compareObj(obj, expected)
  }

}
