package com.fran.androidmaster.imccalculator

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fran.androidmaster.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class ImcCalculatorActivity : AppCompatActivity() {

    private var isMaleSelected:Boolean=true
    private var isFemaleSelected:Boolean=false
    private var currentWeight:Int=70
    private var currentAge:Int=30
    private var currentHeight:Int=120

    private lateinit var viewMale:CardView
    private lateinit var viewFemale:CardView
    private lateinit var tvHeight:TextView
    private lateinit var rsHeight:RangeSlider
    private lateinit var btnSubstractWeight:FloatingActionButton
    private lateinit var btnPlusWeight:FloatingActionButton
    private lateinit var tvWeight:TextView
    private lateinit var btnSubstractAge:FloatingActionButton
    private lateinit var btnPlusAge:FloatingActionButton
    private lateinit var tvAge:TextView
    private lateinit var btnCalculate:Button


    companion object{
        const val IMC_KEY= "IMC_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_calculator)
        initComponent()
        initListener()
        initUI()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun initComponent(){
        viewFemale= findViewById(R.id.viewFemale)
        viewMale= findViewById(R.id.viewMale)
        tvHeight= findViewById(R.id.tvHeight)
        rsHeight=findViewById(R.id.rsHeight)
        btnSubstractWeight = findViewById(R.id.btnSubtractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubstractAge = findViewById(R.id.btnSubtractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)

    }
    private fun initListener(){
        viewFemale.setOnClickListener{
            setGenderColor()
            changeGender()
        }
        viewMale.setOnClickListener{
            setGenderColor()
            changeGender()
        }
        rsHeight.addOnChangeListener { _, value, _ ->

            val df= DecimalFormat("#.##")
            currentHeight= df.format(value).toInt()
            tvHeight.text= "$currentHeight cm"
        }
        btnPlusWeight.setOnClickListener{
            currentWeight += 1
            setWeight()
        }
        btnSubstractWeight.setOnClickListener{
            currentWeight-= 1
            setWeight()
        }
        btnPlusAge.setOnClickListener{
            currentAge += 1
            setAge()
        }
        btnSubstractAge.setOnClickListener{
            currentAge -= 1
            setAge()
        }
        btnCalculate.setOnClickListener{
         val result = calculateIMC()
            navigateToResult(result)
        }
    }

    private fun navigateToResult(result: Double) {
        val intent = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY,result)
        startActivity(intent)

    }

    private fun calculateIMC():Double {
        val df= DecimalFormat("#.##")
       val imc = currentWeight / (currentHeight.toDouble()/100 * currentHeight.toDouble()/100)
       return df.format(imc).toDouble()
    }

    private fun setWeight(){
        tvWeight.text=currentWeight.toString()
    }
    private fun setAge(){
        tvAge.text=currentAge.toString()
    }

    private fun changeGender(){
        isMaleSelected= !isMaleSelected
        isFemaleSelected=!isFemaleSelected
    }
    private fun setGenderColor(){

        var maleColor= if(isMaleSelected){
            R.color.background_component_selected
        }else{
            R.color.background_component
        }
        
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent:Boolean):Int{

        val colorReference=if(isSelectedComponent){
            R.color.background_component_selected
        }else{
            R.color.background_component
        }
       return ContextCompat.getColor(this,colorReference)

    }
    private fun initUI(){
        setGenderColor()
        setWeight()
        setAge()
    }

}