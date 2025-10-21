package com.kodeco.memeverse.models

//Libraries
implementation 'com.google.firebase:firebase-firestore-ktx'
impplementation 'com.github.bumptech.glide:glide:4.15.1'
kapt 'com.github.bumptech.glide:compiler:4.15.1'

apply plugin: 'kotlin-kapt'
apply plugin: 'com.google.gms.google-services'


//Data Model
data class Meme(

)

//Recycle View
class MemeViewHolder(){
    class MemeViewHolder(){

    }

    override fun onCreateViewholder(){

    }
    override fun onBindViewHolder(){

    }

    override fun getItemCount(): Int = memeList.size
}

//Activity to Load MM
class MemeListActivity : AppCompatActivity(){

}