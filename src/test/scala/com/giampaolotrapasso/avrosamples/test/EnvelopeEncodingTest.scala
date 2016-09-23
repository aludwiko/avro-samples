package com.giampaolotrapasso.avrosamples.test

import com.giampaolotrapasso.avrosamples.envelope.{Envelope, EnvelopeSerializer, Order}
import com.giampaolotrapasso.avrosamples.serializers.BinarySerializer
import org.apache.avro.file.SeekableByteArrayInput


class EnvelopeEncodingTest extends TestSpec {




"EnvelopeEncodingTest" should "deserialize an enveloped order " in {



  val order                = Order(1, "book", 2)
  val envelope = Envelope[Order](id = 1, envType = "Order", version = 1, message = order)

  val bytes: Array[Byte] = EnvelopeSerializer.serializeOrder(envelope)
  println("*** SIZE" + bytes.length)

  import com.sksamuel.avro4s.AvroSchema
  val schema = AvroSchema[Envelope[Order]]
  println("schema " + schema)
/*
  val in = new SeekableByteArrayInput(bytes)

  val result = deserialize[Envelope[Order]](SchemaRegistry.movieChanged(1), SchemaRegistry.movieChanged(2), in)

  result should matchPattern {
  case MovieChangedV2(`title`, `year`, "unknown") ⇒
  */
}



}
