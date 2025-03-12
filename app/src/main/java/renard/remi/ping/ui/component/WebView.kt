package renard.remi.ping.ui.component

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.serialization.Serializable
import renard.remi.ping.domain.model.BaseRoute


@Serializable
data class WebViewRoute(val url: String) : BaseRoute

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(modifier: Modifier = Modifier, url: String) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = {
            android.webkit.WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.apply {
                    setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    allowContentAccess = true
                    allowFileAccess = true
                }
            }
        }, update = {
            it.loadUrl(url)
        }
    )
}