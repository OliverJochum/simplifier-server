package com.oljochum.simplifier_server.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class FileParseUtils {
    public Map<String, String> loadAbbreviations(Path path) throws IOException {
    return Files.lines(path)
        .skip(1)
        .map(line -> line.split(",", 2))
        .collect(Collectors.toMap(
            parts -> parts[0],
            parts -> parts[1]
        ));
    }

    public Map<String, String> loadGermanAbbreviations() {
        try {
            return loadAbbreviations(Path.of("/Users/U462343/bachelors_thesis/simplifier-server/src/main/resources/constants/abbreviations.csv"));
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of();
        }
    }
}
