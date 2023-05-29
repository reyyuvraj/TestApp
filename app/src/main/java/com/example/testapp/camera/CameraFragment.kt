package com.example.testapp.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.testapp.BuildConfig
import com.example.testapp.R
import com.example.testapp.ml.LiteModelMovenetSingleposeThunderTfliteFloat164
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var textureView: TextureView
    private lateinit var cameraManager: CameraManager
    private lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread
    private lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var model: LiteModelMovenetSingleposeThunderTfliteFloat164
    private lateinit var imageProcessor: ImageProcessor
    private val requestCameraPermission = 101
    private val paint = Paint()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setUpView()

        view.findViewById<Button>(R.id.fc_btn)?.apply {
            setOnClickListener {
                val packageName = "com.example.yoga_tracker"
                val className = "com.example.yoga_tracker.ClassifyYogaSet1Activity"

                val intent = Intent()
                intent.component = ComponentName(packageName, className)

                // val intent = Intent(requireContext(), CameraActivity::class.java)
                startActivity(intent)
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Handle the case when the target application is not found
                    Toast.makeText(
                        requireContext(), "Target application not found", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        textureView = view.findViewById(R.id.fc_texture_view)
        cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handlerThread = HandlerThread("vThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        imageView = view.findViewById(R.id.fc_image_view)
        model = LiteModelMovenetSingleposeThunderTfliteFloat164.newInstance(requireContext())
        imageProcessor =
            ImageProcessor.Builder().add(ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR)).build()
        paint.color = Color.RED


        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                if (getPermissions()) openCamera()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {

            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                Log.d("cameraFrag", "$p0")
                bitmap = textureView.bitmap!!

                var tensorImage = TensorImage(DataType.UINT8)
                tensorImage.load(bitmap)
                tensorImage = imageProcessor.process(tensorImage)

                /*// Creates inputs for reference.
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.UINT8)
                inputFeature0.loadBuffer(tensorImage.buffer)

                // Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray*/

                // Creates inputs for reference.
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.UINT8)
                inputFeature0.loadBuffer(tensorImage.buffer)

                // Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                val mutable = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                val canvas = Canvas(mutable)
                val h = bitmap.height
                val w = bitmap.width
                var x = 0

                while (x <= 49) {
                    if (outputFeature0[x + 2] > 0.45) {
                        canvas.drawCircle(
                            outputFeature0[x + 1] * w, outputFeature0[x] * h, 10f, paint
                        )
                    }
                    x += 3
                }
                imageView.setImageBitmap(mutable)
            }
        }
    }

    private fun setUpView() {
        view?.findViewById<TextView>(R.id.log_tv)?.apply {
            visibility = View.VISIBLE
        }

        // Print a debug log
        Timber.d("Hello, world!")

        // Print an error log with a throwable
        val exception = RuntimeException("Something went wrong!")
        Timber.e(exception)

        // for logs
        try {
            val process = Runtime.getRuntime().exec("logcat -d cameraFrag")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.inputStream)
            )
            val log = StringBuilder()
            var line: String? = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                log.append(line)
            }
            val tv = view?.findViewById<TextView>(R.id.fc_log_tv)
            tv?.text = log.toString()
            /*val logTV = findViewById<TextView>(R.id.fc_log_tv)
            logTV.text = log.toString()*/
        } catch (e: IOException) {
            // not handled yet
        }

        /*if (BuildConfig.DEBUG) {
            Timber.plant(TextViewTree(view?.findViewById<TextView>(R.id.fc_log_tv)!!))
        }*/
    }

    private fun yogaModel() {

    }

    override fun onDestroy() {
        super.onDestroy()
        // Releases model resources if no longer used.
        model.close()
    }

    private fun getPermissions(): Boolean {
        return if (checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) != PermissionChecker.PERMISSION_GRANTED
        ) {
            //shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
            requestCameraPermission()
            false
        } else true
    }

    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Camera Permission")
                .setMessage("This app needs camera permission to take pictures.")
                .setPositiveButton("OK") { _, _ ->
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA), requestCameraPermission
                    )
                }.setNegativeButton("Cancel", null).create().show()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA), requestCameraPermission
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        cameraManager.openCamera(
            cameraManager.cameraIdList[0], object : CameraDevice.StateCallback() {
                override fun onOpened(p0: CameraDevice) {
                    val captureRequest = p0.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                    val surface = Surface(textureView.surfaceTexture)
                    captureRequest.addTarget(surface)
                    p0.createCaptureSession(
                        listOf(surface), object : CameraCaptureSession.StateCallback() {
                            override fun onConfigured(p0: CameraCaptureSession) {
                                p0.setRepeatingRequest(captureRequest.build(), null, null)
                            }

                            override fun onConfigureFailed(p0: CameraCaptureSession) {

                            }

                        }, handler
                    )
                }

                override fun onDisconnected(p0: CameraDevice) {

                }

                override fun onError(p0: CameraDevice, p1: Int) {

                }
            }, handler
        )
    }

    fun clearLog() {
        try {
            val process = ProcessBuilder().command("logcat", "-d").redirectErrorStream(true).start()
        } catch (e: IOException) {
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PermissionChecker.PERMISSION_GRANTED) getPermissions()
    }
}