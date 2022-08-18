package com.soopeach.coco

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.soopeach.coco.databinding.ActivityMainBinding
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.component3
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val uriList = mutableListOf<Uri>()
    val storage = Firebase.storage
    val storageRef = storage.reference
    var spaceRef = storageRef.child("images")
//    val gsReference = storage.getReferenceFromUrl("gs://cocomissyou-265f2.appspot.com/images/smallSoopeach.jpeg")


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = imgAdapter(uriList)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val uri = uriList[position]
                println(uri)
            }
        })

        CoroutineScope(Dispatchers.IO).launch {
            getImg(adapter)
        }
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 4)

    }

    fun getImg(adapter: imgAdapter) {
        spaceRef.listAll().addOnSuccessListener {
            it.items.forEach {
                it.downloadUrl.addOnCompleteListener {
                    uriList.add(it.result)
//                    println(uriList)
                    adapter.notifyDataSetChanged()
                }
            }

        }
    }

}

// 전체를 가져온다.
//spaceRef.listAll().addOnSuccessListener {
//    it.items.forEach{
//        it.downloadUrl.addOnCompleteListener {
//            Glide.with(this).load(it.result).into(img)
//            Thread.sleep(500)
//        }
//    }
//}