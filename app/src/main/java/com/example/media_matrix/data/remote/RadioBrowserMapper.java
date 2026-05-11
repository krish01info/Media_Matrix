package com.example.media_matrix.data.remote;

import com.example.media_matrix.data.remote.response.RadioBrowserStation;
import com.example.media_matrix.domain.model.RadioStream;
import com.example.media_matrix.domain.model.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts RadioBrowserStation objects → our existing RadioStream domain model.
 * Keeps all radio UI code (adapters, fragments, ViewModels) unchanged.
 */
public class RadioBrowserMapper {

    public static List<RadioStream> mapStations(List<RadioBrowserStation> stations) {
        List<RadioStream> result = new ArrayList<>();
        if (stations == null) return result;

        int id = 1;
        for (RadioBrowserStation station : stations) {
            if (station == null) continue;
            // Skip stations with no stream URL
            if (station.getUrlResolved() == null || station.getUrlResolved().isEmpty()) continue;

            RadioStream stream = new RadioStream();
            stream.setId(id++);
            stream.setTitle(station.getName() != null ? station.getName() : "Unknown Station");
            stream.setDescription(buildDescription(station));
            stream.setStreamUrl(station.getUrlResolved());
            stream.setThumbnailUrl(station.getFavicon());
            stream.setLive(true);
            stream.setHighQuality(station.getBitrate() >= 128);
            stream.setListenerCount(station.getVotes());
            stream.setDisplayOrder(id);

            // Attach source info (country/codec as source name)
            Source source = new Source();
            String sourceName = station.getCountry() != null && !station.getCountry().isEmpty()
                    ? station.getCountry()
                    : "International";
            source.setName(sourceName);
            source.setSlug(sourceName.toLowerCase().replaceAll("\\s+", "-"));
            source.setHasRadio(true);
            stream.setSource(source);

            result.add(stream);
        }
        return result;
    }

    private static String buildDescription(RadioBrowserStation s) {
        StringBuilder sb = new StringBuilder();
        if (s.getCountry() != null && !s.getCountry().isEmpty()) {
            sb.append(s.getCountry());
        }
        if (s.getBitrate() > 0) {
            if (sb.length() > 0) sb.append(" • ");
            sb.append(s.getBitrate()).append(" kbps");
        }
        if (s.getCodec() != null && !s.getCodec().isEmpty()) {
            if (sb.length() > 0) sb.append(" • ");
            sb.append(s.getCodec().toUpperCase());
        }
        return sb.toString();
    }
}
