//package com.friendsbook.frontend.util;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import feign.RetryableException;
//import feign.Retryer;
//
//@Configuration
//public class FeignClientConfig {
//
//    @Bean
//    public Retryer retryer() {
//        return new Custom();
//    }
//
//}
//
//class Custom implements Retryer {
//
//    private final int maxAttempts;
//    private final long backoff;
//    int attempt;
//
//    public Custom() {
//        this(2000, 1);
//    }
//
//    public Custom(long backoff, int maxAttempts) {
//        this.backoff = backoff;
//        this.maxAttempts = maxAttempts;
//        this.attempt = 1;
//    }
//
//    public void continueOrPropagate(RetryableException e) {
//        if (attempt++ >= maxAttempts) {
//            throw e;
//        }
//
//        try {
//            Thread.sleep(backoff);
//        } catch (InterruptedException ignored) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    @Override
//    public Retryer clone() {
//        return new Custom(backoff, maxAttempts);
//    }
//}
