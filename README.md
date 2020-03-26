# Enumeratum MongoDB

[![Build Status](https://travis-ci.com/alonsodomin/enumeraum-mongodb.svg?branch=master)](https://travis-ci.com/alonsodomin/enumeraum-mongodb)
[![License](http://img.shields.io/:license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.alonsodomin/enumeratum-mongodb_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.alonsodomin/enumeratum-mongodb_2.12)

Extension for [Enumeratum](https://github.com/lloydmeta/enumeratum) and official MongoDB Scala Driver

## Getting started

Add the following to your `build.sbt`:

```scala
libraryDependencies += "com.github.alonsodomin" %% "enumeratum-mongodb" % <version>
```

## Usage

Define an enumerated value following the guidelines stated in the [Enumeratum](https://github.com/lloydmeta/enumeratum) documentation:

```scala
import enumeratum._
import enumeratum.mongodb._

sealed trait ShirtSize extends EnumEntry
object ShirtSize extends Enum[ShirtSize] with MongoDBEnum[ShirtSize] {
  case object Small  extends ShirtSize
  case object Medium extends ShirtSize
  case object Large  extends ShirtSize

  val values = findValues
}
```

To make it compatible with MongoDB Driver, we need to mix-in the `MongoDBEnum` trait into our enum definition as shown above, that will provide with the MongoDB codec generation code required to be abe to use this enum.

To have the codec available when communicating with MongoDB, we need to define a `CodecRegistry` as follows:

```scala
val enumCodecRegistry = CodecRegistry.fromProviders(ShirtSize.bsonCodecProvider)
```