package com.example.zad2_shopping.data

import android.os.Parcel
import android.os.Parcelable
import android.util.JsonReader
import com.google.gson.Gson
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object Lists {

    /**
     * An array of sample (placeholder) items.
     */
    var ITEMS: MutableList<HashMap<String, MutableList<ShoppingListItem>>> = ArrayList()

    private val COUNT = 5

    init {
        // Add some sample items.
//        for (i in 1..COUNT) {
//            ITEMS.add(createPlaceholderItem(i))
//        }

        println(ITEMS)
//
        println("CREATION")

        val inputStream: InputStream = File("storage/3820-F411/Android/data/com.example.zad2_shopping/files/output.txt").inputStream()

        var temp:MutableList<HashMap<String, MutableList<ShoppingListItem>>> = ArrayList();
        val jsonReader: JsonReader = JsonReader(InputStreamReader(inputStream));

        try{
            ITEMS = readShoppingListsArray(jsonReader)

            println(ITEMS)
        } finally {
            jsonReader.close()
        }
    }

    private fun readShoppingListsArray(jsonReader: JsonReader):MutableList<HashMap<String,MutableList<ShoppingListItem>>> {
        var array: MutableList<HashMap<String,MutableList<ShoppingListItem>>> = arrayListOf();

        jsonReader.beginArray()

        while (jsonReader.hasNext()){
            jsonReader.beginObject()
            var map: HashMap<String,MutableList<ShoppingListItem>> = hashMapOf();
            var arrayShopping: MutableList<ShoppingListItem> = arrayListOf();
            var name: String = "";
            name = jsonReader.nextName()
            println(name)
            arrayShopping = readShoppingListArray(jsonReader)
            map[name] = arrayShopping;

            jsonReader.endObject()
            array.add(map);
        }
        //array.add(map);
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
        jsonReader.beginObject()
        var shoppingListItem: ShoppingListItem = ShoppingListItem();
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

    public fun addList(item: HashMap<String,MutableList<ShoppingListItem>>) {
        ITEMS.add(item)
    }

    private fun createPlaceholderItem(position: Int): HashMap<String, MutableList<ShoppingListItem>> {
        val shoppingList: ShoppingList = ShoppingList();
        return hashMapOf("Lista $position" to shoppingList.getItems());
    }

    public fun loadData(data: MutableList<HashMap<String, MutableList<ShoppingListItem>>>){
        ITEMS = data;
    }

}

data class ListsItem(val id: String, val title: String):Parcelable {
    private var products = ArrayList<ShoppingListItem>();

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,

    ) {
    }

    override fun toString(): String = title
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListsItem> {
        override fun createFromParcel(parcel: Parcel): ListsItem {
            return ListsItem(parcel)
        }

        override fun newArray(size: Int): Array<ListsItem?> {
            return arrayOfNulls(size)
        }
    }
}
