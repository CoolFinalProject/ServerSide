package server.services.servicesImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import server.DTO.ArticleDto.ArticleDto;
import server.DTO.ArticleDto.ArticleMetadataDto;
import server.services.ScrapeService;

@Service
public class ScrapeServiceImpl implements  ScrapeService{


    @Override
    public List<ArticleDto> scrapeArticles(List<ArticleMetadataDto> sources) {
        List<ArticleDto> scrapedArticles = new ArrayList<>();

        for (ArticleMetadataDto metadata : sources) {
            String targetUrl = metadata.getSource().getWebSource(); 
            
            if (targetUrl == null || targetUrl.trim().isEmpty()) {
                continue; 
            }

            try {
                System.out.println("URL to scrape: " + targetUrl);
                System.out.println("Java working dir: " + System.getProperty("user.dir"));
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "python", 
                        "src/main/python/scrapingArticle/scrapeNews.py", 
                        targetUrl
                );
                
                processBuilder.redirectErrorStream(true); 
                Process process = processBuilder.start();

                // Capture the raw text output
                StringBuilder rawText = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        rawText.append(line).append('\n'); // Preserve line breaks
                    }
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    ArticleDto dto = new ArticleDto(metadata, rawText.toString().trim() ,null);
                                     
                    scrapedArticles.add(dto);
                } else {
                    System.err.println("Scraping failed for URL: " + targetUrl);
                    System.err.println("Script output/error: " + rawText.toString());
                }

            } catch (Exception e) {
                System.err.println("Exception occurred while scraping: " + targetUrl);
                e.printStackTrace();
            }
        }

        return scrapedArticles;
    }

}
