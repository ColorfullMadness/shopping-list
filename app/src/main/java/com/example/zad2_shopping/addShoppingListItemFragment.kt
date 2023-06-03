package com.example.zad2_shopping

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zad2_shopping.data.Lists
import com.example.zad2_shopping.data.ShoppingListItem
import com.example.zad2_shopping.data.Units
import com.example.zad2_shopping.databinding.FragmentAddShoppingListItemBinding

class addShoppingListItemFragment : Fragment() {

    private lateinit var binding: FragmentAddShoppingListItemBinding

    val args: addShoppingListItemFragmentArgs by navArgs()
    private var positionList: Int = 0;
    private var positionShoppingList: Int = 0;
    private var edit: Boolean = false;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddShoppingListItemBinding.inflate(inflater,container,false)

        positionList = args.positionList;
        positionShoppingList = args.positionShoppingList;
        edit = args.edit

        if(edit){
            binding.buttonCreate.text = "Save"
            binding.itemName.setText(Lists.ITEMS[positionList].values.first().get(positionShoppingList).name);
            binding.spinnerUnits.setSelection(Lists.ITEMS[positionList].values.first().get(positionShoppingList).units.ordinal)
            binding.textEditAmount.setText(Lists.ITEMS[positionList].values.first().get(positionShoppingList).amount.toString())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreate.setOnClickListener(){buttonCreateClick()}

    }

    private fun buttonCreateClick() {
        var name = binding.itemName.text.toString()
        var amount_s = binding.textEditAmount.text.toString()
        val unit: Units =  when(binding.spinnerUnits.selectedItem.toString()){
            "Units" -> Units.Units
            "ml" -> Units.ml
            "g" -> Units.g
            else -> {Units.Units}
        }
        var amount =0;

        if (name.isEmpty()) name = "product";
        if (!amount_s.isEmpty() && amount_s.toInt()>0) amount = amount_s.toInt();
        else amount = 1;

        if(edit){
            Lists.ITEMS[positionList].values.first().get(positionShoppingList).amount = amount;
            Lists.ITEMS[positionList].values.first().get(positionShoppingList).name = name;
            Lists.ITEMS[positionList].values.first().get(positionShoppingList).units = unit;
        } else {
            val item = ShoppingListItem(name,name,false,unit,amount);

            Lists.ITEMS[positionList].values.first().add(item);
        }

        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken,0)

        findNavController().popBackStack(R.id.shoppingListFragment,false);
    }
}