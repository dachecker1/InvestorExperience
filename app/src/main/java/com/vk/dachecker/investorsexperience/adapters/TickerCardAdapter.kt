package com.vk.dachecker.investorsexperience.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.activities.MainActivity
import com.vk.dachecker.investorsexperience.databinding.CardInfoBinding
import com.vk.dachecker.investorsexperience.db.Company


class TickerCardAdapter(var clickListener: OnTickerCardClickListener) :
RecyclerView.Adapter<TickerCardAdapter.TickerCardViewHolder>(){

    class TickerCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardInfoBinding.bind(view)

        fun initialize(company: Company, action: OnTickerCardClickListener) = with(binding){
            tvTicker.text = company.ticker
            tvDescription.text = company.description
            tvDate.text = company.date

            //если не сработает, добавить в конструктор листенера позицию
            itemView.setOnClickListener {
                action.onItemClick(company)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TickerCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_info, parent, false)
        return TickerCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: TickerCardViewHolder, position: Int) {
        holder.initialize(result.get(position), clickListener)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    //если не сработает, то добавить в конструктор position: Int
    interface OnTickerCardClickListener{
        fun onItemClick(item: Company)
    }

    companion object {
        var result = arrayListOf<Company>()}
}