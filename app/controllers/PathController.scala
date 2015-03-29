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
 *
 * The main controller behind the system.
 * This  will expose Rest services to work with the Logistic Network.
 * Through [[routes]] file you can see what service is linked with what rest path.
 * The services has been designed to be async non-block
 *
 */
object PathController extends Controller {

  val path = PathService

  /**
   *
   * This service receives and create the [[LogisticNetwork]]
   *
   * @return HTTP 201 CREATED
   */
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

  /**
   *
   * This service receives the [[ShortestRequest]] as json, process the Shortest Path and returns a [[dto.ShortestResult]]
   *
   * @return An instance of [[dto.ShortestResult]]
   */
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
