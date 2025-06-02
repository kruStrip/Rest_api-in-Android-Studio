package com.example.myapplication.presentaition.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemCharacterBinding
import com.example.myapplication.domain.models.Character

/**
 * ListAdapter для отображения списка персонажей Rick & Morty.
 * Принимает List<Character> (доменная модель) и обновляет RecyclerView автоматически при изменении списка.
 */
class CharactersAdapter :
    ListAdapter<Character, CharactersAdapter.CharacterViewHolder>(CharacterDiffCallback) {

    /**
     * ViewHolder, который хранит binding к item_character.xml и умеет «биндить» данные.
     */
    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Привязывает данные из [item] (Character) к View-элементам.
         */
        fun bind(item: Character) = with(binding) {
            tvName.text = item.name
            tvStatus.text = item.status
            // Загружаем картинку через Glide
            Glide.with(root.context)
                .load(item.image)
                .into(ivAvatar)
        }
    }

    /**
     * Создаёт новый ViewHolder, «надувая» layout item_character.xml.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    /**
     * Вызывается при привязке данных к конкретному ViewHolder.
     */
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    /**
     * DiffUtil позволяет RecyclerView понимать, как обновился список:
     * какие элементы добавились/удалились/изменились, чтобы анимировать вставку/удаление.
     */
    companion object {
        private val CharacterDiffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                // Считаем, что это один и тот же объект, если совпадает ID
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                // Проверяем полное равенство всех полей
                return oldItem == newItem
            }
        }
    }
}
