package com.giampaolotrapasso.avrosamples.test

import java.io.ByteArrayInputStream

import com.giampaolotrapasso.avrosamples.SchemaRegistry
import com.giampaolotrapasso.avrosamples.events.{Event, MovieChangedV1, MovieChangedV2, MovieChangedV3}
import com.giampaolotrapasso.avrosamples.serializers.{BinarySerializer, DataWithSchemaSerializer, JsonSerializer}
import com.sksamuel.avro4s.{AvroInputStream, FromRecord, RecordFormat, ToRecord}
import org.apache.avro.Schema
import org.apache.avro.file.SeekableByteArrayInput
import org.apache.avro.generic.{GenericDatumReader, GenericRecord}
import org.apache.avro.io.DecoderFactory

class JsonEncodingDecoding extends TestSpec {
  val title     = "Raiders of lost ark"
  val year      = 1986
  val director  = "Spielberg"
  val wonOscars = 1

  def deserialize[A <: Event: ToRecord: FromRecord: RecordFormat](oldSchema: Schema,
                                                                  newSchema: Schema,
                                                                  stream: ByteArrayInputStream) = {
    val gdr                   = new GenericDatumReader[GenericRecord](oldSchema, newSchema)
    val binDecoder            = DecoderFactory.get().jsonDecoder(newSchema, stream)
    val record: GenericRecord = gdr.read(null, binDecoder)
    val format                = RecordFormat[A]
    format.from(record)
  }

  "BinaryEncodingDecodingTest" should "deserialize an added field V1(title, year) to V2(title, year, director) " in {
    val obj                = MovieChangedV1(title, year)
    val bytes: Array[Byte] = JsonSerializer.serializeV1(obj)
    //println("*** Json Size" + bytes.length)

    val in = new SeekableByteArrayInput(bytes)

    val result = deserialize[MovieChangedV2](SchemaRegistry.movieChanged(1), SchemaRegistry.movieChanged(2), in)

    result should matchPattern {
      case MovieChangedV2(`title`, `year`, "unknown") ⇒
    }
  }

}
