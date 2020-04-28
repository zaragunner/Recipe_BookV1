package org.wit.recipebook.models

interface RecipeStore {

        fun findAll(): List<RecipeModel>
        fun create(recipe:RecipeModel)
    fun update(recipe: RecipeModel)
    fun delete(recipe: RecipeModel)
    }