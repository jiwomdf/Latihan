package com.katilijiwo.latihan.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.katilijiwo.latihan.databinding.ListFooterBinding

class FooterAdapter(
    val retry: () -> Unit,
    val errMsg: (errMsg: String) -> Unit
): LoadStateAdapter<FooterAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ListFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val binding: ListFooterBinding): RecyclerView.ViewHolder(binding.root){
        private val loading = 1
        private val error = 2
        private val notLoading = 3

        init {
            binding.ivRetryFooter.setOnClickListener {
                setComponentVisibility(1)
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            when(loadState){
                is LoadState.Loading -> {
                    setComponentVisibility(1)
                }
                is LoadState.Error -> {
                    errMsg.invoke(loadState.error.message.toString())
                    setComponentVisibility(2)
                }
                is LoadState.NotLoading -> {
                    setComponentVisibility(3)
                }
            }
        }

        private fun setComponentVisibility(status: Int){
            when(status){
                loading -> {
                    binding.ivRetryFooter.visibility = View.GONE
                    binding.pbFooter.visibility = View.VISIBLE
                }
                error -> {
                    binding.ivRetryFooter.visibility = View.VISIBLE
                    binding.pbFooter.visibility = View.GONE
                }
                notLoading -> {
                    binding.ivRetryFooter.visibility = View.GONE
                    binding.pbFooter.visibility = View.GONE
                }
            }
        }
    }
}