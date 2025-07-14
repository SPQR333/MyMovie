package com.example.mymovie.Domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovie.Domain.MovieItem
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.R
import com.example.mymovie.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val onItemClick: (MovieModel) -> Unit = {}
) : ListAdapter<MovieModel, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    inner class MovieViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieModel) {
            with(binding) {
                tvMovieName.text = item.title

                // Используем Picasso с обработкой ошибок
                Picasso.get()
                    .load("https://${item.poster}")  // Убедитесь, что в item.poster нет дублирования "http:"
                    .placeholder(R.drawable.searchview_bg) // Заглушка при загрузке
                    .error(R.drawable.ic_launcher_background) // Если ошибка загрузки
                    .into(imPoster)

                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id // Лучше сравнивать по уникальному ID
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.poster == newItem.poster // Сравниваем только необходимые поля
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}