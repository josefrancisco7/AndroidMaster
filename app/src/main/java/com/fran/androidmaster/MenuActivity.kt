package com.fran.androidmaster

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fran.androidmaster.firtsapp.FiirtsAppActivity
import com.fran.androidmaster.imccalculator.ImcCalculatorActivity
import com.fran.androidmaster.settings.SettingsActivity
import com.fran.androidmaster.superheroapp.SuperHeroListActivity
import com.fran.androidmaster.todoapp.TodoActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        val btnSaludApp= findViewById<Button>(R.id.btnSaludApp)
        val btnImcApp= findViewById<Button>(R.id.btnImcApp)
        val btnTODO = findViewById<Button>(R.id.btnTODO)
        val btnSuperhero = findViewById<Button>(R.id.btnSuperhero)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        btnSaludApp.setOnClickListener{ navigateToSaludApp()}
        btnImcApp.setOnClickListener{ navigateToImcApp()}
        btnTODO.setOnClickListener{navigateTodoApp()}
        btnSuperhero.setOnClickListener{navigateToSuperheroApp()}
        btnSettings.setOnClickListener{navigateToSettings()}



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigateToSettings() {
        val intent = Intent(this,SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSuperheroApp() {
        val intent = Intent(this,SuperHeroListActivity::class.java)
        startActivity(intent)
    }

    private fun navigateTodoApp() {
        val intent = Intent(this,TodoActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToImcApp(){
        val intent= Intent(this, ImcCalculatorActivity::class.java)
        startActivity(intent)
    }

   private  fun navigateToSaludApp(){
        val intent= Intent(this, FiirtsAppActivity::class.java)
        startActivity(intent)

    }
}