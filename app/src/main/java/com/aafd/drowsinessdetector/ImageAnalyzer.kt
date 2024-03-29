package com.aafd.drowsinessdetector

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

class ImageAnalyzer : ImageAnalysis.Analyzer {
    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    override fun analyze(imageProxy: ImageProxy?, degrees: Int) {
        val mediaImage = imageProxy?.image
        val imageRotation = degreesToFirebaseRotation(degrees)
        if (mediaImage != null) {
            val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            val options = FirebaseVisionFaceDetectorOptions.Builder()
                .build()
            val detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options)
            detector.detectInImage(image)
                .addOnSuccessListener { faces ->
                   val fa = faces
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            // Pass image to an ML Kit Vision API
            // ...
        }
    }
}

