package com.messages.abdallah.mymessages.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.messages.abdallah.mymessages.R
import com.messages.abdallah.mymessages.ViewModel.MsgsTypesViewModel
import com.messages.abdallah.mymessages.ViewModel.MsgsViewModel
import com.messages.abdallah.mymessages.ViewModel.MyViewModelFactory
import com.messages.abdallah.mymessages.ViewModel.ViewModelFactory
import com.messages.abdallah.mymessages.adapter.MsgsTypes_Adapter
import com.messages.abdallah.mymessages.api.ApiService

import com.messages.abdallah.mymessages.databinding.FragmentFirstBinding
import com.messages.abdallah.mymessages.db.LocaleSource
import com.messages.abdallah.mymessages.repository.MsgsRepo
import com.messages.abdallah.mymessages.repository.MsgsTypesRepo

import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class FirstFragment : Fragment() {
    private var _binding : FragmentFirstBinding?=null
    private val binding get() = _binding!!


    private val msgstypesAdapter by lazy {  MsgsTypes_Adapter() }

    private val retrofitService = ApiService.provideRetrofitInstance()
    private val mainRepository2 by lazy {  MsgsRepo(retrofitService, LocaleSource(requireContext())) }

    private val mainRepository by lazy {  MsgsTypesRepo(retrofitService, LocaleSource(requireContext())) }

    private val viewModel: MsgsTypesViewModel by viewModels{
        MyViewModelFactory(mainRepository,mainRepository2,requireActivity() as MainActivity)
    }


    private val viewModel2: MsgsViewModel by viewModels{
        ViewModelFactory(mainRepository2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        Log.e("tessst","entred")
        (activity as MainActivity).fragment = 1

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()


        adapterOnClick()
    }

    private fun adapterOnClick(){
        //لاحظ الفانكشن انها بترمي الid
//        msgstypesAdapter.onItemClick = {id, MsgTypes ->
        msgstypesAdapter.onItemClick = {id ->
//            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
//            val direction = FirstFragmentDirections.actionFirsFragmentToSecondFragment(id, MsgTypes)
            val direction = FirstFragmentDirections.actionFirsFragmentToSecondFragment(id)
            findNavController().navigate(direction)
        }
    }

    private fun setUpRv() = viewModel.viewModelScope.launch {

//        binding.rcMsgTypes.apply {
//            adapter = msgstypesAdapter
//            setHasFixedSize(true)
//        }



//        viewModel.getAllMsgsTypes().observe(viewLifecycleOwner) { listShows ->
//            msgstypesAdapter.msgsTypesModel = listShows
//            binding.rcMsgTypes.adapter = msgstypesAdapter
//        }

        viewModel.getPostsFromRoom(requireContext() as MainActivity).observe(requireActivity()) { listTvShows ->
            //     Log.e("tessst",listTvShows.size.toString()+"  adapter")

            val map =  loadMap(requireContext())
            val list:MutableList<HashMap<String,Int>> = mutableListOf()
            list.add(map)
            Log.e("sizeee",map.size.toString())
            msgstypesAdapter.msgsTypesModel = listTvShows
            msgstypesAdapter.counter = list
            binding.rcMsgTypes.layoutManager = LinearLayoutManager(requireContext())
            binding.rcMsgTypes.adapter = msgstypesAdapter
            Log.e("sizeee",map.size.toString())
        }

//        viewModel.getAllMsgsTypes().observe(viewLifecycleOwner) { listShows ->
//            msgstypesAdapter.msgsTypesModel = listShows
//            binding.rcMsgTypes.adapter = msgstypesAdapter
//        }
//


    }

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater = inflater
        inflater.inflate(R.menu.first_frag_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                    viewModel.refreshPosts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadMap(context: Context): HashMap<String, Int> {
        val outputMap: HashMap<String, Int> = HashMap()
        val pSharedPref: SharedPreferences =
            context.getSharedPreferences("MyVariables", Context.MODE_PRIVATE)
        try {
            if (pSharedPref != null) {
                val jsonString = pSharedPref.getString("My_map", JSONObject().toString())
                if (jsonString != null) {
                    val jsonObject = JSONObject(jsonString)
                    val keysItr = jsonObject.keys()
                    while (keysItr.hasNext()) {
                        val key = keysItr.next()
                        val value = jsonObject.getInt(key)
                        outputMap[key] = value
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return outputMap
    }

}