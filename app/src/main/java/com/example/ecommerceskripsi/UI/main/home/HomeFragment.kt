package com.example.ecommerceskripsi.UI.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerceskripsi.Data.api.main.seller.sellerCategory.CategoryResponse
import com.example.ecommerceskripsi.Data.api.main.buyerProduct.ProductResponse
import com.example.ecommerceskripsi.R
import com.example.ecommerceskripsi.UI.auth.login.LoginActivity
import com.example.ecommerceskripsi.UI.main.home.adapter.CategoryAdapter
import com.example.ecommerceskripsi.UI.main.home.adapter.ProductAdapter
import com.example.ecommerceskripsi.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    companion object {
        var result = 0
        const val PRODUCT_ID = "id"
    }
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindView()

        val status = "available"
        val categoryId = ""
        val search = ""
        viewModel.getProductBuyer(status = status, categoryId = categoryId, search = search)
        viewModel.getUserData()
        viewModel.getCategory()
    }

    private fun bindViewModel() {
        viewModel.showLogin.observe(viewLifecycleOwner) {
            Intent(activity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        viewModel.showCategory.observe(viewLifecycleOwner){
            categoryAdapter.submitData(it.data)
        }

        viewModel.showProductBuyer.observe(viewLifecycleOwner){
            productAdapter.submitData(it.produk)
        }

        viewModel.showUser.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.imageUrl.toString())
                .placeholder(
                    AvatarGenerator
                        .AvatarBuilder(requireContext())
                        .setTextSize(50)
                        .setAvatarSize(200)
                        .toSquare()
                        .setLabel(it.fullName.toString())
                        .build()
                )
                .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                .into(binding.ivProfileAcc)

            binding.tvProfileName.text = it.fullName
            binding.tvProfileAddress.text = it.address
        }
    }

    private fun bindView(){
        binding.tvLogout.setOnClickListener {
            viewModel.clearCredential()
        }

        binding.tvEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_editProfileFragment)
        }

        categoryAdapter = CategoryAdapter(object: CategoryAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponse.Data) {
                val status = "available"
                val search = ""
                viewModel.getProductBuyer(categoryId = data.id.toString(), status = status, search = search)
            }
        })
        binding.rvCategoryHome.adapter = categoryAdapter

        productAdapter = ProductAdapter(object: ProductAdapter.OnClickListener{
            override fun onClickItem(data: ProductResponse.Produk) {
//                val bundle = Bundle()
//                bundle.putInt(PRODUCT_ID, data.id.hashCode())
//                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
            }
        })
//        binding.rvProductHome.layoutManager =
//            GridLayoutManager(requireContext(), 2)
//        binding.rvProductHome.isNestedScrollingEnabled = false
        binding.rvProductHome.adapter = productAdapter

        binding.tvPostProduct.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_uploadProduct)
        }

        binding.etSearchProduct.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                val onSearch = binding.etSearchProduct.text.toString()
                viewModel.getProductBuyer(search = onSearch, status = "available", categoryId = "")
                true
            }else{
                false
            }
        }
    }
}
