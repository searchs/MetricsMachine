package team.datasci.poc

import team.datasci.poc.utils.utils._

import scala.concurrent._
import scala.util.Random


object StatsdMetricsClient extends App {

  val client = new MetricsClientImplementation

  for (i <- 1 to 5000) {
    val inc = (1 + Random.nextInt(5)).toLong
    val time = (1 + Random.nextInt(5)).toLong
    val value = (1 + Random.nextInt(5)).toLong

    println(inc)
    client.incrementCount("ola.test.counter", inc)
    print(time)
    client.recordTime("ola.test.time", time)
    print(value)
    client.recordValue("ola.test.value", value)

    recordTimeF("thread.sleep.future") {

      Future {
        Thread.sleep(100)
      }
    }

    recordTime("thread.sleep") {
      Thread.sleep(100)
    }


  }

}
