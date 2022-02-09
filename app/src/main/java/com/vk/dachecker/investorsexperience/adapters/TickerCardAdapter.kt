package com.vk.dachecker.investorsexperience.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.activities.MainActivity
import com.vk.dachecker.investorsexperience.databinding.CardInfoBinding
import com.vk.dachecker.investorsexperience.db.Company


class TickerCardAdapter(
    var clickListener: OnTickerCardClickListener,
    var clickShare: ShareListener
) :
    RecyclerView.Adapter<TickerCardAdapter.TickerCardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TickerCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_info, parent, false)
        return TickerCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: TickerCardViewHolder, position: Int) {
        holder.initialize(result.get(position), clickListener, clickShare)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    interface OnTickerCardClickListener {
        fun onItemClick(item: Company)
    }

    interface ShareListener {
        fun onShareClick(item: Company)
    }

    class TickerCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardInfoBinding.bind(view)

        fun initialize(company: Company, action: OnTickerCardClickListener, share: ShareListener) =
            with(binding) {
                tvTicker.text = company.ticker
                tvDescription.text = company.description
                tvDate.text = company.date

                itemView.setOnClickListener {
                    action.onItemClick(company)
                }

                imShare.setOnClickListener {
                    share.onShareClick(company)
                }
            }
    }

    companion object {
        var result = arrayListOf<Company>()
    }
}