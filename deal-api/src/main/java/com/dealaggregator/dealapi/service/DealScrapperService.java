package com.dealaggregator.dealapi.service;
import org.jsoup.nodes.Document;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class DealScrapperService {
    
    public void testJsoup() {
        System.out.println("Jsoup chilling");
    }

    public void scrapeReddit() {
        try {
            Document doc = Jsoup.connect("https://old.reddit.com/r/buildapcsales").get();
            System.out.println("Success");
        } catch(Exception e) {
            System.out.print("c");
        }
    }
}
