package com.example.movieapp_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp_xml.databinding.ItemCharacterBinding
import com.example.movieapp_xml.model.Person // Giả định model Person nằm ở đây

class CharacterAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Person, CharacterAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = getItem(position)
        val imageUrl = "https://image.tmdb.org/t/p/w500${person.profile_path}" // Dựa trên logic Constants

        holder.binding.ivCharacter.load(imageUrl) {
            crossfade(true)
        }

        holder.binding.root.setOnClickListener { onClick(person.id) }
    }

    object DiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
    }
}