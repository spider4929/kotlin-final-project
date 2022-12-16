package com.example.midterm.datagrid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.midterm.MapResponse
import com.example.midterm.MapService
import com.example.midterm.R
import com.example.midterm.database.PathDatabase
import com.example.midterm.databinding.FragmentPathDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PathDetailFragment : Fragment() {
    private var mapData: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentPathDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_path_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = PathDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = PathDatabase.getInstance(application).pathDatabaseDao
        val viewModelFactory = PathDetailViewModelFactory(arguments.pathKey,dataSource)

        val pathDetailViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(PathDetailViewModel::class.java)

        binding.pathDetailViewModel = pathDetailViewModel

        binding.setLifecycleOwner(this)

        pathDetailViewModel.navigateToViewPath.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PathDetailFragmentDirections.actionPathDetailFragmentToViewPathFragment())
                pathDetailViewModel.doneNavigating()
            }
        })
        pathDetailViewModel.navigateToEditPath.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val pathKey = pathDetailViewModel.getPathKey()
                this.findNavController().navigate(
                    PathDetailFragmentDirections.actionPathDetailFragmentToEditPathFragment(pathKey))
                pathDetailViewModel.doneNavigating()
            }
        })

        mapData = binding.pathString
        var src: String? = null
        var dest: String? = null
        var BaseUrl = "https://www.mapquestapi.com/"
        var key = "utydmlZb3HL2uQ8DZasJ70h0hdSGIcC5"
        fun getCurrentData(){
                val path = pathDetailViewModel.getPath()
                val pathValue = path.value
                Log.i("RICK","$pathValue")
                if (pathValue != null) {
                    var from = pathValue.source
                    var to = pathValue.destination
                    src = from
                    dest = to
                    Log.i("RICK", from)
                    Log.i("RICK", to)
                    val retrofit = Retrofit.Builder()
                        .baseUrl(BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val service = retrofit.create(MapService::class.java)
                    val call = service.getCurrentMapData(from, to, key)
                    call.enqueue(object: Callback<MapResponse> {
                        override fun onResponse(call: Call<MapResponse>, response: Response<MapResponse>) {
                            if (response.code() == 200) {
                                val mapResponse = response.body()!!

                                val stringBuilder = "Distance: " + String.format("%.2f",(mapResponse.route!!.distance*1.61)) +
                                        " km\n\n" +
                                        "Estimated Time of Arrival: " + (mapResponse.route!!.time*5)/60 + " minutes"

                                mapData!!.text = stringBuilder
                            }
                        }
                        override fun onFailure(call: Call<MapResponse>, t: Throwable) {
                            mapData!!.text = t.message
                        }
                    })
                }
            }
        binding.LoadButton.setOnClickListener{
            getCurrentData()
        }

        fun openMaps() {
            if(src == null || dest == null){
                Toast.makeText(activity?.applicationContext,"Please use API button first to collect coordinates!", Toast.LENGTH_SHORT).show()
                return
            }
            val url = "http://maps.google.com/maps?saddr=" + src + "&daddr=" + dest + "&dirflg=w"
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
            startActivity(intent)
        }

        binding.openButton.setOnClickListener{
            openMaps()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Hide the bottom navigation view
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE

//        val toolbar = activity?.findViewById<Toolbar>(R.id.app_bar)
//        toolbar?.visibility = View.VISIBLE
    }

}