package com.xheghun.vidit


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.TextureView
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xheghun.vidit.adapter.ImageFrameAdapter
import com.xheghun.vidit.classes.Animate
import kotlinx.android.synthetic.main.fragment_four_snaps.*
import java.io.File
import java.io.FileInputStream


/**
 * A simple [Fragment] subclass.
 */
val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

class FourSnapActivity : AppCompatActivity() {

    private var lensFacing = CameraX.LensFacing.BACK
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_four_snaps)

        val snapButton: FloatingActionButton = findViewById(R.id.snap_btn)
        val flashToggle: ImageButton = findViewById(R.id.flash_toggle_btn)
        val switchCamButton: ImageButton = findViewById(R.id.switch_cam_btn)
        bindCamera()

        val images = ArrayList<String>()


        snapButton.setOnClickListener{
            val filename =  "${System.currentTimeMillis()}.png"
            val dest = File(filesDir, filename)
            imageCapture?.takePicture(dest,
                    object : ImageCapture.OnImageSavedListener {
                        override fun onError(error: ImageCapture.UseCaseError,
                                             message: String, exc: Throwable?) {
                            Log.e("Image", error.toString())
                        }

                        override fun onImageSaved(file: File) {
                            Log.v("Image", "Successfully saved image")
                            val image = file.absolutePath
                            //Toast.makeText(this@FourSnapActivity,file.absolutePath,Toast.LENGTH_LONG).show()
                            if(images.size > 15) {
                             Toast.makeText(this@FourSnapActivity,"Maximum Of 16 Photos Allowed",Toast.LENGTH_SHORT).show()
                            } else {
                                images.add(image)
                            }


                            //Toast.makeText(this@FourSnapActivity,"photo path: ${file.path},\n current image: $image",Toast.LENGTH_LONG).show()

                            val layoutManager = LinearLayoutManager(this@FourSnapActivity)
                            layoutManager.orientation = RecyclerView.HORIZONTAL
                            Animate.fadeInAnimation(image_frame_recycler_view,this@FourSnapActivity)
                            image_frame_recycler_view.layoutManager = layoutManager
                            image_frame_recycler_view.adapter = ImageFrameAdapter(images,this@FourSnapActivity)

                            if (images.size in 4..16) {
                                Animate.fadeInAnimation(next_btn,this@FourSnapActivity)
                                next_btn.setOnClickListener {

                                    val array = arrayOfNulls<String>(images.size)

                                    val intent = Intent(this@FourSnapActivity,PhotoEditActivity::class.java)
                                    intent.putExtra("images",images.toArray(array))
                                    startActivity(intent)
                                }
                            }else {
                                Animate.fadeOutAnimation(next_btn,this@FourSnapActivity)
                            }

                            if(images.size > 0) {
                                clear_btn.visibility = View.VISIBLE
                                clear_btn.setOnClickListener {
                                    images.clear()
                                    Animate.fadeOutAnimation( image_frame_recycler_view,this@FourSnapActivity)
                                    Animate.fadeOutAnimation(clear_btn,this@FourSnapActivity)
                                    Animate.fadeOutAnimation(next_btn,this@FourSnapActivity)
                                }
                            }
                           // Toast.makeText(this@FourSnapActivity,"Photo Taken",Toast.LENGTH_SHORT).show()

                        }
                    }
            )
        }

        flashToggle.setOnClickListener {
            val flashMode = imageCapture?.flashMode
            if(flashMode == FlashMode.ON) {
                imageCapture?.flashMode = FlashMode.OFF
                Toast.makeText(this,"Flash Off",Toast.LENGTH_SHORT).show()
            }
            else{
                imageCapture?.flashMode = FlashMode.ON
                Toast.makeText(this,"Flash On",Toast.LENGTH_SHORT).show()
            }
        }

        switchCamButton.setOnClickListener {
            lensFacing = if (CameraX.LensFacing.FRONT == lensFacing) {
                CameraX.LensFacing.BACK
            } else {
                CameraX.LensFacing.FRONT
            }
            bindCamera()
            Toast.makeText(this,"Switching Cam",Toast.LENGTH_SHORT).show()

        }

        gallery_btn.setOnClickListener { startActivity(Intent(this,GalleryActivity::class.java)) }
    }

    private fun hasNoPermission() :Boolean {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // !! means cannot be null
        ActivityCompat.requestPermissions(this, permissions,0)
    }

    private fun bindCamera(){
        CameraX.unbindAll()

        // The view that displays the preview
        val textureView: TextureView = findViewById(R.id.texture_view)

        val aspectRatio = Rational(textureView.width,textureView.height)
        val screen = Size(textureView.width,textureView.height)

        // Preview config for the camera
        val previewConfig = PreviewConfig.Builder()
                .setLensFacing(lensFacing)
                .setTargetAspectRatio(aspectRatio)
                .setTargetResolution(screen)
                .build()

        val preview = Preview(previewConfig)



        // Handles the output data of the camera
        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            // Displays the camera image in our preview view
            textureView.surfaceTexture = previewOutput.surfaceTexture
        }



        // Image capture config which controls the Flash and Lens
        val imageCaptureConfig = ImageCaptureConfig.Builder()
                .setTargetRotation(this.windowManager.defaultDisplay.rotation)
                .setLensFacing(lensFacing)
                .setFlashMode(FlashMode.ON)
                .build()

        imageCapture = ImageCapture(imageCaptureConfig)

        // Bind the camera to the lifecycle
        CameraX.bindToLifecycle(this as LifecycleOwner, imageCapture, preview)
    }

    override fun onStart() {
        super.onStart()
        //Check if request permissions
        if (hasNoPermission()) {
            requestPermission()
        }
    }

    private fun getBitmap(path: String): Bitmap? {
        try {
            var bitmap: Bitmap? = null
            val file = File(path)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            bitmap = BitmapFactory.decodeStream(FileInputStream(file), null, options)


            return bitmap!!
        } catch (e: Exception) {

            return null
        }
    }
}
