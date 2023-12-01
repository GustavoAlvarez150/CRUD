package com.example.diary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper(context: Context): SQLiteOpenHelper(context, "diary.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreation = "CREATE TABLE diary" +
                "(id_diary INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombreEvento TEXT, lugarEvento TEXT, horaEvento TEXT, fechaEvento TEXT)"
        db!!.execSQL(ordenCreation)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS diary"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun addInformation(data: DataDiary): Int{
        val db = this.writableDatabase
        val dataContent = ContentValues()

        dataContent.put("nombreEvento", data.nameEvent)
        dataContent.put("lugarEvento", data.namePlace)
        dataContent.put("horaEvento", data.timeEvent)
        dataContent.put("fechaEvento", data.dateEvent)

        var getSucces = db.insert("diary", null, dataContent)
        db.close()

        var res = 0
        if (getSucces > -1){
            res = getSucces.toInt()
        }else{
            res = getSucces.toInt()
        }


        return res


    }

    @SuppressLint("Range")
    fun getInformation(): ArrayList<DataDiary>{
        //Colocamos la bd para que pueda ser leida por consultas
        val db = this.readableDatabase
        val listInfo: ArrayList<DataDiary> = ArrayList()
        val queryGetAll = "SELECT * FROM diary"

        //Nos ayudara para obtener el resultado de nuestra consulta de manera secuencial
       //var cursor: Cursor?
        //Las rawQuery son consultas en crudo, que sería meramente puro sql
         var cursor = db!!.rawQuery(queryGetAll, null)
        var aux = cursor.getString(cursor.getColumnIndex("nombreEvento"))
        if (cursor.moveToFirst()){
            do {
                val diaryFill = DataDiary(namePlace = cursor.getString(2),
                    nameEvent = cursor.getString(1),
                    timeEvent = cursor.getString(3),
                    dateEvent = cursor.getString(4))

                //llenamos la lista
                listInfo.add(diaryFill)
            }while (cursor.moveToNext())

        }
        return listInfo

    }
}