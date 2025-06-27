package com.example.mymovie.Domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovie.Domain.MovieItem
import com.example.mymovie.Domain.model.MovieModel
import com.example.mymovie.Domain.model.SimpleMovie
import com.example.mymovie.R
import com.example.mymovie.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class MovieAdapter: ListAdapter<MovieModel,MovieAdapter.Holder>(Comparator()) {

    class Holder(view: View) :RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)

        fun bind(item: MovieModel) = with(binding){
            tvMovieName.text = item.UserName
            Picasso.get().load("http:" + item.Image).into(imPoster)

        }
        }

    class Comparator : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem // Необходимо будет добавить проверку по ID
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem //Необходимо будет добавить проверку по полям
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}