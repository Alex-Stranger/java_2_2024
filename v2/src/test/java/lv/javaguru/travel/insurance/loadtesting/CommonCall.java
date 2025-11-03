package lv.javaguru.travel.insurance.loadtesting;

import com.google.common.base.Stopwatch;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

abstract class CommonCall implements Runnable {

    protected final RestTemplate restTemplate = new RestTemplate();

    protected void processVersion(String version) {
        try {
            Path versionPath = Paths.get(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("rest/" + version)
            ).toURI());

            Files.list(versionPath)
                    .filter(Files::isDirectory)
                    .forEach(apiGroupDir -> {
                        try {
                            Files.list(apiGroupDir)
                                    .filter(Files::isDirectory)
                                    .forEach(testCaseDir -> {
                                        String relativePath = "rest/" + version + "/" +
                                                apiGroupDir.getFileName() + "/" +
                                                testCaseDir.getFileName();
                                        String endpoint = "/" + version + "/";

                                        try {
                                            callAndCompare(
                                                    endpoint,
                                                    relativePath + "/request.json",
                                                    relativePath + "/response.json"
                                            );
                                        } catch (Exception e) {
                                            System.err.println("Error while starting case: " + relativePath);
                                            e.printStackTrace();
                                        }
                                    });
                        } catch (Exception e) {
                            System.err.println("Error processing group: " + apiGroupDir);
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            System.err.println("Error processing version " + version);
            e.printStackTrace();
        }
    }

    protected void callAndCompare(String endpoint, String requestPath, String responsePath) throws Exception {
        String requestJson = readFileFromResources(requestPath);
        String expectedResponse = readFileFromResources(responsePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        String url = "http://localhost:8080/insurance/travel/api" + endpoint;
        Stopwatch stopwatch = Stopwatch.createStarted();
        String actualResponse = restTemplate.postForObject(url, entity, String.class);
        stopwatch.stop();
        long duration = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("Request to " + endpoint + " processed in ms: " + duration);

        if (normalizeJson(actualResponse).equals(normalizeJson(expectedResponse))) {
            System.out.println("Successful for endpoint " + endpoint + " with case: " + requestPath);
        } else {
            System.err.println("Error for endpoint " + endpoint + " with case: " + requestPath);
            System.err.println("Expected:\n" + expectedResponse);
            System.err.println("Received:\n" + actualResponse);
        }
    }

    private String readFileFromResources(String path) throws Exception {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + path);
        }
        return new String(Files.readAllBytes(Paths.get(resource.toURI())));
    }

    private String normalizeJson(String json) {
        return json.replaceAll("\"uuid\"\\s*:\\s*\"[^\"]+\",?", "")
                .replaceAll("\\s+", "");
    }
}
