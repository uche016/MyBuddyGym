package com.example.mybuddygym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybuddygym.R
import com.example.mybuddygym.database.model.workout_type.WorkOutType
import kotlinx.android.synthetic.main.card_view_work_out_type_item.view.*

class DoWorkoutAdapter() :
    RecyclerView.Adapter<DoWorkoutAdapter.WorkOutViewHolder>() {
    var listener:OnItemClickListener? = null
    var arrList = ArrayList<WorkOutType>()
    //linking the code to the design line 15-19
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkOutViewHolder {
        return WorkOutViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_work_out_type_item,parent,false)
        )
    }
// this sets number of items in the table line 21-23
    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrWorkOutList: List<WorkOutType>){
        arrList = arrWorkOutList as ArrayList<WorkOutType>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: WorkOutViewHolder, position: Int) {

        holder.itemView.tv_title.text = arrList[position].workoutName


    }

    class WorkOutViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

//calls the fragment
    interface OnItemClickListener{

    }

}