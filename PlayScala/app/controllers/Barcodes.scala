package controllers

import play.api.mvc.{Action, Controller}
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

object Barcodes extends Controller {
  val ImageResolution=144
  def barcode(ean: Long) = Action{
  	import java.lang.IllegalArgumentException
  	val mimeType= "image/png"
  	try { 
  	  val imageData=ean13BarCode(ean,mimeType)
  	  Ok(imageData).as(mimeType)
  	} catch {
  	  case e: IllegalArgumentException =>
  	      BadRequest("Couldnt generate barcode . Error: "+ e.getMessage) 
  	}
  }

  def ean13BarCode(ean: Long, mimeType: String) = {
    import java.io.ByteArrayOutputStream
    import java.awt.image.BufferedImage
    import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
    import org.krysalis.barcode4j.impl.upcean.EAN13Bean
    val output:ByteArrayOutputStream = new ByteArrayOutputStream
    val canvas:BitmapCanvasProvider =
      new BitmapCanvasProvider(output, mimeType, ImageResolution,BufferedImage.TYPE_BYTE_BINARY, false, 0)
    val barcode = new EAN13Bean()
    barcode.generateBarcode(canvas, String valueOf ean)
    canvas.finish
   output.toByteArray
 }
  

}