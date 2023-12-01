package com.example.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private  var adapter: diaryAdapter? = null
    var sql = SqlHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //instaciamos la clase pasando el contexto de esta otra clase



        iniRecycleView()
        getAll()
        binding.btnSave.setOnClickListener{

            if (binding.inptDate.text.isBlank() && binding.inptName.text.isBlank() && binding.inptPlace.text.isBlank() && binding.inptTime.text.isBlank()){
                Toast.makeText(this, "Favor de llenar todos los campos!!!", Toast.LENGTH_LONG).show()
            }else{
                //Se llena los datos para solo ser insertados a la bd
                val fildiry = DataDiary(nameEvent = binding.inptName.toString(), namePlace = binding.inptPlace.toString(), timeEvent = binding.inptTime.toString(), dateEvent = binding.inptDate.toString())
                val status = sql.addInformation(fildiry)
                clearFields()
                Toast.makeText(this,"codigo arrojado por la insercion de datos: " + status,Toast.LENGTH_LONG  ).show()

            }

        }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.nameEvent, Toast.LENGTH_LONG).show()
        }

        binding.btnDelet.setOnClickListener{
           // sql.getInformation()

        }
    }

    private fun getAll(){
        var list = sql.getInformation()
        adapter?.addItems(list)
    }

    private fun iniRecycleView(){
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        adapter = diaryAdapter()
        binding.recycleView.adapter = adapter

    }

    fun clearFields(){
        binding.inptDate.setText("")
        binding.inptName.setText("")
        binding.inptPlace.setText("")
        binding.inptTime.setText("")

    }
}