### Aenea
 An automatic xml case class serializer and deserializer using reflection.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.zeab/aenea_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.zeab/aenea_2.12)
[![Build Status](https://travis-ci.org/zeab/aenea.svg?branch=master)](https://travis-ci.org/zeab/aenea)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/cd50df7f597e4619a8d0b615a1955fb1)](https://www.codacy.com/app/zeab/aenea?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=zeab/aenea&amp;utm_campaign=Badge_Grade)

Xml Serialize:
```scala
import zeab.aenea.XmlSerialize._
case class MyDoubleClass(myDouble:Double) 
val obj: MyDoubleClass = MyDoubleClass(1.1)
val serializedXml: Either[Throwable, String] = xmlSerialize(obj)
```

Xml Deserialize:
```scala
import zeab.aenea.XmlDeserialize._
case class MyBooleanClass(myBoolean:Boolean)
val expectedType: String = "MyBooleanClass"
val xml: String = "<myBooleanClass><myBoolean>false</myBoolean></myBooleanClass>"
val obj = xmlDeserialize[MyBooleanClass](xml)
```
