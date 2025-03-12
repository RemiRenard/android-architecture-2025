package renard.remi.ping.extension

fun String.removeAllAfterSlash() = this.replace("/.*$".toRegex(), "")
