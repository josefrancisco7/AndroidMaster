package com.fran.androidmaster.superheroapp;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;
import com.fran.androidmaster.R


class SuperheroAdapter(
    var superheroList:List<SuperheroItemResponse> = emptyList(),
    private val onItemSelected:(String)->Unit
) :
    RecyclerView.Adapter<SuperheroViewHolder>(){

     fun updateList(superheroList: List<SuperheroItemResponse>){
         this.superheroList=superheroList
         notifyDataSetChanged()
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
       return SuperheroViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_superhero,parent,false)
       )
    }

    override fun getItemCount()= superheroList.size


    override fun onBindViewHolder(viewholder: SuperheroViewHolder, position: Int) {
        viewholder.bind(superheroList[position],onItemSelected)
    }

}


