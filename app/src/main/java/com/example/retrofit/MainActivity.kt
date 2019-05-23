package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cell.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var api = Retrofit.Builder()
        .baseUrl("https://earthquake.usgs.gov")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuakeAPI::class.java)


    var quake = arrayListOf<QuakeEvents>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()


        api.getQuakes().enqueue(object : Callback<QuakeEventService> {
            override fun onFailure(call: Call<QuakeEventService>, t: Throwable) {
                Log.e("MainActivity","${t.localizedMessage}")
            }

            override fun onResponse(call: Call<QuakeEventService>, response: Response<QuakeEventService>) {
                val quakeEventService = response.body()
                if (quakeEventService != null) {
                    var result = quakeEventService.features.map { it.quakeEvents }
                    quake = arrayListOf()
                    quake.addAll(result)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        })

    }

    fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = QuakeAdapter()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    inner class QuakeAdapter: RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell,parent,false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int = quake.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (holder is ViewHolder) {
                holder.itemView.textView.text = "${quake[position].mag}"
            }
        }
    }

}


