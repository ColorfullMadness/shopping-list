package com.example.zad2_shopping

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val LIST_POSITION_PARAM = "list_position"

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteListDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteListDialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var listPositionParam: Int? = null
    lateinit var mListener: OnDeleteDialogInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listPositionParam = it.getInt(LIST_POSITION_PARAM)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Delete this entry" + " $listPositionParam")
        builder.setPositiveButton("Confirm", DialogInterface.OnClickListener(){ dialogInterface, i ->
            mListener?.onDialogPositiveClick(listPositionParam)
        })
        builder.setNegativeButton("Discard", DialogInterface.OnClickListener(){ dialogInterface, i ->
            mListener?.onDialogNegativeClick(listPositionParam)
        })
        return builder.create()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @param pos Parameter 2.
         * @return A new instance of fragment DeleteDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(pos: Int, interactionListener: OnDeleteDialogInteractionListener) =
            DeleteListDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(LIST_POSITION_PARAM, pos)
                }
                mListener = interactionListener
            }
    }

    interface OnDeleteDialogInteractionListener{
        fun onDialogPositiveClick(pos: Int?)
        fun onDialogNegativeClick(pos: Int?)
    }
}