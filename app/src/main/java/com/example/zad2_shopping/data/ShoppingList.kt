package com.example.zad2_shopping.data

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
class ShoppingList {

    val ITEMS: MutableList<ShoppingListItem> = ArrayList()

    private val COUNT = 8

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            ITEMS.add(createPlaceholderItem(i))
        }
    }

    public fun itemsFromName(title: String): MutableList<ShoppingListItem> {
        this.ITEMS.clear()
        for (i in 1..Lists.ITEMS.count()) {
            ITEMS.add(createPlaceholderFromTitle(i, title))
        }
        return this.ITEMS
    }

    public fun getItems(): MutableList<ShoppingListItem>{
        return ITEMS;
    }

    public fun changeCheckedStatus(position: Int) {
        ITEMS[position].checked = !ITEMS[position].checked;
    }

    private fun createPlaceholderFromTitle(position: Int, title: String): ShoppingListItem {
        return ShoppingListItem(position.toString(), "$title ", false, Units.Units, 1)
    }

    private fun createPlaceholderItem(position: Int): ShoppingListItem {
        return ShoppingListItem(position.toString(), "Item $position", false, Units.Units, 1)
    }
}

data class ShoppingListItem(
    var id: String = "marmolada1",
    var name: String = "marmolada",
    var checked: Boolean = false,
    var units: Units = Units.Units,
    var amount: Int = 1): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        Units.values()[parcel.readInt()],
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "name: $name, chekced: $checked, units: $units, amount $amount";
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeByte(if (checked) 1 else 0)
        parcel.writeInt(units.ordinal)
        parcel.writeInt(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingListItem> {
        override fun createFromParcel(parcel: Parcel): ShoppingListItem {
            return ShoppingListItem(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingListItem?> {
            return arrayOfNulls(size)
        }
    }

}

enum class Units {
    Units,
    ml,
    g
}
