package com.vk.dachecker.investorsexperience.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.databinding.ListOfTickersBinding


class AllTickersAdapter(var clickListener: OnTickerCardClickListener) :
    RecyclerView.Adapter<AllTickersAdapter.TickerCardViewHolder>() {

    var itemList = listOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TickerCardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_of_tickers, parent, false)
        return TickerCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: TickerCardViewHolder, position: Int) {
        holder.initialize(itemList.get(position), clickListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface OnTickerCardClickListener {
        fun onItemClick(item: String)
    }

    class TickerCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListOfTickersBinding.bind(view)

        fun initialize(ticker: String, action: OnTickerCardClickListener) = with(binding) {
            tvTicker.text = ticker
            itemView.setOnClickListener {
                action.onItemClick(ticker)
            }
        }
    }
}