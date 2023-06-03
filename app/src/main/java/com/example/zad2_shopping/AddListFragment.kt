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
import com.example.zad2_shopping.data.ListsItem
import com.example.zad2_shopping.data.ShoppingList
import com.example.zad2_shopping.databinding.FragmentAddListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [AddListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddListFragment : Fragment() {

    private lateinit var binding: FragmentAddListBinding

    var edit: Boolean = false;
    var position: Int = 0;

    val args: AddListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddListBinding.inflate(inflater, container, false)

        position = args.position
        edit = args.edit

        if (edit){
            binding.listName.setText(Lists.ITEMS[position].keys.first())
            binding.buttonCreate.text = "Save"
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCreate.setOnClickListener(){createButtonClick()}
    }

    private fun createButtonClick() {
        if(edit){
            Lists.ITEMS[position].put(binding.listName.text.toString(),Lists.ITEMS[position].remove(Lists.ITEMS[position].keys.first())!!);
        } else {
            var title: String = binding.listName.text.toString()

            if(title.isEmpty()) title = getString(R.string.default_task_title) + "${Lists.ITEMS.size + 1}"

            val shoppingList: ShoppingList = ShoppingList();
            Lists.ITEMS.add(hashMapOf(title to shoppingList.itemsFromName(title)))
        }
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken,0)

        findNavController().popBackStack(R.id.listsFragment,false);
    }
}