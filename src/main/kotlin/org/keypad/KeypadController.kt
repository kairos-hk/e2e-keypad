package org.keypad

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/key_create")
class KeypadController(private val keypadService: KeypadService) {

    @GetMapping
    fun createKeypad(): String {
        return keypadService.generateShuffledKeypad()
    }
}
