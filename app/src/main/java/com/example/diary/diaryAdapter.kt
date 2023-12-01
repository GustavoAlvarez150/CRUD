package com.example.diary

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class diaryAdapter : RecyclerView.Adapter<diaryAdapter.diaryViewHolder>(){
    private var diarList: ArrayList<DataDiary> = ArrayList()
    private var onClick:((DataDiary) -> Unit)? = null
    private var onClickDeleteItem:((DataDiary) -> Unit)? = null

    fun addItems(items: ArrayList<DataDiary>){
        this.diarList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (DataDiary) -> Unit){
        this.onClick = callback

    }

    fun setOnclickDeleteItem(callback: (DataDiary) -> Unit){
        this.onClickDeleteItem = callback

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  diaryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items, parent, false)
    )

    override fun getItemCount(): Int =  diarList.size

    override fun onBindViewHolder(holder: diaryViewHolder, position: Int) {
        val diarry = diarList[position]
        holder.binViews(diarry)
        holder.itemView.setOnClickListener{ onClick?.invoke(diarry)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(diarry)}
    }

    class diaryViewHolder(var view: View):RecyclerView.ViewHolder(view){

        private var nameEvent = view.findViewById<TextView>(R.id.txtNameEvento)
        private var namePlace = view.findViewById<TextView>(R.id.txtNamePlace)
        private var timeEventr = view.findViewById<TextView>(R.id.txtNameEvento)
        private var dataEventr = view.findViewById<TextView>(R.id.txtDateEVent)
         var btnDelete = view.findViewById<TextView>(R.id.btnDelete)


        fun binViews(diary: DataDiary){
            nameEvent.text = diary.nameEvent
            namePlace.text = diary.namePlace
            timeEventr.text = diary.timeEvent
            dataEventr.text = diary.dateEvent

        }


    }



}