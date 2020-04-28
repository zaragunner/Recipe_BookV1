package org.wit.recipebook.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import android.accounts.AuthenticatorDescription

@Parcelize
data class RecipeModel (var id: Long = 0,
                        var title: String = "",
                        var description: String = "",
                        var image: String = "",
                        var lat: Double = 0.0,
                        var lng: Double = 0.0,
                        var zoom: Float = 0f,
                        var country: String = "",
                        var ingredients: String= ""

                            ): Parcelable
@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f,
                    var country: String = ""
                   ) : Parcelable