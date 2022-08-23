package com.soopeach.coco

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.soopeach.coco.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val uriList = mutableListOf<Uri>()
    val storage = Firebase.storage("gs://coco-ae2c2.appspot.com")
    val storageRef = storage.reference
    var thumbnailsRef = storageRef.child("thumbnails")
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

//        uriList.add("https://helpx.adobe.com/content/dam/help/en/photoshop/using/quick-actions/remove-background-before-qa1.png".toUri())
//        uriList.add("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E".toUri())
//        uriList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxmp7sE1ggI4_L7NGZWcQT9EyKaqKLeQ5RBg&usqp=CAU".toUri())

        super.onCreate(savedInstanceState)
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

        CoroutineScope(Dispatchers.IO).launch {
//            getImg(adapter)
            listAllPaginated(null, adapter)

        }
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 4)

    }

    // 모든 이미지 한 번에 가져오기 - 안씀.
    fun getImg(adapter: imgAdapter) {
        var cnt = 0
        thumbnailsRef.listAll().addOnSuccessListener {
            it.items.forEach {
                it.downloadUrl.addOnCompleteListener {
                    uriList.add(it.result)
//                    println(uriList)
                    adapter.notifyDataSetChanged()
                    println("${++cnt} 번째 업데이트")
                }
            }
        }
    }

    fun listAllPaginated(pageToken: String?, adapter: imgAdapter) {

        // Fetch the next page of results, using the pageToken if we have one.
//        val listPageTask = if (pageToken != null) {
//            spaceRef.list(10, pageToken)
//        } else {
//            spaceRef.list(10)
//        }
        thumbnailsRef.list(100).addOnSuccessListener {
            it.items.forEach {
                it.downloadUrl.addOnCompleteListener {
                    uriList.add(it.result)
                    if (uriList.size >= 45){
                        binding.shimmerFrameLayout.stopShimmer()
                        binding.shimmerFrameLayout.visibility = View.GONE
                    }
//                    println(uriList)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // You'll need to import com.google.firebase.storage.ktx.component1 and
        // com.google.firebase.storage.ktx.component2
//        listPageTask
//            .addOnSuccessListener { (items, prefixes, pageToken) ->
//                // Process page of results
////                processResults(items, prefixes)
//                adapter.notifyDataSetChanged()
//                // Recurse onto next page
////                pageToken?.let {
////                    listAllPaginated(it)
////                }
//            }.addOnFailureListener {
//                // Uh-oh, an error occurred.
//            }
    }

}
