package com.giampaolotrapasso.avrosamples.envelope

import java.io.ByteArrayOutputStream

import com.giampaolotrapasso.avrosamples.envelope.Envelope.Payload
import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream}
import shapeless.{:+:, CNil}

trait Message

case class Order(id: Int, product: String, quantity: Int) extends Message

case class Payment(id: Int, orderId: Int, value: Int) extends Message

case class Envelope(id: String,
                    envType: String,
                    version: Int,
                    occurredOn: Long = System.currentTimeMillis(),
                    message: Payload)
object Envelope {
  type Payload = Order :+: Payment :+: CNil
}

object EnvelopeSerializer {

  def serialize(o: Envelope): Array[Byte] = {
    val output = new ByteArrayOutputStream
    val avro   = AvroOutputStream.data[Envelope](output)
    avro.write(o.asInstanceOf[Envelope])
    avro.close()
    output.toByteArray
  }

  def deserialize(array: Array[Byte]): Envelope = {
    AvroInputStream.data[Envelope](array).iterator().next()
  }
}
