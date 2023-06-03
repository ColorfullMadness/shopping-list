package com.example.zad2_shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.zad2_shopping.data.Lists
import com.example.zad2_shopping.databinding.FragmentItemListBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.InputStream
import com.google.gson.Gson as Gson
import android.util.JsonReader;
import com.example.zad2_shopping.data.ShoppingList
import com.example.zad2_shopping.data.ShoppingListItem
import com.example.zad2_shopping.data.Units
import java.io.InputStreamReader

/**
 * A fragment representing a list of Items.
 */
class ListsFragment : Fragment(), ListsListener, DeleteListDialogFragment.OnDeleteDialogInteractionListener {

    private lateinit var binding: FragmentItemListBinding

    override fun onStart() {
        super.onStart()


       // val inputStream: InputStream = File(context?.getExternalFilesDir(null),"output.txt").inputStream()
        //val inputString = inputStream.bufferedReader().use { it.readText() }

//        var temp:MutableList<HashMap<String, MutableList<ShoppingListItem>>> = ArrayList();
//        val jsonReader: JsonReader = JsonReader(InputStreamReader(inputStream));
//
//        try{
//            readShoppingListsArray(jsonReader)
//        } finally {
//            jsonReader.close()
//        }
    }

    private fun readShoppingListsArray(jsonReader: JsonReader):MutableList<HashMap<String,MutableList<ShoppingListItem>>> {
        jsonReader.beginArray()
        var array: MutableList<HashMap<String,MutableList<ShoppingListItem>>> = arrayListOf();
        var map: HashMap<String,MutableList<ShoppingListItem>> = hashMapOf();
        while (jsonReader.hasNext()){
            var array: MutableList<ShoppingListItem> = arrayListOf();
            var name: String = "";
            jsonReader.beginObject()
            name = jsonReader.nextName()
            println(name)
            array = readShoppingListArray(jsonReader)
            jsonReader.endObject()
            map.put(name,array);
        }
        array.add(map);
        jsonReader.endArray()
        return array;
    }

    private fun readShoppingListArray(jsonReader: JsonReader):MutableList<ShoppingListItem> {
        jsonReader.beginArray()
        var shoppingList: MutableList<ShoppingListItem> = mutableListOf();
        while (jsonReader.hasNext()){

            shoppingList.add(readShoppingListItem(jsonReader))
        }
        jsonReader.endArray()
        return shoppingList;
    }

    private fun readShoppingListItem(jsonReader: JsonReader):ShoppingListItem {
        var shoppingListItem: ShoppingListItem = ShoppingListItem();
        jsonReader.beginObject()
        while (jsonReader.hasNext()){
            var name = jsonReader.nextName()
            //println(name)
            when(name){
                "amount" -> shoppingListItem.amount = jsonReader.nextInt()
                "checked" -> shoppingListItem.checked = jsonReader.nextBoolean()
                "id" -> shoppingListItem.id = jsonReader.nextString()
                "name" -> shoppingListItem.name = jsonReader.nextString()
                "units" -> shoppingListItem.units = Units.valueOf(jsonReader.nextString())
            }
        }
        jsonReader.endObject()
        println(shoppingListItem)
        return shoppingListItem;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemListBinding.inflate(inflater,container,false)

        //tu mozemy dac odpowiedni adapter w zaleznosci od tego co chcemy pokazac
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyListRecyclerViewAdapter(Lists.ITEMS,this@ListsFragment)
        }

        binding.addButton.setOnClickListener(){addButtonClick()}
        return binding.root
    }

    private fun addButtonClick() {
        val action = ListsFragmentDirections.actionListFragmentToAddListFragment(false,0)
        findNavController().navigate(action)
    }

    private fun showDeleteDialog(position: Int) {
        val deleteDialog = DeleteListDialogFragment.newInstance(position,this)
        deleteDialog.show(requireActivity().supportFragmentManager,"DeleteDialog")
    }

    override fun onStop() {
        super.onStop()


//        println("DESTRUCTION")
//        val gson = Gson();
//        val fromObj = gson.toJson(Lists.ITEMS);
//        val file = File(context?.getExternalFilesDir(null),"output.txt");
//        file.writeText(fromObj)

//        val inputStream: InputStream = File(context?.getExternalFilesDir(null),"output.txt").inputStream()
//        //val inputString = inputStream.bufferedReader().use { it.readText() }
//
//        var temp:MutableList<HashMap<String, MutableList<ShoppingListItem>>> = ArrayList();
//        val jsonReader: JsonReader = JsonReader(InputStreamReader(inputStream));
//
//        try{
//            readShoppingListsArray(jsonReader)
//        } finally {
//            jsonReader.close()
//        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
    }

    override fun onItemClick(position: Int) {
//        val actionListsFragmentToDisplayListsFragment =
//            ListsFragmentDirections.actionListsFragmentToShoppingListFragment(Lists.ITEMS.get(position))

        val action = ListsFragmentDirections.actionListsFragmentToShoppingListFragment(position)

        findNavController().navigate(action);
    }

    override fun onLongItemClick(position: Int) {
        val deleteDialog = DeleteListDialogFragment.newInstance(position,this)
        deleteDialog.show(requireActivity().supportFragmentManager,"DeleteDialog")
    }

    override fun onButtonClick(position: Int) {
        val action = ListsFragmentDirections.actionListFragmentToAddListFragment(true, position)
        findNavController().navigate(action)
    }

    override fun onDialogPositiveClick(pos: Int?) {
        Lists.ITEMS.removeAt(pos!!)
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