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

/**
  * Created by Giampaolo on 22/09/16.
  */
class ForwardBackwardCompatibility extends TestSpec {

  val title     = "Raiders of lost ark"
  val year      = 1986
  val director  = "Spielberg"
  val wonOscars = 1

  val topic = List(
    ConfluentBinarySerializer.serializeV5(MovieChangedV5(title, director, wonOscars, Map("Italy" -> 1983, "USA" -> 1979))),
    ConfluentBinarySerializer.serializeV4(MovieChangedV4(title, director, wonOscars)),
    ConfluentBinarySerializer.serializeV3(MovieChangedV3(title, year, director)),
    ConfluentBinarySerializer.serializeV2(MovieChangedV2(title, year, director)),
    ConfluentBinarySerializer.serializeV1(MovieChangedV1(title, year)))

  def deserialize[A <: Event: ToRecord: FromRecord: RecordFormat](newSchema: Schema,
                                                                  stream: ByteArrayInputStream) = {

    val version = stream.read()
    val oldSchema  = SchemaRegistry.movieChanged(version)

    val gdr                   = new GenericDatumReader[GenericRecord](oldSchema, newSchema)
    val binDecoder            = DecoderFactory.get().binaryDecoder(stream, null)
    val record: GenericRecord = gdr.read(null, binDecoder)
    val format                = RecordFormat[A]
    format.from(record)
  }

  "ForwardBackwardCompatibility" should "deserialize all to V1 " in {
    val list = topic.map{record =>
      deserialize[MovieChangedV1](SchemaRegistry.movieChanged(1), new SeekableByteArrayInput(record))
    }
    list.size should be(5)
  }

  "ForwardBackwardCompatibility" should "deserialize all to V2 " in {
    val list = topic.map{record =>
      deserialize[MovieChangedV2](SchemaRegistry.movieChanged(2), new SeekableByteArrayInput(record))
    }
    list.size should be(5)
  }

  "ForwardBackwardCompatibility" should "deserialize all to V3 " in {
    val list = topic.map{record =>
      deserialize[MovieChangedV3](SchemaRegistry.movieChanged(3), new SeekableByteArrayInput(record))
    }
    list.size should be(5)
  }

  "ForwardBackwardCompatibility" should "deserialize all to V4 " in {
    val list = topic.map{record =>
      deserialize[MovieChangedV4](SchemaRegistry.movieChanged(4), new SeekableByteArrayInput(record))
    }
    list.size should be(5)
  }

  "ForwardBackwardCompatibility" should "deserialize all to V5 " in {
    val list = topic.map{record =>
      deserialize[MovieChangedV5](SchemaRegistry.movieChanged(5), new SeekableByteArrayInput(record))
    }
    list.size should be(5)
  }



}
