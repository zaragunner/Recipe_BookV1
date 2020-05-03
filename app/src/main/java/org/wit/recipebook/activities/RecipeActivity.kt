package org.wit.recipebook.activities

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.activity_recipe.description
import kotlinx.android.synthetic.main.activity_recipe_list.*
import kotlinx.android.synthetic.main.card_recipe.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.recipebook.main.MainApp
import org.wit.recipebook.R
import org.wit.recipebook.helpers.readImage
import org.wit.recipebook.helpers.readImageFromPath
import org.wit.recipebook.helpers.showImagePicker
import org.wit.recipebook.models.Location
import org.wit.recipebook.models.RecipeModel
import java.io.IOException


class RecipeActivity : AppCompatActivity(), AnkoLogger {
    var recipe = RecipeModel()
    lateinit var app: MainApp
    lateinit var  ref : DatabaseReference
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var edit = false

//    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        app = application as MainApp

        ref = FirebaseDatabase.getInstance().getReference("recipes")




        if (intent.hasExtra("recipe_edit")) {
            edit = true
            recipe = intent.extras.getParcelable<RecipeModel>("recipe_edit")
            recipeTitle.setText(recipe.title)
            description.setText(recipe.description)
            countryView.setText(recipe.country)
            recipeIngredients.setText(recipe.ingredients)
            recipeMethod.setText(recipe.method)

            recipeIngredients.setText(recipe.ingredients)

            recipeImage.setImageBitmap(readImageFromPath(this, recipe.image))
            if (recipe.image != null) {
                chooseImage.setText(R.string.change_recipe_image)
            }
            btnAdd.setText(R.string.save_recipe)


        }






        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        btnAdd.setOnClickListener() {

            recipe.title = recipeTitle.text.toString()
            recipe.description = description.text.toString()
            recipe.ingredients=recipeIngredients.text.toString()
            recipe.country= countryView.text.toString()
            recipe.method = recipeMethod.text.toString()
            if (recipe.title.isEmpty()) {
                toast(R.string.enter_recipe_title)
            } else {
                if (edit) {
                    app.recipes.update(recipe.copy())
                } else {

                    app.recipes.create(recipe.copy())
                      val recId = ref.push().key
                    val recipe = recipe.copy()
                    if (recId != null) {
                        ref.child(recId).setValue(recipe).addOnCompleteListener{
                            Toast.makeText(applicationContext, "Recipe Saved Successfully", Toast.LENGTH_LONG).show()
                    }

                    }

                }
            }
            info("add Button Pressed: $recipeTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()


        }

//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                    if(p0!!.exists()){
//                        for( i in p0.children){
//                            val recipe = i.getValue(RecipeModel::class.java)
//                            app.recipes.create(recipe!!)
//                    }
//
//            }}
//
//        })  attempt to load from database



        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)

        }


        recipeLocation.setOnClickListener {
        val location =Location(52.245696, -7.139102, 15f)
            if (recipe.zoom != 0f) {
                location.lat = recipe.lat
                location.lng = recipe.lng
                location.zoom = recipe.zoom
            }

            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location),
                LOCATION_REQUEST
            )
        }








    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recipe, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.recipes.delete(recipe)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //    Researched how to return the country of a ma
//        echotopia.com/index.php/Kotlin_-_Working_with_the_Google_Maps_Android_API_in_Android_Studio#.EF.BB.BFUnderstanding_Geocoding.EF.BB.BF_and_Reverse_Geocoding.EF.BB.BF
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var geocodeMatches: List<Address>? = null
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    recipe.image = data.getData().toString()
                    recipeImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_recipe_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    recipe.lat = location.lat
                    recipe.lng = location.lng
                    recipe.zoom = location.zoom

                }


                try {
                    geocodeMatches = Geocoder(this).getFromLocation(recipe.lat, recipe.lng, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (geocodeMatches != null) {
                    recipe.country = geocodeMatches[0].countryName.toString()
                }
                countryView.setText(recipe.country)
            }
        }}
    }






