package team.datasci.poc.utils

import team.datasci.poc.MetricsClientImplementation

import scala.compat.Platform
import scala.concurrent.{ExecutionContext, Future}


package object utils {
  private val client = new MetricsClientImplementation

  def recordTime[T](metricName: String)(f: => T): T = {
    val startTimeMs = Platform.currentTime
    val result = f
    val endTimeMs = Platform.currentTime
    client.synchronized {
      client.recordTime(metricName, endTimeMs - startTimeMs)
    }
    result
  }
  implicit val ec = ExecutionContext.global

  def recordTimeF[T](metricName: String)(f: => Future[T]): Future[T] = {
    val startTimeMs = Platform.currentTime
    val fResult = f
    // TODO: check if future is completed successfully
    fResult.onComplete { case _ =>
      val endTimeMs = Platform.currentTime
      client.synchronized {
        client.recordTime(metricName, endTimeMs - startTimeMs)
      }
    }
    fResult
  }
}
