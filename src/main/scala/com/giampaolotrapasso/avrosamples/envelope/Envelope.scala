package com.giampaolotrapasso.avrosamples.envelope

import java.io.ByteArrayOutputStream

import com.giampaolotrapasso.avrosamples.envelope.Envelopes.OrderWithEnvelope
import com.giampaolotrapasso.avrosamples.events.MovieChangedV1
import com.sksamuel.avro4s._
import org.apache.avro.Schema
import org.apache.avro.Schema.Field
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.{BinaryEncoder, EncoderFactory}
import org.apache.avro.specific.SpecificDatumWriter


trait Message

case class Order(id: Int, product: String, quantity: Int) extends Message

case class Payment(id: Int, orderId: Int, value: Int)  extends Message

case class Envelope[T <: Message](id: String,
                                  envType: String,
                                  version: Int,
                                  occurredOn: Long = System.currentTimeMillis(),
                                  message: T)

object Envelopes {
  type OrderWithEnvelope = Envelope[Order]
}


object EnvelopeSerializer {





  def serializeOrder(o: Envelope[Order]): Array[Byte] = {

    val format = RecordFormat[Envelope[Order]]

    val writer = new SpecificDatumWriter[GenericRecord](schema)
    val out = new ByteArrayOutputStream()
    val encoder: BinaryEncoder = EncoderFactory.get().binaryEncoder(out, null)
    writer.write(genericUser, encoder)
    encoder.flush()
    out.close()

    val serializedBytes: Array[Byte] = out.toByteArray()



      val output = new ByteArrayOutputStream


    // record is of type GenericRecord
    val record: GenericRecord = format.to(o)



      val avro   = AvroOutputStream.data[GenericRecord](output)
      avro.write(o.asInstanceOf[GenericRecord])
      avro.close()
      output.toByteArray
    }
/*
  def serializePayments(o: AnyRef): Array[Byte] = {
    implicit val schemaFor = SchemaFor[Envelope[Payment]]

    val output = new ByteArrayOutputStream
    val avro   = AvroOutputStream.data[Envelope[Payment]](output)
    avro.write(o.asInstanceOf[Envelope[Payment]])
    avro.close()
    output.toByteArray
  }
  */



}