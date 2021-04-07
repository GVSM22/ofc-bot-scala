package utils.implicits

object StringUtils {
  implicit class Formatter(string: String) {
    def removeQuotes(): String = string.replaceAll("\"", "")
  }
}
