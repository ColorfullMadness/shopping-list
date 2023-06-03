package com.example.zad2_shopping

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.JsonReader
import androidx.appcompat.app.AppCompatActivity
import com.example.zad2_shopping.data.Lists
import com.example.zad2_shopping.data.ShoppingListItem
import com.example.zad2_shopping.data.Units
import com.example.zad2_shopping.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        askPermissions()

        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()
        println("DESTRUCTION")
        val gson = Gson();
        val fromObj = gson.toJson(Lists.ITEMS);
        val file = File("storage/3820-F411/Android/data/com.example.zad2_shopping/files/output.txt");
        file.writeText(fromObj)
//        println("CREATION")
//
//        val inputStream: InputStream = File(getExternalFilesDir(null),"output.txt").inputStream()
//
//        var temp:MutableList<HashMap<String, MutableList<ShoppingListItem>>> = ArrayList();
//        val jsonReader: JsonReader = JsonReader(InputStreamReader(inputStream));
//
//        try{
//            Lists.ITEMS = readShoppingListsArray(jsonReader)
//        } finally {
//            jsonReader.close()
//        }
    }

    override fun onStart() {
        super.onStart()
//        println("CREATION")
//
//        val inputStream: InputStream = File(getExternalFilesDir(null),"output.txt").inputStream()
//
//        var temp:MutableList<HashMap<String, MutableList<ShoppingListItem>>> = ArrayList();
//        val jsonReader: JsonReader = JsonReader(InputStreamReader(inputStream));
//
//        try{
//            Lists.ITEMS = readShoppingListsArray(jsonReader)
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

    protected fun askPermissions() {
        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.MANAGE_EXTERNAL_STORAGE"
        )
        val requestCode = 200
        requestPermissions(permissions, requestCode)
    }
}