package com.giampaolotrapasso.avrosamples.test

import java.io.ByteArrayInputStream

import com.giampaolotrapasso.avrosamples.SchemaRegistry
import com.giampaolotrapasso.avrosamples.events._
import com.giampaolotrapasso.avrosamples.serializers.{BinarySerializer, ConfluentBinarySerializer}
import com.sksamuel.avro4s.{FromRecord, RecordFormat, ToRecord}
import org.apache.avro.Schema
import org.apache.avro.file.SeekableByteArrayInput
import org.apache.avro.generic.{GenericDatumReader, GenericRecord}
import org.apache.avro.io.DecoderFactory

class ConfluentBinaryEncodingDecodingTest extends TestSpec {

  val title     = "Raiders of lost ark"
  val year      = 1986
  val director  = "Spielberg"
  val wonOscars = 1

  def deserialize[A <: Event: ToRecord: FromRecord: RecordFormat](newSchema: Schema, stream: ByteArrayInputStream) = {

    val version   = stream.read()
    val oldSchema = SchemaRegistry.movieChanged(version)

    val gdr                   = new GenericDatumReader[GenericRecord](oldSchema, newSchema)
    val binDecoder            = DecoderFactory.get().binaryDecoder(stream, null)
    val record: GenericRecord = gdr.read(null, binDecoder)
    val format                = RecordFormat[A]
    format.from(record)
  }

  "ConfluentBinaryEncodingDecodingTest" should "deserialize an added field V1(title, year) to V2(title, year, director) " in {
    val obj                = MovieChangedV1(title, year)
    val bytes: Array[Byte] = ConfluentBinarySerializer.serializeV1(obj)

    val in = new SeekableByteArrayInput(bytes)

    val result = deserialize[MovieChangedV2](SchemaRegistry.movieChanged(2), in)

    result should matchPattern {
      case MovieChangedV2(`title`, `year`, "unknown") ⇒
    }
  }

  it should "deserialize a name change: V2(title, year, director) to V3(title, released_year, director) " in {
    val obj                = MovieChangedV2(title, year, director)
    val bytes: Array[Byte] = ConfluentBinarySerializer.serializeV2(obj)

    val in = new SeekableByteArrayInput(bytes)

    val result = deserialize[MovieChangedV3](SchemaRegistry.movieChanged(3), in)

    result should matchPattern {
      case MovieChangedV3(`title`, `year`, `director`) =>
    }
  }

  it should "deserialize a field drop and a new one of the same tipe : V3(title, released_year, director) to V4(title, director, wonOscars)" in {
    val obj                = MovieChangedV3(title, year, director)
    val bytes: Array[Byte] = ConfluentBinarySerializer.serializeV3(obj)

    val in = new SeekableByteArrayInput(bytes)

    val result = deserialize[MovieChangedV4](SchemaRegistry.movieChanged(4), in)

    result should matchPattern {
      case MovieChangedV4(`title`, `director`, 0) =>
    }
  }

  it should "deserialize adding a complex parameter: V4(title, director, wonOscars) to V4(title, director, wonOscars, releases)" in {
    val obj                = MovieChangedV4(title, director, wonOscars)
    val bytes: Array[Byte] = ConfluentBinarySerializer.serializeV4(obj)

    val in = new SeekableByteArrayInput(bytes)

    val result = deserialize[MovieChangedV5](SchemaRegistry.movieChanged(5), in)

    result should matchPattern {
      case MovieChangedV5(`title`, `director`, `wonOscars`, _) =>
    }
  }

}
