package com.messages.abdallah.mymessages.ui

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

            msgstypesAdapter.msgsTypesModel = listTvShows
            binding.rcMsgTypes.layoutManager = LinearLayoutManager(requireContext())
            binding.rcMsgTypes.adapter = msgstypesAdapter

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

}