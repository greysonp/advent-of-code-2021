package d16.p2

import java.io.File
import java.lang.RuntimeException

fun main() {
  val binaryString: String = File("16/input.txt")
    .readLines()
    .first()
    .trim()
    .chunked(2)
    .map { it.toUInt(16).toUByte().toString(2).leftPad(8, '0') }
    .joinToString(separator = "")

  val packets: List<Packet> = parse(BinaryString(binaryString))

  println(packets[0].eval())
}

fun parse(data: BinaryString, packetLimit: Int = Int.MAX_VALUE): List<Packet> {
  val packets: MutableList<Packet> = mutableListOf()

  while (data.position < data.length - 8 && packets.size < packetLimit) {
    val version: Int = data.readInt(3)

    val type: Int = data.readInt(3)

    if (type == 4) {
      val literalString = StringBuilder()

      var group = "1"

      while (group.startsWith("1")) {
        group = data.readString(5)
        literalString.append(group.substring(1))
      }

      packets.add(Packet.LiteralPacket(version, literalString.toString().toULong(2)))
    } else {
      val lengthTypeId: Int = data.readInt(1)
      val subPackets: List<Packet> = if (lengthTypeId == 0) {
        val subPacketLength: Int = data.readInt(15)
        val subData = BinaryString(data.readString(subPacketLength))
        parse(subData)
      } else {
        val subPacketCount: Int = data.readInt(11)
        parse(data, subPacketCount)
      }

      packets.add(Packet.OperatorPacket(version, type, subPackets))
    }
  }

  return packets
}

class BinaryString(private val string: String) {

  var position: Int = 0
    private set

  val length: Int = string.length

  fun readInt(length: Int): Int {
    val value: Int = string.substring(position, position + length).toInt(2)
    position += length
    return value
  }

  fun readString(length: Int): String {
    val value: String = string.substring(position, position + length)
    position += length
    return value
  }
}

fun String.leftPad(targetLength: Int, char: Char): String {
  return char.toString().repeat((targetLength - this.length).coerceAtLeast(0)) + this
}

sealed class Packet(val version: Int, val type: Int) {

  abstract fun versionSum(): Int

  abstract fun eval(): ULong

  class LiteralPacket(version: Int, private val literal: ULong): Packet(version, 4) {
    override fun versionSum(): Int {
      return version
    }

    override fun eval(): ULong {
      return literal
    }
  }

  class OperatorPacket(version: Int, type: Int, private val packets: List<Packet>): Packet(version, type) {
    override fun versionSum(): Int {
      return version + packets.sumOf { it.versionSum() }
    }

    override fun eval(): ULong {
      return when (type) {
        0 -> packets.sumOf { it.eval() }
        1 -> {
          var product = 1UL
          packets.forEach { product *= it.eval() }
          product
        }
        2 -> packets.minOf { it.eval() }
        3 -> packets.maxOf { it.eval() }
        5 -> if (packets[0].eval() > packets[1].eval()) 1UL else 0UL
        6 -> if (packets[0].eval() < packets[1].eval()) 1UL else 0UL
        7 -> if (packets[0].eval() == packets[1].eval()) 1UL else 0UL
        else -> throw RuntimeException()
      }
    }
  }
}
