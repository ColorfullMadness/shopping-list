package com.example.zad2_shopping

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

import com.example.zad2_shopping.data.ShoppingListItem
import com.example.zad2_shopping.data.Units
import com.example.zad2_shopping.databinding.FragmentItem2Binding

/**
 * [RecyclerView.Adapter] that can display a [ShoppingListItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyShoppingListRecyclerViewAdapter(
    private val values: List<ShoppingListItem>,
    private val eventListener: ListsListener
) : RecyclerView.Adapter<MyShoppingListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItem2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        var name = item.name
        if(item.units == Units.Units){
            name += " " + item.amount
        } else {
            name += " " + item.amount + item.units.toString();
        }

        holder.nameView.text = name
        if (item.checked) {
            holder.nameView.setTextColor(Color.GRAY);
        }
        holder.checked.isChecked = item.checked
        holder.checked.setOnClickListener(){
            values[position].checked = !values[position].checked;
            if(values[position].checked){
                holder.nameView.setTextColor(Color.GRAY)
            } else {
                holder.nameView.setTextColor(Color.WHITE)
            }
        }

        holder.itemContainer.setOnLongClickListener(){
            eventListener.onLongItemClick(position)
            return@setOnLongClickListener true
        }
        holder.itemContainer.setOnClickListener(){
            eventListener.onItemClick(position)
            return@setOnClickListener Unit
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        values
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItem2Binding) : RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.nameView
        val checked: CheckBox = binding.checked
        val itemContainer: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + nameView.text
        }
    }

}