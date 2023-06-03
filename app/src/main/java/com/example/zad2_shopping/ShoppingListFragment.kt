package com.example.zad2_shopping

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zad2_shopping.data.Lists
import com.example.zad2_shopping.data.ShoppingListItem
import com.example.zad2_shopping.databinding.FragmentItemList2Binding
import com.google.android.material.snackbar.Snackbar


/**
 * A fragment representing a list of Items.
 */
class ShoppingListFragment : Fragment(),ListsListener,DeleteListDialogFragment.OnDeleteDialogInteractionListener {

    private lateinit var binding: FragmentItemList2Binding
    val args: ShoppingListFragmentArgs by navArgs()
    private lateinit var values: List<ShoppingListItem>;
    private lateinit var key: String;
    private var position: Int = 0;

    var state: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemList2Binding.inflate(inflater,container,false)

        position = args.position

        val map = Lists.ITEMS.get(position)
        key = map.keys.first()
        values = map.getValue(key)

        with(binding.list){
            layoutManager = LinearLayoutManager(context)
            adapter = MyShoppingListRecyclerViewAdapter(values,this@ShoppingListFragment)
        }

        binding.listTitle.text = key;

        binding.addButton.setOnClickListener(){

            val action = ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingListItemFragment(position, 0, false)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun showDeleteDialog(position: Int) {
        val deleteDialog = DeleteListDialogFragment.newInstance(position,this)
        deleteDialog.show(requireActivity().supportFragmentManager,"DeleteDialog")
    }


    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
        binding.list.layoutManager?.onRestoreInstanceState(state)
    }

    override fun onItemClick(position: Int) {
        val action = ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingListItemFragment(this.position,position,true)
        findNavController().navigate(action)
    }


    override fun onLongItemClick(position: Int) {
        val deleteDialog = DeleteListDialogFragment.newInstance(position,this)
        deleteDialog.show(requireActivity().supportFragmentManager,"DeleteDialog")
    }

    override fun onButtonClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDialogPositiveClick(pos: Int?) {
        Lists.ITEMS[position].values.first().removeAt(pos!!);
        Snackbar.make(requireView(), "Task Deleted", Snackbar.LENGTH_LONG).show()
        notifyDataSetChanged()
    }

    private fun notifyDataSetChanged() {
        val rvAdapter = binding.list.adapter
        rvAdapter?.notifyDataSetChanged()
    }

    override fun onDialogNegativeClick(pos: Int?) {
        Snackbar.make(requireView(),"Delete conncelled", Snackbar.LENGTH_LONG)
            .setAction("Redo",View.OnClickListener { showDeleteDialog(pos!!) })
            .show()
    }
}