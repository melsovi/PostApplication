package com.company.postapplication.data.remote;

import com.company.postapplication.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/posts?sort=asc")
    Call<List<Post>> getGroupPosts(
            @Query("group") int groupId
    );

    @GET("/posts/{id}")
    Call<Post> getPost(
            @Path("id") String id
    );

    @POST("/posts")
    Call<Post> createPost(
            @Body Post post
    );

    @PUT("/posts/{id}")
    Call<Post> editPost (
            @Path("id") String id,
            @Body Post post
    );

    @DELETE("/posts/{id}")
    Call<Post> deletePost (
            @Path("id") String id
    );
}
