package org.wit.recipebook.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_recipe.view.*
import org.wit.recipebook.R
import org.wit.recipebook.helpers.readImageFromPath
import org.wit.recipebook.models.RecipeModel


interface RecipeListener {
    fun onRecipeClick(recipe: RecipeModel)
}

class RecipeAdapter constructor(private var recipes: List<RecipeModel>,
                                   private val listener: RecipeListener) : RecyclerView.Adapter<RecipeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_recipe,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val recipe = recipes[holder.adapterPosition]
        holder.bind(recipe, listener)
    }

    override fun getItemCount(): Int = recipes.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(recipe: RecipeModel, listener: RecipeListener) {
            itemView.recipetitle.text = recipe.title
            itemView.description.text = recipe.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, recipe.image))
            itemView.setOnClickListener { listener.onRecipeClick(recipe) }
        }
    }
}