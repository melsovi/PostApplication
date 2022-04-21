package com.company.postapplication.ui.form;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.postapplication.App;
import com.company.postapplication.R;
import com.company.postapplication.data.models.Post;
import com.company.postapplication.databinding.FragmentFormBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormFragment extends Fragment {
    private static final int userId = 2;
    private static final int groupId = 41;
    private FragmentFormBinding binding;

    private boolean isEdit = false;

    private String postId = null;

    public FormFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            isEdit = true;
            postId = bundle.getString("postId");
            Log.e("TAG", "onViewCreated: " + postId);

            App.api.getPost(postId).enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        binding.etTitle.setText(response.body().getTitle());
                        binding.etContent.setText(response.body().getContent());
                    } else {
                        Snackbar.make(
                                binding.getRoot(),
                                response.message(),
                                BaseTransientBottomBar.LENGTH_LONG
                        ).setBackgroundTint(Color.RED)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Snackbar.make(
                            binding.getRoot(),
                            t.getLocalizedMessage(),
                            BaseTransientBottomBar.LENGTH_LONG
                    ).setBackgroundTint(Color.RED)
                            .show();
                }
            });
        }

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.etTitle.getText().toString();
                String content = binding.etContent.getText().toString();
                if (isEdit) {
                    Post post = new Post(title, content, userId, groupId);
                    App.api.editPost(postId, post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                requireActivity().onBackPressed();
                                Toast.makeText(requireActivity(), "Post was successfully edited", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Log.e("Not edited", t.getLocalizedMessage());
                            Snackbar.make(
                                    binding.getRoot(),
                                    t.getLocalizedMessage(),
                                    BaseTransientBottomBar.LENGTH_LONG
                            ).setBackgroundTint(Color.RED)
                                    .show();
                        }
                    });
                } else {
                    Post post = new Post(title, content, userId, groupId);
                    App.api.createPost(post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                requireActivity().onBackPressed();
                                Toast.makeText(requireActivity(), "Post was successfully created", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }
            }

        });
    }
}