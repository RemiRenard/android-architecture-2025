package renard.remi.ping.domain.model

sealed interface DataError : Error {
    enum class Network : DataError {
        NOT_FOUND,
        BAD_REQUEST,
        UNAVAILABLE_SERVICE,
        CONFLICT,
        UNAUTHORIZED,
        UNPROCESSABLE_ENTITY,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        REQUEST_ERROR,
    }
}