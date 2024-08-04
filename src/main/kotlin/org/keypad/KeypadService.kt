package org.keypad

import org.springframework.stereotype.Service
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.Base64
import javax.imageio.ImageIO
import kotlin.random.Random

@Service
class KeypadService {

    private val imagePaths = listOf(
        "0.png", "1.png", "2.png", "3.png", "4.png",
        "5.png", "6.png", "7.png", "8.png", "9.png",
        "blank1.png", "blank2.png"
    )

    fun generateShuffledKeypad(): String {
        val shuffledPaths = imagePaths.shuffled(Random(System.currentTimeMillis()))
        val combinedImage = combineImages(shuffledPaths)
        return encodeImageToBase64(combinedImage)
    }

    private fun combineImages(imagePaths: List<String>): BufferedImage {

        val firstImage = loadImage(imagePaths[0])
        val width = firstImage.width
        val height = firstImage.height

        val combinedImage = BufferedImage(width * 4, height * 3, BufferedImage.TYPE_INT_ARGB)
        val g: Graphics2D = combinedImage.createGraphics()

        for (i in imagePaths.indices) {
            val img = loadImage(imagePaths[i])
            val x = (i % 4) * width
            val y = (i / 4) * height
            g.drawImage(img, x, y, null)
        }

        g.dispose()
        return combinedImage
    }

    private fun loadImage(imagePath: String): BufferedImage {
        val resourceStream = this::class.java.classLoader.getResourceAsStream(imagePath)
            ?: throw IllegalArgumentException("Image not found: $imagePath")
        return ImageIO.read(resourceStream)
    }

    private fun encodeImageToBase64(image: BufferedImage): String {
        val byteAO = ByteArrayOutputStream()
        ImageIO.write(image, "png", byteAO)
        val bytes = byteAO.toByteArray()
        return Base64.getEncoder().encodeToString(bytes)
    }
}
