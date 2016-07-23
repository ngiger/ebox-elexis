package models.medication.dao

import play.api.test.PlaySpecification
import Converters._
import org.joda.time.DateTime

class ConverterSpec extends PlaySpecification with Samples{

  "Converters" should {

    "convert a XML into a DosageInstruction" in {

      val d1 = dosageInstructionConverter(dosageInstruction1)
      d1.text must equalTo(Some("1-1-0-1"))

      val d2 = dosageInstructionConverter(dosageInstruction2)
      d2.additionalInstructions.get.text.get must equalTo("Only if not on beta-Lactam")

    }

    "convert a XML into a MedicalOrder" in {

      val o1 = medicationOrderConverter(order1)
      o1.id must equalTo("Ff60020d714950f83034")
      o1.identifier.head must equalTo(o1.id)
      o1.dateWritten.get must equalTo(new DateTime(2016,7,22,9,54,35))
      o1.note.get must equalTo("Morgens-Mittags-Vor schlafengehen")
      o1.medicationCodeableConcept.codings.head.code.get must equalTo("A10BH01")
      o1.medicationCodeableConcept.text.get must equalTo("JANUVIA 100 mg Filmtabl")
      o1.dosageInstructions.head.text must equalTo(Some("1-1-0-1"))

    }
    "convert a XML into MedicalOrders" in {

      val orders = medicationOrdersConverter(orders1)
      orders.length must equalTo(4)

    }

    "convert a XML into an optional MedicalOrder" in {

      val order = mayBeMedicationOrderConverter(orders2)
      order.isEmpty must equalTo(true)

    }
  }

}