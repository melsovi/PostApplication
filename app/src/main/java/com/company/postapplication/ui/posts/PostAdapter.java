package com.company.postapplication.ui.posts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.company.postapplication.OnItemClickListener;
import com.company.postapplication.R;
import com.company.postapplication.data.models.Post;
import com.company.postapplication.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<Post> posts = new ArrayList<>();

    private Map<Integer, String> userMap = new HashMap<>();
    Set<Map.Entry<Integer, String>> set = userMap.entrySet();

    public PostAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        loadUserMap();
        return new PostViewHolder(binding);
    }

    private void loadUserMap() {
        userMap.put(0, "Рахат");
        userMap.put(1, "Мэльсов Ислам");
        userMap.put(2, "Советбеков Нурель");
        userMap.put(3, "Курманбеков Матай");
        userMap.put(4, "Жумабаев Марат");
        userMap.put(5, "Эрнисов Санжар");
        userMap.put(6, "Бурибоев Элмурод");
        userMap.put(7, "Турдукулов Нурмухаммед");
        userMap.put(8, "Исмаилов Арстанбек");
        userMap.put(9, "Эркинов Дастан");
        userMap.put(10, "Укуев Бексултан");
        userMap.put(11, "Гашигулин Рустам");
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.onBind(posts.get(position));
        Bundle bundle = new Bundle();
        bundle.putString("postId", String.valueOf(posts.get(position).getId()));

        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_postsFragment_to_formFragment, bundle));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onItemLongClick(String.valueOf(posts.get(position).getId()));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Post post) {
            binding.tvUserId.setText(userMap.get(post.getUserId()));
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
        }
    }
}
