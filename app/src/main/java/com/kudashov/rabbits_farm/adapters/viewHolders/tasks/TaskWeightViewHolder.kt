package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.domain.WeightListItem

class TaskWeightViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val editTxtWeight = view.findViewById<EditText>(R.id.edit_txt_weight)
    private var weightListItem: WeightListItem? = null

    init {
        editTxtWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (editTxtWeight.text.isNotEmpty())
                    weightListItem?.weight = editTxtWeight.text.toString().toDouble()
                else weightListItem?.weight = null
            }
        })
    }

    fun bind(weightListItem: WeightListItem) {
        this.weightListItem = weightListItem

        if (weightListItem.weight != null)
            editTxtWeight.setText(weightListItem.weight.toString())
    }
}