package com.example.ejerciciobotones

import java.util.*

class BroadcastObserver : Observable() {

    companion object {
        private val instance = BroadcastObserver()
        fun getInstance(): BroadcastObserver {
            return instance
        }
    }

    private fun triggerObservers(message: String) {
        setChanged()
        notifyObservers(message)
    }

    fun change(message: String) {
        triggerObservers(message)
    }
}