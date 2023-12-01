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
    private var adapter: diaryAdapter? = null
    var sql = SqlHelper(this)
    private var diar: DataDiary? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //instaciamos la clase pasando el contexto de esta otra clase


        iniRecycleView()
        getAll()
        binding.btnSave.setOnClickListener {

            if (binding.inptDate.text.isBlank() && binding.inptName.text.isBlank() && binding.inptPlace.text.isBlank() && binding.inptTime.text.isBlank()) {
                Toast.makeText(this, "Favor de llenar todos los campos!!!", Toast.LENGTH_LONG)
                    .show()
            } else {
                //Se llena los datos para solo ser insertados a la bd
                val fildiry = DataDiary(
                    nameEvent = binding.inptName.text.toString(),
                    namePlace = binding.inptPlace.text.toString(),
                    timeEvent = binding.inptTime.text.toString(),
                    dateEvent = binding.inptDate.text.toString()
                )
                val status = sql.addInformation(fildiry)
                clearFields()
                getAll()
                Toast.makeText(
                    this,
                    "codigo arrojado por la insercion de datos: " + status,
                    Toast.LENGTH_LONG
                ).show()

            }

        }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.nameEvent, Toast.LENGTH_LONG).show()
            binding.inptDate.setText(it.dateEvent)
            binding.inptName.setText(it.nameEvent)
            binding.inptPlace.setText(it.namePlace)
            binding.inptTime.setText(it.timeEvent)

        }

        adapter?.setOnclickDeleteItem {
            delete(it.nameEvent)

        }

        binding.btnEdit.setOnClickListener {
            updateInfo()

        }


    }

    private fun updateInfo() {

        if (binding.inptName.text.toString() == diar?.nameEvent && binding.inptPlace.text.toString() == diar?.namePlace && binding.inptTime.text.toString() == diar?.timeEvent && binding.inptDate.text.toString() == diar?.dateEvent) {
            Toast.makeText(this, "No has realizado ningun cmabio", Toast.LENGTH_LONG).show()
            return
        }


        if (diar == null) return
        val diar = DataDiary(
            nameEvent = diar!!.nameEvent,
            namePlace = binding.inptPlace.text.toString(),
            timeEvent = binding.inptTime.text.toString(),
            dateEvent = binding.inptDate.text.toString()
        )
        val status = sql.updateDiary(diar)

        if (status > -1) {
            clearFields()
            getAll()
        } else {
            Toast.makeText(this, "No se ha actualizado tu informacion", Toast.LENGTH_LONG).show()
        }


    }

    fun delete(name: String){
        sql.deleteDiary(name)
        getAll()

    }

    private fun getAll() {
        var list = sql.getInformation()
        adapter?.addItems(list)
    }

    private fun iniRecycleView() {
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        adapter = diaryAdapter()
        binding.recycleView.adapter = adapter

    }

    fun clearFields() {
        binding.inptDate.setText("")
        binding.inptName.setText("")
        binding.inptPlace.setText("")
        binding.inptTime.setText("")

    }
}