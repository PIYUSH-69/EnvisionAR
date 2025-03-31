import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.invictus.envisionar.VrScreen

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VrScreeen(args : VrScreen) {
    val modelUrl = "https://modelviewer.dev/shared-assets/models/Astronaut.glb" // Example 3D model

    val htmlData = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <script type="module" src="https://ajax.googleapis.com/ajax/libs/model-viewer/4.0.0/model-viewer.min.js"></script>
            <style>
                body { margin: 0; overflow: hidden; background-color: black; }
                model-viewer { width: 100vw; height: 100vh; }
            </style>
        </head>
        <body>
            <model-viewer 
                src="$modelUrl" 
                ar 
                auto-rotate 
                camera-controls>
            </model-viewer>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
            }
        }
    )
}
