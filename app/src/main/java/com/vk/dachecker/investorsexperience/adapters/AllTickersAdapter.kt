package com.vk.dachecker.investorsexperience.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.activities.MainActivity
import com.vk.dachecker.investorsexperience.databinding.CardInfoBinding
import com.vk.dachecker.investorsexperience.databinding.ListOfTickersBinding
import com.vk.dachecker.investorsexperience.db.Company


class AllTickersAdapter(var clickListener: OnTickerCardClickListener) :
RecyclerView.Adapter<AllTickersAdapter.TickerCardViewHolder>(){

    var itemList = listOf<String>()

    class TickerCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListOfTickersBinding.bind(view)

        fun initialize(ticker : String, action: OnTickerCardClickListener) = with(binding){
            tvTicker.text = ticker

            //если не сработает, добавить в конструктор листенера позицию
            itemView.setOnClickListener {
                action.onItemClick(ticker)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TickerCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_tickers, parent, false)
        return TickerCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: TickerCardViewHolder, position: Int) {
        holder.initialize(itemList.get(position), clickListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    //если не сработает, то добавить в конструктор position: Int
    interface OnTickerCardClickListener{
        fun onItemClick(item: String)
    }

//    companion object {
//        var result = arrayListOf<String>()}
}