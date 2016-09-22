package com.giampaolotrapasso.avrosamples.test

import java.io.ByteArrayInputStream

import com.giampaolotrapasso.avrosamples.SchemaRegistry
import com.giampaolotrapasso.avrosamples.events.{Event, MovieChangedV1, MovieChangedV2, MovieChangedV3}
import com.giampaolotrapasso.avrosamples.serializers.{BinarySerializer, DataWithSchemaSerializer}
import com.sksamuel.avro4s.{AvroInputStream, _}
import org.apache.avro.Schema
import org.apache.avro.file.SeekableByteArrayInput
import org.apache.avro.generic.{GenericDatumReader, GenericRecord}
import org.apache.avro.io.DecoderFactory

class DataEncodingDecodingTest extends TestSpec {

  val title     = "Raiders of lost ark"
  val year      = 1986
  val director  = "Spielberg"
  val wonOscars = 1

  "DataEncodingDecodingTest" should "deserialize an added field V1(title, year) to V2(title, year, director) " in {
    val obj                = MovieChangedV1(title, year)
    val bytes: Array[Byte] = DataWithSchemaSerializer.serializeV1(obj)
    val s                  = AvroInputStream.data[MovieChangedV2](bytes)

    // println("*** SIZE:" + bytes.length)

    val events = s.iterator.toList
    val result = events(0)

    result should matchPattern {
      case MovieChangedV2(`title`, `year`, "Burton") =>
    }
  }

  it should "deserialize a name change: V2(title, year, director) to V3(title, released_year, director) " in {
    val obj                = MovieChangedV2(title, year, director)
    val bytes: Array[Byte] = DataWithSchemaSerializer.serializeV2(obj)
    val s                  = AvroInputStream.data[MovieChangedV3](bytes)

    val events = s.iterator.toList
    val result = events(0)

    result should matchPattern {
      case MovieChangedV3(`title`, 1980, `director`) =>
    }
  }

}
