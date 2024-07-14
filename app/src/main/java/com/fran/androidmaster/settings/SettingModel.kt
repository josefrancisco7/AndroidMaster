package com.fran.androidmaster.settings

import android.health.connect.datatypes.units.Volume
import android.os.Vibrator

data class SettingModel(var volume: Int,
                        var bluetooth:Boolean,
                        var darkMode:Boolean,
                        var vibration:Boolean)

