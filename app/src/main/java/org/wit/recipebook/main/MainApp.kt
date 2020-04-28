package org.wit.recipebook.main


import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.recipebook.models.RecipeJSONStore
import org.wit.recipebook.models.RecipeMemStore
import org.wit.recipebook.models.RecipeStore

class MainApp : Application(), AnkoLogger {

    lateinit var recipes: RecipeStore

    override fun onCreate() {
        super.onCreate()
        recipes = RecipeJSONStore(applicationContext)
        info("Recipe Book started")
    }
}