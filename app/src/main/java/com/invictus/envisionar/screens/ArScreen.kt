package com.invictus.envisionar.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
import com.invictus.envisionar.R
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@SuppressLint("ResourceType")
class ArScreen : AppCompatActivity(R.layout.activity_main) {

    lateinit var sceneView: ARSceneView
    lateinit var loadingView: View
    lateinit var instructionText: TextView
    lateinit var modelurl: String

    var isLoading = false
        set(value) {
            field = value
            loadingView.isGone = !value
        }


    var anchorNode: AnchorNode? = null
        set(value) {
            if (field != value) {
                field = value
                updateInstructions()
            }
        }

    var anchorNodeView: View? = null

    var trackingFailureReason: TrackingFailureReason? = null
        set(value) {
            if (field != value) {
                field = value
                updateInstructions()
            }
        }

    fun updateInstructions() {
        instructionText.text = trackingFailureReason?.let {
            it.getDescription(this)
        } ?: if (anchorNode == null) {
            getString(R.string.point_your_phone_down)
        } else {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false
        )

         modelurl = intent.getStringExtra("MODEL_URL").toString()
        if (modelurl.isNullOrEmpty() ){
            modelurl = "https://piyush-69.github.io/3d-models/DamagedHelmet%20(1).glb"
        }
        //instructionText = findViewById(R.id.instructionText)
      //  instructionText.text = receivedString

        //        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar)?.apply {
        //            doOnApplyWindowInsets { systemBarsInsets ->
        //                (layoutParams as ViewGroup.MarginLayoutParams).topMargin = systemBarsInsets.top
        //            }
        //            title = ""
        //        })
        instructionText = findViewById(R.id.instructionText)
        loadingView = findViewById(R.id.loadingView)
        sceneView = findViewById<ARSceneView?>(R.id.sceneView).apply {
            lifecycle = this@ArScreen.lifecycle
            planeRenderer.isEnabled = true
            configureSession { session, config ->
                config.depthMode = when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
                config.instantPlacementMode = Config.InstantPlacementMode.DISABLED
                config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            }
            onSessionUpdated = { _, frame ->
                if (anchorNode == null) {
                    frame.getUpdatedPlanes()
                        .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                        ?.let { plane ->
                            addAnchorNode(plane.createAnchor(plane.centerPose))
                        }
                }
            }
            onTrackingFailureChanged = { reason ->
                this@ArScreen.trackingFailureReason = reason
            }
        }
        //        sceneView.viewNodeWindowManager = ViewAttachmentManager(context, this).apply { onResume() }
    }

    fun addAnchorNode(anchor: Anchor) {
        sceneView.addChildNode(
            AnchorNode(sceneView.engine, anchor)
                .apply {
                    isEditable = true
                    lifecycleScope.launch {
                        isLoading = true
                        buildModelNode()?.let { addChildNode(it) }
                        //                        buildViewNode()?.let { addChildNode(it) }
                        isLoading = false
                    }
                    anchorNode = this
                }
        )
    }

    suspend fun buildModelNode(): ModelNode? {
        // Load the model from the assets directory
        sceneView.modelLoader.loadModelInstance(modelurl)?.let { modelInstance ->
            return ModelNode(
                modelInstance = modelInstance,
                // Scale to fit in a 0.5 meters cube
                scaleToUnits = 0.5f,
                // Bottom origin instead of center so the model base is on floor
                centerOrigin = Position(y = -0.5f)
            ).apply {
                isEditable = true
            }
        }
        return null
    }

    //    suspend fun buildViewNode(): ViewNode? {
    //        return withContext(Dispatchers.Main) {
    //            val engine = sceneView.engine
    //            val materialLoader = sceneView.materialLoader
    //            val windowManager = sceneView.viewNodeWindowManager ?: return@withContext null
    //            val view = LayoutInflater.from(materialLoader.context).inflate(R.layout.view_node_label, null, false)
    //            val ViewAttachmentManager(context, this).apply { onResume() }
    //            val viewNode = ViewNode(engine, windowManager, materialLoader, view, true, true)
    //            viewNode.position = Position(0f, -0.2f, 0f)
    //            anchorNodeView = view
    //            viewNode
    //        }
    //    }

}

fun Activity.setFullScreen(
    rootView: View,
    fullScreen: Boolean = true,
    hideSystemBars: Boolean = true,
    fitsSystemWindows: Boolean = true
) {
    rootView.viewTreeObserver?.addOnWindowFocusChangeListener { hasFocus ->
        if (hasFocus) {
            WindowCompat.setDecorFitsSystemWindows(window, fitsSystemWindows)
            WindowInsetsControllerCompat(window, rootView).apply {
                if (hideSystemBars) {
                    if (fullScreen) {
                        hide(
                            WindowInsetsCompat.Type.statusBars() or
                                    WindowInsetsCompat.Type.navigationBars()
                        )
                    } else {
                        show(
                            WindowInsetsCompat.Type.statusBars() or
                                    WindowInsetsCompat.Type.navigationBars()
                        )
                    }
                    systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        }
    }
}