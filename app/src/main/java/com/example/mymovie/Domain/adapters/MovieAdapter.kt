package com.example.mymovie.Domain.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.DataSource
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mymovie.Domain.model.Movie
import com.example.mymovie.R
import com.example.mymovie.databinding.ListItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.Proxy
import java.net.ProxySelector
import java.net.URI
import java.net.URL
import java.util.concurrent.TimeUnit

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit = {}
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    inner class MovieViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Movie) {
            with(binding) {
                tvMovieName.text = item.title

                val imageUrl = if (item.poster.startsWith("http")) {
                    item.poster
                } else {
                    "https://image.tmdb.org/t/p/w500${item.poster}"
                }

                Log.d("IMAGE_DEBUG", "Loading: $imageUrl")

                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.searchview_bg)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imPoster)
            }
        }

    }




    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id // Лучше сравнивать по уникальному ID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
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
        getItem(position)?.let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                onItemClick(movie) // Убедитесь, что это вызывается
            }
        }
    }
}