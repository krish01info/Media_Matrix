package com.example.media_matrix.data.remote;

import com.example.media_matrix.data.remote.response.NewsApiArticle;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Source;
import com.example.media_matrix.domain.model.Reporter;

import java.util.ArrayList;
import java.util.List;

public class NewsApiMapper {

    public static List<Article> mapArticles(
            List<NewsApiArticle> newsArticles,
            boolean isFeatured,
            boolean isBreaking,
            boolean isMorningBrief,
            boolean isDeveloping) {

        List<Article> result = new ArrayList<>();
        if (newsArticles == null) return result;

        for (int i = 0; i < newsArticles.size(); i++) {
            NewsApiArticle na = newsArticles.get(i);
            if (na == null || na.getTitle() == null || na.getTitle().equals("[Removed]")) {
                continue;
            }
            Article article = mapSingle(na);
            article.setFeatured(isFeatured);
            article.setBreaking(isBreaking);
            article.setMorningBrief(isMorningBrief);
            article.setDeveloping(isDeveloping);
            
            // Set a default category name if missing so badges aren't empty
            if (article.getCategoryName() == null || article.getCategoryName().isEmpty()) {
                article.setTitle(article.getTitle()); // Ensure title is preserved
                // We'll set a generic category based on flags
                String category = "General";
                if (isFeatured) category = "Featured";
                else if (isBreaking) category = "Breaking";
                else if (isDeveloping) category = "Developing";
                
                article.setCategory(new com.example.media_matrix.domain.model.Category());
                article.getCategory().setName(category);
            }

            article.setInteractionCount((long) (10000 - i * 100));
            result.add(article);
        }
        return result;
    }

    public static List<Article> mapArticles(List<NewsApiArticle> newsArticles) {
        return mapArticles(newsArticles, false, false, false, false);
    }

    public static Article mapSingle(NewsApiArticle na) {
        Article article = new Article();
        String url = na.getUrl() != null ? na.getUrl() : "";
        article.setUuid(url);
        article.setTitle(na.getTitle() != null ? na.getTitle() : "");
        article.setSummary(na.getDescription() != null ? na.getDescription() : "");

        String content = na.getContent();
        if (content == null || content.isEmpty()) {
            content = na.getDescription() != null ? na.getDescription() : "";
        }
        if (content != null && content.contains("[+")) {
            content = content.substring(0, content.lastIndexOf("[+")).trim();
        }
        article.setContent(content);

        article.setImageUrl(na.getUrlToImage());
        article.setThumbnailUrl(na.getUrlToImage());
        article.setPublishedAt(na.getPublishedAt());
        article.setVerified(true);

        Source source = new Source();
        source.setName(na.getSourceName());
        article.setSource(source);

        if (na.getAuthor() != null && !na.getAuthor().isEmpty()) {
            Reporter reporter = new Reporter();
            reporter.setName(na.getAuthor());
            article.setReporter(reporter);
        }

        return article;
    }
}
