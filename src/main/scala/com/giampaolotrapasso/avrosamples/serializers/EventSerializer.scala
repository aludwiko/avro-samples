package com.giampaolotrapasso.avrosamples.serializers

import java.io.ByteArrayOutputStream

import com.sksamuel.avro4s._
import org.apache.avro.file.SeekableByteArrayInput
import org.apache.avro.generic.{GenericDatumReader, GenericDatumWriter, GenericRecord}
import org.apache.avro.io.{DecoderFactory, EncoderFactory}

abstract class EventSerializer[T: ToRecord: FromRecord: SchemaFor] {

  type Event = T
  val eventSchema = implicitly[SchemaFor[T]].apply()

  protected val format = RecordFormat[T]

  protected val eventDatumWriter = new GenericDatumWriter[GenericRecord](eventSchema)
  protected val eventDatumReader = new GenericDatumReader[GenericRecord](eventSchema, eventSchema)

  //override val identifier: Int = EventSerializer.generateIdentifier()

  //override def manifest(o: AnyRef): String = o.getClass.getSimpleName

  def toBinary(o: AnyRef): Array[Byte] = {

    AvroOutputStream
    val output  = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get().binaryEncoder(output, null)
    val record  = format.to(o.asInstanceOf[T])
    eventDatumWriter.write(record, encoder)
    encoder.flush()
    output.close()
    output.toByteArray
  }

  def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = {
    val input   = new SeekableByteArrayInput(bytes)
    val decoder = DecoderFactory.get().binaryDecoder(input, null)
    val record  = eventDatumReader.read(null, decoder)
    format.from(record).asInstanceOf[AnyRef]
  }
}
