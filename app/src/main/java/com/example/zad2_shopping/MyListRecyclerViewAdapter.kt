package com.example.zad2_shopping

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.example.zad2_shopping.data.ListsItem
import com.example.zad2_shopping.data.ShoppingListItem
import com.example.zad2_shopping.databinding.FragmentItemBinding
import com.example.zad2_shopping.databinding.FragmentItemList2Binding

/**
 * [RecyclerView.Adapter] that can display a [ListsItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyListRecyclerViewAdapter(
    private val values: MutableList<HashMap<String, MutableList<ShoppingListItem>>>,
    private val eventListener: ListsListener
) : RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.keys.first()
        holder.itemContainer.setOnClickListener() {
            eventListener.onItemClick(position)
        }
        holder.buttonEdit.setOnClickListener(){
            eventListener.onButtonClick(position)
        }
        holder.itemContainer.setOnLongClickListener(){
            eventListener.onLongItemClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //val idView: TextView = binding.itemNumber
        val titleView: TextView = binding.title
        val buttonEdit: Button = binding.buttonEdit
        val itemContainer: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + titleView.text
        }
    }

}