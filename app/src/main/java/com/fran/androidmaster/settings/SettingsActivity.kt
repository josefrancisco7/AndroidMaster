package com.fran.androidmaster.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fran.androidmaster.R
import com.fran.androidmaster.databinding.ActivitySettingsBinding
import com.fran.androidmaster.databinding.ItemTodoTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "settings")


class SettingsActivity : AppCompatActivity() {

    companion object{
        const val VOLUME_LVL="volume_lvl"
        const val KEY_BLUETOOTH="key_bluetooth"
        const val KEY_VIBRATION="key_vibration"
        const val KEY_DARK_MODE="key_dark_mode"
    }

    private lateinit var binding: ActivitySettingsBinding
    private var firstTime:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
        getSettings().filter{firstTime}.collect{settingsModel->
            if(settingsModel!=null){
                runOnUiThread{
                binding.switchVibration.isChecked=settingsModel.vibration
                binding.switchBluetooh.isChecked=settingsModel.bluetooth
                binding.switchDarkMode.isChecked=settingsModel.darkMode
                binding.rsVolumen.setValues(settingsModel.volume.toFloat())
                    firstTime= !firstTime
            }
            }

        }
        }
        initUI()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initUI() {
        binding.rsVolumen.addOnChangeListener { _, value, _ ->
            Log.i("Fran", "El valor es $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }

        }
        binding.switchBluetooh.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_BLUETOOTH, value)
            }
        }
        binding.switchVibration.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_VIBRATION, value)
            }
        }
        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->

            if(value){
                enableDarkMode()
            }else{
                disableDarkMode()
            }

            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_DARK_MODE, value)
            }
        }


    }

    private suspend fun saveVolume(value:Int){
        dataStore.edit {preferences ->
            preferences[intPreferencesKey(VOLUME_LVL)]=value
        }

    }

    private suspend fun saveOptions(key:String, value:Boolean){
        dataStore.edit{ preferences->
            preferences[booleanPreferencesKey(key)]=value
        }
    }
    private fun getSettings(): Flow<SettingModel?> {
        return dataStore.data.map{ preferences->
            SettingModel(
                volume=preferences[intPreferencesKey(VOLUME_LVL)] ?: 50,
                bluetooth= preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?:true,
                darkMode =  preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
                vibration = preferences[booleanPreferencesKey(KEY_VIBRATION)]?: true
            )


        }

    }
    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }
    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}