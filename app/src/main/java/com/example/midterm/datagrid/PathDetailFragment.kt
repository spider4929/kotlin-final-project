package com.example.midterm.datagrid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.midterm.MapResponse
import com.example.midterm.MapService
import com.example.midterm.R
import com.example.midterm.database.PathDatabase
import com.example.midterm.databinding.FragmentPathDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

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
//        source = pathDetailViewModel.getPathFrom
//        destination = pathDetailViewModel.getPathTo
        mapData = binding.pathString

        var BaseUrl = "https://www.mapquestapi.com/"
        var key = "utydmlZb3HL2uQ8DZasJ70h0hdSGIcC5"
        var from = (binding.pathDest.text.toString())
        var to = (binding.pathSrc.text.toString())

        fun getCurrentData() {
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
        getCurrentData()
        return binding.root
    }
}