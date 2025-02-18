package renard.remi.ping.extension

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import renard.remi.ping.R
import renard.remi.ping.domain.model.DataError
import renard.remi.ping.domain.model.Result

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.NOT_FOUND -> UiText.StringResource(
            R.string.not_found
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Network.BAD_REQUEST -> UiText.StringResource(
            R.string.http_error_400
        )

        DataError.Network.UNAUTHORIZED -> UiText.StringResource(
            R.string.http_error_401
        )

        DataError.Network.CONFLICT -> UiText.StringResource(
            R.string.http_error_409
        )

        DataError.Network.UNPROCESSABLE_ENTITY -> UiText.StringResource(
            R.string.http_error_422
        )

        DataError.Network.UNAVAILABLE_SERVICE -> UiText.StringResource(
            R.string.http_error_503
        )

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        DataError.Local.REQUEST_ERROR -> UiText.StringResource(R.string.error_disk_request)

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )
    }
}

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> LocalContext.current.getString(id, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}