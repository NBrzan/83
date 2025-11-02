package com.group83.a83.controller

import com.group83.a83.model.ABCDModel

class ABCDController(val question: ABCDModel) {
    var selectedIndex: Int? = null
        private set
    var confirmed: Boolean = false
        private set

    fun select(index: Int) {
        if (!confirmed) {
            selectedIndex = index
        }
    }

    fun confirm() {
        if (selectedIndex != null) {
            confirmed = true
        }
    }

    fun reset() {
        selectedIndex = null
        confirmed = false
    }
}

