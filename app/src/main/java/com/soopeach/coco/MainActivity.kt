package com.soopeach.coco

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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


        uriList.add("https://helpx.adobe.com/content/dam/help/en/photoshop/using/quick-actions/remove-background-before-qa1.png".toUri())
        uriList.add("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E".toUri())
        uriList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxmp7sE1ggI4_L7NGZWcQT9EyKaqKLeQ5RBg&usqp=CAU".toUri())

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = imgAdapter(uriList)

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val uri = uriList[position]
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("uri", uri.toString())
                startActivity(intent)
//                Toast.makeText(this@MainActivity, uri.toString(), Toast.LENGTH_SHORT).show()
            }
        })

//        CoroutineScope(Dispatchers.IO).launch {
//            getImg(adapter)
//        }
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
