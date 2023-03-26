package com.example.testapp.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import android.view.View
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.testapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val requestCameraPermission = 101
    private lateinit var textureView: TextureView
    private lateinit var cameraManager: CameraManager
    private lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textureView = view.findViewById(R.id.fc_texture_view)
        cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handlerThread = HandlerThread("vThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

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

            }

        }
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

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PermissionChecker.PERMISSION_GRANTED) getPermissions()
    }
}