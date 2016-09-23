package com.giampaolotrapasso.avrosamples.test

import com.giampaolotrapasso.avrosamples.envelope.Envelope.Payload
import com.giampaolotrapasso.avrosamples.envelope.{Envelope, EnvelopeSerializer, Order}
import com.giampaolotrapasso.avrosamples.serializers.BinarySerializer
import org.apache.avro.file.SeekableByteArrayInput
import shapeless.{Coproduct, HNil}

class EnvelopeEncodingTest extends TestSpec {

  "EnvelopeEncodingTest" should "deserialize an enveloped order " in {

    val order    = Order(1, "book", 2)
    val envelope = Envelope(id = "1", envType = "Order", version = 1, message = Coproduct[Payload](order))

    val bytes: Array[Byte] = EnvelopeSerializer.serialize(envelope)
    // println("*** SIZE" + bytes.length)

    import com.sksamuel.avro4s.AvroSchema
    val schema = AvroSchema[Envelope]
    // println("schema " + schema)
    /*
  val in = new SeekableByteArrayInput(bytes)

  val result = deserialize[Envelope[Order]](SchemaRegistry.movieChanged(1), SchemaRegistry.movieChanged(2), in)

  result should matchPattern {
  case MovieChangedV2(`title`, `year`, "unknown") ⇒
   */
  }

}
