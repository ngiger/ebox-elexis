package controllers.medication

import com.google.inject.{Inject, Singleton}
import controllers.Errors
import controllers.actions.Actions
import models.medication.{Formats, MedicationService}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.Controller
import Formats.medicationOrderWrites
import models.medication.dal.MedicationDAO

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class MedicationCtrl @Inject()(actions: Actions, mediationService: MedicationService, medicationDAO: MedicationDAO)(val messagesApi: MessagesApi) extends Controller with I18nSupport{

  import actions._

  def list(patientId: String, history: Boolean) = IsAuthenticated.async{implicit request =>

    mediationService.list(patientId, history).map(_.fold(
      e => BadRequest(Errors.toJson(e)),
      medicationOrders => Ok(Json.toJson(medicationOrders))
    ))

  }

  def detail(id: String) = IsAuthenticated.async{implicit request =>

    medicationDAO.detail(id).map(_.fold(
      e => BadRequest(Errors.toJson(e)),
      medicationOrders => Ok(Json.toJson(medicationOrders))
    ))

  }

  def save() = IsAuthenticated.async{implicit request =>

    Future(Ok(Json.obj("status" -> "success")))

  }

}
