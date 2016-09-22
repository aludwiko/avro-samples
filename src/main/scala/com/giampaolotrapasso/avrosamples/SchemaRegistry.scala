package com.giampaolotrapasso.avrosamples

import org.apache.avro.Schema



object SchemaRegistry {

  implicit class StringToSchema(json: String) {
    def toSchema: Schema = new (Schema.Parser).parse(json)
  }

  val movieChanged: Map[Int, Schema] = Map(
      1 →
        """
          |{
          |  "type" : "record",
          |  "name" : "MovieChanged",
          |  "version" : 1,
          |  "namespace" : "foo.bar",
          |  "fields" : [
          |   { "name" : "title", "type" : "string" },
          |   { "name" : "year", "type" : "int" }
          |  ]
          |}
      """.stripMargin.toSchema,
      2 →
        """
          |{
          |  "type" : "record",
          |  "name" : "MovieChanged",
          |  "version" : 2,
          |  "namespace" : "foo.bar",
          |  "fields" : [
          |   { "name" : "title", "type" : "string" },
          |   { "name" : "year", "type" : "int" },
          |   { "name" : "director", "type" : "string", "default": "unknown" }
          |  ]
          |}
      """.stripMargin.toSchema,
      3 →
        """
          |{
          |  "type" : "record",
          |  "name" : "MovieChanged",
          |  "version" : 3,
          |  "namespace" : "foo.bar",
          |  "fields" : [
          |   { "name" : "title", "type" : "string"},
          |   { "name" : "released_year", "type" : "int", "aliases" : ["year"] },
          |   { "name" : "director", "type" : "string", "default" : "unknown" }
          |  ]
          |}
      """.stripMargin.toSchema,
      4 →
        """
          |{
          |  "type" : "record",
          |  "name" : "MovieChanged",
          |  "version" : 3,
          |  "namespace" : "foo.bar",
          |  "fields" : [
          |   { "name" : "title", "type" : "string", "default" : "" },
          |   { "name" : "director", "type" : "string", "default" : "unknown" },
          |   { "name" : "wonOscars", "type" : "int", "default" : 0 }
          |  ]
          |}
      """.stripMargin.toSchema,
      5 →
        """
          |{
          |  "type" : "record",
          |  "name" : "MovieChanged",
          |  "version" : 5,
          |  "namespace" : "foo.bar",
          |  "fields" : [
          |   { "name" : "title", "type" : "string", "default" : "" },
          |   { "name" : "director", "type" : "string", "default" : "unknown" },
          |   { "name" : "wonOscars", "type" : "int", "default" : 0 },
          |   { "name" : "releases", "type" : { "type": "map", "values" : "int" }, "default" : {  } }
          |  ]
          |}
      """.stripMargin.toSchema
  )
}
