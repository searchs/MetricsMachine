package team.datasci.poc

import com.timgroup.statsd.NonBlockingStatsDClient


trait MetricsClient {
  /**
   * Define the basic functions that should be implemented for dashboard metrics
   *
   * @param name   Name of metrics being timed
   * @param timeMs Current time in milliseconds
   */
  def recordTime(name: String, timeMs: Long): Unit

  def recordValue(name: String, value: Long): Unit

  def incrementCount(name: String, delta: Long = 1L): Unit

}


class MetricsClientImplementation extends MetricsClient {

  //  TODO:  Read only from config
  private val prefix = "gauges.metrics"
//  private val host = "172.18.0.2" //machine IP
  private val host = "127.0.0.1" //machine IP
  private val port = 8125

  private val client = new NonBlockingStatsDClient(prefix, host, port)

  /**
   * Define the basic functions that should be implemented for dashboard metrics
   *
   * @param name
   * @param timeMs
   */
  override def recordTime(name: String, timeMs: Long): Unit = {
    client.recordExecutionTime(name, timeMs)
  }

  override def recordValue(name: String, value: Long): Unit = {
    client.recordGaugeValue(name, value)
  }

  override def incrementCount(name: String, delta: Long): Unit = {
    client.count(name, delta)
  }
}