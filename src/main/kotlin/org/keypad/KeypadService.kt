package org.keypad

import org.springframework.stereotype.Service
import java.util.Base64
import kotlin.random.Random

@Service
class KeypadService {

    private val imagePaths = listOf(
        "0.png", "1.png", "2.png", "3.png", "4.png",
        "5.png", "6.png", "7.png", "8.png", "9.png",
        "blank1.png", "blank2.png"
    )

    fun generateShuffledKeypad(): List<KeypadButton> {
        val shuffledPaths = imagePaths.shuffled(Random(System.currentTimeMillis()))
        return shuffledPaths.map { path ->
            KeypadButton(path, encodeImageToBase64(path))
        }
    }

    private fun encodeImageToBase64(imagePath: String): String {
        val resourceStream = this::class.java.classLoader.getResourceAsStream(imagePath)
            ?: throw IllegalArgumentException("Image not found: $imagePath")

        val bytes = resourceStream.readBytes()
        return Base64.getEncoder().encodeToString(bytes)
    }
}
