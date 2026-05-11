package com.example.media_matrix.data.local;

import com.example.media_matrix.domain.model.Newspaper;

import java.util.ArrayList;
import java.util.List;

/**
 * Curated local list of popular newspapers with their actual website URLs
 * and high-quality cover/logo images. No API key needed.
 */
public class NewspaperDataSource {

    public static List<Newspaper> getNewspapers() {
        List<Newspaper> list = new ArrayList<>();

        // ── International ────────────────────────────────────────────────
        list.add(build(1, "The New York Times", "NYT",
                "https://www.nytimes.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/The_New_York_Times_logo.png/640px-The_New_York_Times_logo.png",
                "2025-05-09", "International"));

        list.add(build(2, "The Guardian", "Guardian",
                "https://www.theguardian.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/The_Guardian_2018.svg/640px-The_Guardian_2018.svg.png",
                "2025-05-09", "International"));

        list.add(build(3, "BBC News", "BBC",
                "https://www.bbc.com/news",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/BBC_News_2019.svg/640px-BBC_News_2019.svg.png",
                "2025-05-09", "International"));

        list.add(build(4, "Reuters", "Reuters",
                "https://www.reuters.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/Reuters_logo.svg/640px-Reuters_logo.svg.png",
                "2025-05-09", "International"));

        list.add(build(5, "Al Jazeera", "Al Jazeera",
                "https://www.aljazeera.com",
                "https://upload.wikimedia.org/wikipedia/en/thumb/f/f2/Al_Jazeera_English.svg/640px-Al_Jazeera_English.svg.png",
                "2025-05-09", "International"));

        list.add(build(6, "Washington Post", "WashPost",
                "https://www.washingtonpost.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/The_Logo_of_The_Washington_Post_Newspaper.svg/640px-The_Logo_of_The_Washington_Post_Newspaper.svg.png",
                "2025-05-09", "International"));

        // ── Indian Newspapers ────────────────────────────────────────────
        list.add(build(7, "The Hindu", "Hindu",
                "https://www.thehindu.com",
                "https://upload.wikimedia.org/wikipedia/en/thumb/4/42/The_Hindu_Logo.svg/640px-The_Hindu_Logo.svg.png",
                "2025-05-09", "Indian"));

        list.add(build(8, "Times of India", "TOI",
                "https://timesofindia.indiatimes.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Times_Of_India_Logo.svg/640px-Times_Of_India_Logo.svg.png",
                "2025-05-09", "Indian"));

        list.add(build(9, "Hindustan Times", "HT",
                "https://www.hindustantimes.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Hindustan_Times_logo.svg/640px-Hindustan_Times_logo.svg.png",
                "2025-05-09", "Indian"));

        list.add(build(10, "NDTV", "NDTV",
                "https://www.ndtv.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/NDTV_24x7.svg/640px-NDTV_24x7.svg.png",
                "2025-05-09", "Indian"));

        list.add(build(11, "Indian Express", "IE",
                "https://indianexpress.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/57/The_Indian_Express_Logo.svg/640px-The_Indian_Express_Logo.svg.png",
                "2025-05-09", "Indian"));

        // ── Business ─────────────────────────────────────────────────────
        list.add(build(12, "Financial Times", "FT",
                "https://www.ft.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Financial_Times_corporate_logo.svg/640px-Financial_Times_corporate_logo.svg.png",
                "2025-05-09", "Business"));

        list.add(build(13, "Wall Street Journal", "WSJ",
                "https://www.wsj.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/WSJ_Logo.svg/640px-WSJ_Logo.svg.png",
                "2025-05-09", "Business"));

        list.add(build(14, "Bloomberg", "Bloomberg",
                "https://www.bloomberg.com",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/New_Bloomberg_Logo.svg/640px-New_Bloomberg_Logo.svg.png",
                "2025-05-09", "Business"));

        return list;
    }

    private static Newspaper build(int id, String name, String shortName,
                                   String websiteUrl, String logoUrl,
                                   String date, String category) {
        Newspaper n = new Newspaper();
        n.setId(id);
        n.setSourceName(name);
        n.setSourceShortName(shortName);
        n.setPdfUrl(websiteUrl);          // reuse pdfUrl field as websiteUrl
        n.setCoverImageUrl(logoUrl);
        n.setEditionDate(date);
        n.setCategory(category);
        return n;
    }
}
