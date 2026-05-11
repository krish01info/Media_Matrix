package com.example.media_matrix.data.local;

import com.example.media_matrix.domain.model.LiveChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a curated, hard-coded list of live news TV channels.
 * Uses public YouTube live stream IDs or Channel IDs for reliability.
 */
public class LiveChannelDataSource {

    public static List<LiveChannel> getChannels() {
        List<LiveChannel> channels = new ArrayList<>();

        // ── International (Using Channel IDs for better reliability) ──
        channels.add(new LiveChannel(
                "bbc_world",
                "BBC World News",
                "United Kingdom",
                "English",
                "International",
                "UC16niRr50-MSBwiO3YDb3RA",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/BBC_World_News_2022_%28Alt%29.svg/320px-BBC_World_News_2022_%28Alt%29.svg.png",
                "Live coverage from BBC World News — global news 24/7",
                true
        ));

        channels.add(new LiveChannel(
                "al_jazeera",
                "Al Jazeera English",
                "Qatar",
                "English",
                "International",
                "UCNye-wNBqNL5ZzHSJj3l8Bg",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/en/thumb/f/f2/Al_Jazeera_English.svg/320px-Al_Jazeera_English.svg.png",
                "Award-winning news from the Middle East and beyond",
                true
        ));

        channels.add(new LiveChannel(
                "dw_news",
                "DW News",
                "Germany",
                "English",
                "International",
                "UCuZ4Dn06qY3S_2s_XmU87jQ",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/DW_Logo_%282012%29.svg/320px-DW_Logo_%282012%29.svg.png",
                "Germany's international broadcaster — news, analysis & more",
                true
        ));

        channels.add(new LiveChannel(
                "france24",
                "France 24 English",
                "France",
                "English",
                "International",
                "UC80p7eYvW95mX80S6A1ZtEw",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/France_24_logo.svg/320px-France_24_logo.svg.png",
                "Live from Paris — news in English from France 24",
                true
        ));

        channels.add(new LiveChannel(
                "abc_news",
                "ABC News (US)",
                "United States",
                "English",
                "International",
                "UCBi2mrWuNuyYy4gbM6fU18Q",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/ABC_News_logo_2021.svg/320px-ABC_News_logo_2021.svg.png",
                "Breaking news and videos from ABC News",
                true
        ));

        // ── Indian Channels ───────────────────────────────────────────
        channels.add(new LiveChannel(
                "ndtv",
                "NDTV 24x7",
                "India",
                "English",
                "Indian",
                "UCZ52XbfuP2vupNOGS6I9MvA",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/NDTV_24x7.svg/320px-NDTV_24x7.svg.png",
                "India's most trusted news network",
                true
        ));

        channels.add(new LiveChannel(
                "republic_world",
                "Republic World",
                "India",
                "English",
                "Indian",
                "UCGvvGz-G6nsmF699S_x8rYQ",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/en/thumb/0/07/Republic_TV_logo.svg/320px-Republic_TV_logo.svg.png",
                "Breaking news & prime-time debates from India",
                true
        ));

        channels.add(new LiveChannel(
                "aaj_tak",
                "Aaj Tak",
                "India",
                "Hindi",
                "Indian",
                "UCt4t-jeY85JegMlZ-E5UWtA",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/en/thumb/a/a4/Aaj_Tak_logo.svg/320px-Aaj_Tak_logo.svg.png",
                "India's No.1 Hindi news channel",
                true
        ));

        channels.add(new LiveChannel(
                "india_today",
                "India Today",
                "India",
                "English",
                "Indian",
                "UC6SByOIn47uXjI406Q6y2uA",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/en/thumb/3/3c/India_Today_TV_logo.svg/320px-India_Today_TV_logo.svg.png",
                "Latest news from India Today Group",
                true
        ));

        // ── Business ─────────────────────────────────────────────────
        channels.add(new LiveChannel(
                "bloomberg",
                "Bloomberg TV",
                "United States",
                "English",
                "Business",
                "UCoVV86G9-4A7rVfT7uE76-Q",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/New_Bloomberg_Logo.svg/320px-New_Bloomberg_Logo.svg.png",
                "Markets, finance, and business news from Bloomberg",
                true
        ));

        // ── Regional / UK ────────────────────────────────────────────
        channels.add(new LiveChannel(
                "sky_news",
                "Sky News",
                "United Kingdom",
                "English",
                "International",
                "UCo70S06vC_W1_zL9f_uO9Ew",   // Channel ID
                "https://upload.wikimedia.org/wikipedia/en/thumb/5/57/Sky_News_logo_2016.svg/320px-Sky_News_logo_2016.svg.png",
                "24-hour breaking news from Sky News",
                true
        ));

        return channels;
    }

    /** Returns channels filtered by category. */
    public static List<LiveChannel> getByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("All")) {
            return getChannels();
        }
        List<LiveChannel> result = new ArrayList<>();
        for (LiveChannel ch : getChannels()) {
            if (ch.getCategory().equalsIgnoreCase(category)) {
                result.add(ch);
            }
        }
        return result;
    }
}
