package com.example.testapp.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class CameraActivity : AppCompatActivity() {
    protected var tflite: Interpreter? = null
    private val tfliteModel: MappedByteBuffer? = null
    private var inputImageBuffer: TensorImage? = null
    private var imageSizeX = 0
    private var imageSizeY = 0
    private var outputProbabilityBuffer: TensorBuffer? = null
    private var probabilityProcessor: TensorProcessor? = null
    private val IMAGE_MEAN = 0.0f
    private val IMAGE_STD = 1.0f
    private val PROBABILITY_MEAN = 0.0f
    private val PROBABILITY_STD = 255.0f
    private var bitmap: Bitmap? = null
    private var labels: List<String>? = null
    private var mBarChart: HorizontalBarChart? = null
    var imageView: ImageView? = null
    var imageuri: Uri? = null
    var buclassify: Button? = null

    var prediction: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        imageView = findViewById<View>(R.id.ac_image) as ImageView
        buclassify = findViewById<View>(R.id.ac_classify) as Button
        prediction = findViewById<View>(R.id.ac_predictions) as TextView
        imageView!!.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12)
        }
        try {
            tflite = Interpreter(loadModelFile(this@CameraActivity)!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        buclassify!!.setOnClickListener {
            val imageTensorIndex = 0
            val imageShape =
                tflite!!.getInputTensor(imageTensorIndex).shape() // {1, height, width, 3}
            imageSizeY = imageShape[1]
            imageSizeX = imageShape[2]
            val imageDataType = tflite!!.getInputTensor(imageTensorIndex).dataType()
            val probabilityTensorIndex = 0
            val probabilityShape =
                tflite!!.getOutputTensor(probabilityTensorIndex).shape() // {1, NUM_CLASSES}
            val probabilityDataType = tflite!!.getOutputTensor(probabilityTensorIndex).dataType()
            inputImageBuffer = TensorImage(imageDataType)
            outputProbabilityBuffer =
                TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
            probabilityProcessor =
                TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build()
            inputImageBuffer = loadImage(bitmap)
            tflite!!.run(inputImageBuffer!!.buffer, outputProbabilityBuffer!!.buffer.rewind())
            showResult()
        }
    }


    private fun loadImage(bitmap: Bitmap?): TensorImage {
        // Loads bitmap into a TensorImage.
        inputImageBuffer!!.load(bitmap)

        // Creates processor for the TensorImage.
        val cropSize = Math.min(bitmap!!.width, bitmap.height)
        // TODO(b/143564309): Fuse ops inside ImageProcessor.
        val imageProcessor = ImageProcessor.Builder().add(ResizeWithCropOrPadOp(cropSize, cropSize))
            .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(getPreprocessNormalizeOp()).build()
        return imageProcessor.process(inputImageBuffer)
    }

    @Throws(IOException::class)
    private fun loadModelFile(activity: Activity): MappedByteBuffer? {
        val fileDescriptor = activity.assets.openFd("set1_model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startoffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength)
    }

    private fun getPreprocessNormalizeOp(): TensorOperator? {
        return NormalizeOp(IMAGE_MEAN, IMAGE_STD)
    }

    private fun getPostprocessNormalizeOp(): TensorOperator? {
        return NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD)
    }

    fun barchart(
        barChart: BarChart?, arrayList: ArrayList<BarEntry>?, xAxisValues: ArrayList<String>?
    ) {
        barChart!!.setDrawBarShadow(false)
        barChart.setFitBars(true)
        barChart.setDrawValueAboveBar(true)
        barChart.setMaxVisibleValueCount(25)
        barChart.setPinchZoom(true)
        barChart.setDrawGridBackground(true)
        val barDataSet = BarDataSet(arrayList, "Class")
        barDataSet.setColors(
            *intArrayOf(
                Color.parseColor("#03A9F4"),
                Color.parseColor("#FF9800"),
                Color.parseColor("#76FF03"),
                Color.parseColor("#E91E63"),
                Color.parseColor("#2962FF")
            )
        )
        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        val barData = BarData(barDataSet)
        barData.barWidth = 0.9f
        barData.setValueTextSize(0f)
        barChart.setBackgroundColor(Color.TRANSPARENT) //set whatever color you prefer
        barChart.setDrawGridBackground(false)
        barChart.animateY(2000)

        //Legend l = barChart.getLegend(); // Customize the ledgends
        //l.setTextSize(10f);
        //l.setFormSize(10f);
//To set components of x axis
        val xAxis = barChart.xAxis
        xAxis.textSize = 13f
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        xAxis.setDrawGridLines(false)
        barChart.data = barData
    }


    /*private fun showresult() {
        try {
            labels = FileUtil.loadLabels(this@ClassifyYogaSet1Activity, "set1_labels.txt")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        val labeledProbability = TensorLabel(
            labels!!, probabilityProcessor!!.process(outputProbabilityBuffer)
        ).mapWithFloatValue
        val maxValueInMap = Collections.max(labeledProbability.values)

        for ((key, value) in labeledProbability) {
            val label = labeledProbability.keys.toTypedArray()
            val label_probability = labeledProbability.values.toTypedArray()
            mBarChart = findViewById<HorizontalBarChart>(R.id.chart)
            mBarChart.getXAxis().setDrawGridLines(false)
            mBarChart.getAxisLeft().setDrawGridLines(false)
            val barEntries = ArrayList<BarEntry>()
            for (i in label_probability.indices) {
                barEntries.add(BarEntry(i.toFloat(), label_probability[i] * 100))
            }
            val xAxisName = ArrayList<String>()
            Collections.addAll(xAxisName, *label)
            barchart(
                mBarChart, barEntries, xAxisName
            )
            prediction!!.text = "Yoga Set 1 Pose Prediction:"
        }
    }*/

    private fun showResult() {
        try {
            labels = FileUtil.loadLabels(this@CameraActivity, "set1_labels.txt")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val labeledProbability = TensorLabel(
            labels!!,
            probabilityProcessor!!.process(outputProbabilityBuffer)
        ).mapWithFloatValue
        val maxValueInMap = Collections.max(labeledProbability.values)

        for ((entryKey, entryValue) in labeledProbability) {
            val label = labeledProbability.keys.toTypedArray()
            val labelProbability = labeledProbability.values.toTypedArray()

            mBarChart = findViewById(R.id.ac_chart)
            mBarChart?.xAxis?.setDrawGridLines(false)
            mBarChart?.axisLeft?.setDrawGridLines(false)

            val barEntries = ArrayList<BarEntry>()
            for (i in labelProbability.indices) {
                barEntries.add(BarEntry(i.toFloat(), labelProbability[i] * 100))
            }

            val xAxisName = ArrayList<String>()
            xAxisName.addAll(label)
            barchart(mBarChart, barEntries, xAxisName)
            prediction?.text = "Yoga Set 1 Pose Prediction:"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            imageuri = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageuri)
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}