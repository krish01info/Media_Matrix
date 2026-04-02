package com.example.media_matrix.ui.article;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.databinding.ActivityArticleDetailBinding;
import com.example.media_matrix.domain.model.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDetailActivity extends AppCompatActivity {
    private ActivityArticleDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        // Load from intent extras first
        String uuid = getIntent().getStringExtra("article_uuid");
        String title = getIntent().getStringExtra("article_title");
        String imageUrl = getIntent().getStringExtra("article_image");
        String source = getIntent().getStringExtra("article_source");
        String summary = getIntent().getStringExtra("article_summary");
        String time = getIntent().getStringExtra("article_time");

        binding.articleTitle.setText(title != null ? title : "");
        binding.articleSource.setText(source != null ? source : "");
        binding.articleTime.setText(time != null ? time : "");
        binding.articleContent.setText(summary != null ? summary : "Loading...");
        binding.collapsingToolbar.setTitle(title != null ? title : "");

        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).placeholder(R.color.surface_light).into(binding.articleHeroImage);
        }

        // Fetch full article
        if (uuid != null) {
            ApiClient.getInstance().getApiService().getArticle(uuid).enqueue(new Callback<Article>() {
                @Override
                public void onResponse(Call<Article> call, Response<Article> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Article article = response.body();
                        binding.articleTitle.setText(article.getTitle());
                        binding.articleSource.setText(article.getSourceName());
                        binding.articleContent.setText(article.getContent() != null ? article.getContent() : article.getSummary());
                        binding.articleTime.setText(article.getTimeAgo());
                    }
                }
                @Override
                public void onFailure(Call<Article> call, Throwable t) {}
            });
        }
    }
}
