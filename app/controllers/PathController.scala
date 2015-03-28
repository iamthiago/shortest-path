package controllers

import dto.{LogisticNetwork, ShortestRequest}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{Action, BodyParsers, Controller}
import service.PathService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

/**
 * Created by thiago on 3/28/15.
 */
object PathController extends Controller {

  val path = PathService

  def createLogisticNetwork = Action.async(BodyParsers.parse.json) { request =>
    val result = request.body.validate[LogisticNetwork]
    result.fold(
      error => Future.successful(BadRequest(Json.obj("status" -> "Error", "message" -> JsError.toFlatJson(error)))),
      t => {
        val future = path.neoService.createLogisticNetwork(t)
        future.map { x =>
          Created
        }.recover {
          case e =>
            Logger.error("Something went wrong", e)
            InternalServerError(e.getMessage)
        }
      }
    )
  }

  def getShortestPathWithCost = Action.async(BodyParsers.parse.json) { request =>
    try {
      val result = request.body.validate[ShortestRequest]
      result.fold(
        error => Future.successful(BadRequest(Json.obj("status" -> "Error", "message" -> JsError.toFlatJson(error)))),
        t => {
          val future = path.neoService.getShortestPathWithCost(t)
          future.map { x =>
            Ok(Json.toJson(x))
          }.recover {
            case e =>
              Logger.error("Something went wrong", e)
              InternalServerError(e.getMessage)
          }
        }
      )
    } catch {
      case e: Exception =>
        Logger.error("Something went wrong", e)
        Future.successful(InternalServerError(e.getMessage))
    }
  }
}
