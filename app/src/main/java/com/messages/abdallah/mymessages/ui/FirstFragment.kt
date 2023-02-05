package com.messages.abdallah.mymessages.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.messages.abdallah.mymessages.ViewModel.MsgsTypesViewModel
import com.messages.abdallah.mymessages.adapter.MsgsTypes_Adapter
import com.messages.abdallah.mymessages.api.ApiService
import com.messages.abdallah.mymessages.api.ApiService.Companion.retrofitService

import com.messages.abdallah.mymessages.databinding.FragmentFirstBinding
import com.messages.abdallah.mymessages.models.MsgsTypesModel
import com.messages.abdallah.mymessages.repository.MsgsTypesRepo

class FirstFragment : Fragment() {
    private var _binding : FragmentFirstBinding?=null
    private val binding get() = _binding!!


    private val msgstypesAdapter by lazy {  MsgsTypes_Adapter() }

    private val retrofitService = ApiService.provideRetrofitInstance()
    private val mainRepository = MsgsTypesRepo(retrofitService)//, LocaleSource(this))
    private val viewModel: MsgsTypesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRv()


        adapterOnClick()
    }

    private fun adapterOnClick(){
        //لاحظ الفانكشن انها بترمي الid
        msgstypesAdapter.onItemClick = {
//            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            val direction = FirstFragmentDirections.actionFirsFragmentToSecondFragment(it)
            findNavController().navigate(direction)
        }
    }

    private fun setUpRv() {

//        binding.rcMsgTypes.apply {
//            adapter = msgstypesAdapter
//            setHasFixedSize(true)
//        }



            viewModel.responseMsgsTypes.observe(viewLifecycleOwner) { listTvShows ->
                msgstypesAdapter.msgsTypesModel = listTvShows
                binding.rcMsgTypes.adapter = msgstypesAdapter
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}