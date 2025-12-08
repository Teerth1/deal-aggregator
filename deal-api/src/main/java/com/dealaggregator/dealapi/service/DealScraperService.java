package com.dealaggregator.dealapi.service;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.dealaggregator.dealapi.repository.DealRepository;


import com.dealaggregator.dealapi.entity.Deal;



@Service
public class DealScraperService {
    
    private final DealRepository dealRepo;


    public DealScraperService(DealRepository dealRepo) {
        this.dealRepo = dealRepo;
    }
    public void testJsoup() {
        System.out.println("Jsoup chilling");
    }

    private String extractVendor(String url) {
        if (url.contains("amazon.com")) {
            return "Amazon";
        } else if (url.contains("newegg.com")) {
            return "Newegg";
        } else if (url.contains("bestbuy.com")) {
            return "Best Buy";
        } else if (url.contains("microcenter.com")) {
            return "Micro Center";
        } else if (url.contains("walmart.com")) {
            return "Walmart";
        } else if (url.contains("target.com")) {
            return "Target";
        } else if (url.contains("bhphotovideo.com")) {
            return "B&H Photo";
        } else {
            return "Other";
        }
    }

    private BigDecimal extractPrice(String title) {
        try {
            Pattern pattern = Pattern.compile("\\$([0-9,]+\\.?[0-9]*)");
            Matcher matcher = pattern.matcher(title);
            if (matcher.find()) {
                String priceStr = matcher.group(1).replace(",", "");
                return new BigDecimal(priceStr);
            }
        } catch (Exception e) {
            System.out.println("Could not extract price from: " + title);
        }
        return BigDecimal.ZERO;
    }

    private String extractCategory(String title) {
        if (title.startsWith("[")) {
            int endBracket = title.indexOf("]");
            if (endBracket > 0) {  // ← Safety check
                return title.substring(1, endBracket);
            }
        }
        return "Other";
    }

    @Scheduled(fixedRate = 60000) 
    public void scrapeReddit() {
        try {
            Document doc = Jsoup.connect("https://old.reddit.com/r/buildapcsales").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)").get();
            
            Elements posts = doc.select("div.thing");
            System.out.println("Found " + posts.size() + " posts");
            for (Element post : posts) {
                String title = post.select("a.title").text();
                String link = post.select("a.title").attr("href");
                String score = post.attr("data-score");
                
                Deal deal = new Deal();
                deal.setTitle(title);
                deal.setDealUrl(link);
                deal.setVendor(extractVendor(link));
                deal.setCategory(extractCategory(title));
                deal.setDealType("Online");
                deal.setPrice(extractPrice(title));
                dealRepo.save(deal);

                System.out.println("Saved: " + title + " from " + deal.getVendor() + " (" + deal.getCategory() + ")");

            }   
                
        } catch(Exception e) {
            System.out.print("Error: " + e.getMessage());
        }
    }
}
