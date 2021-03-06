package org.wit.recipebook.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import com.mancj.materialsearchbar.MaterialSearchBar

import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.jetbrains.anko.intentFor

import org.jetbrains.anko.startActivityForResult
import org.wit.recipebook.R
import org.wit.recipebook.main.MainApp
import org.wit.recipebook.models.RecipeModel


class RecipeListActivity : AppCompatActivity(), RecipeListener {

    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        app = application as MainApp
//        val searchBar = findViewById(R.id.searchCat) as MaterialSearchBar


        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = recipeAdapter(app.recipes)
        recyclerView.adapter = RecipeAdapter(app.recipes.findAll(), this)
        loadRecipes()

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
//val adapter = RecipeAdapter(app.recipes.findAll(), this)
//
//
//        searchBar.addTextChangeListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//                //SEARCH FILTER
//              RecipeAdapter.getFilter().filter(charSequence)
//
//            }
//
//            override fun afterTextChanged(editable: Editable) {
//
//            }
//        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<RecipeActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRecipeClick(recipe: RecipeModel) {
        startActivityForResult(intentFor<RecipeActivity>().putExtra("recipe_edit", recipe), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadRecipes()
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun loadRecipes() {
        showRecipes(app.recipes.findAll())
    }

    fun showRecipes (recipes: List<RecipeModel>) {
        recyclerView.adapter = RecipeAdapter(recipes, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}






