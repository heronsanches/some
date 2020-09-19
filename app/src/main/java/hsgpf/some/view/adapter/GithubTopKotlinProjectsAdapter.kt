package hsgpf.some.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hsgpf.some.databinding.ItemGithubRepositoryBinding
import hsgpf.some.model.datasource.remote.retrofit.data.github.GithubRepositoryData

class GithubTopKotlinProjectsAdapter
   : PagedListAdapter<GithubRepositoryData, GithubTopKotlinProjectsAdapter.VH>(diffCallBack) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
      val item =
         ItemGithubRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return VH(item)
   }

   override fun onBindViewHolder(holder: VH, position: Int) {
      holder.item.repository = getItem(position)
   }

   class VH(val item: ItemGithubRepositoryBinding) : RecyclerView.ViewHolder(item.root)

   companion object {

      val diffCallBack = object : DiffUtil.ItemCallback<GithubRepositoryData>() {

         override fun areItemsTheSame(oldItem: GithubRepositoryData,
                                      newItem: GithubRepositoryData): Boolean {
            return oldItem.id == newItem.id
         }

         override fun areContentsTheSame(oldItem: GithubRepositoryData,
                                         newItem: GithubRepositoryData): Boolean {
            return oldItem == newItem
         }
      }
   }
}