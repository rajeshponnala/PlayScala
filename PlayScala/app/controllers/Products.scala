package  controllers

import play.api.mvc.{Action, Controller}
import models.Product
import play.api.data.Form
import play.api.data.Forms.{mapping,longNumber,nonEmptyText}
import play.api.i18n.Messages
/** Uncomment the following lines as needed **/
/**
import play.api.Play.current
import play.api.libs._
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import java.util.concurrent._
import scala.concurrent.stm._
import akka.util.duration._
import play.api.cache._
import play.api.libs.json._
**/

object Products extends Controller {
  private val ProductForm:Form[Product]=Form(mapping("ean"->longNumber.verifying("validation.ean.duplicate",Product.findByEan(_).isEmpty),
       "name"->nonEmptyText,
       "description"->nonEmptyText
  	)(Product.apply)(Product.unapply)
)

  def list = Action{
  	 Ok(views.html.products.list(Product.findAll))
  }
  
  def show(ean: Long) = Action{
  	   Product.findByEan(ean).map { product =>
        Ok(views.html.products.details(product))
       }.getOrElse(NotFound)
  }			

}