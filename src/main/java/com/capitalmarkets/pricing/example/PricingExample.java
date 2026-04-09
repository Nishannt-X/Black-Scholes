package com.capitalmarkets.pricing.example;

import com.capitalmarkets.pricing.PricingService;
import com.capitalmarkets.pricing.models.Options;

public class PricingExample {
    public static void main(String[] args) {
        // Create a PricingService instance
        PricingService pricingService = new PricingService();
        
        // Example 1: Call the pricing function for a call option
        Options callOption = new Options("Call", 100.0, 100.0, 1.0, 0.05);
        double callOptionPrice = pricingService.priceOption(callOption);
        System.out.println("Call Option Price: " + callOptionPrice);
        
        // Example 2: Call the pricing function for a put option
        Options putOption = new Options("Put", 100.0, 100.0, 1.0, 0.05);
        double putOptionPrice = pricingService.priceOption(putOption);
        System.out.println("Put Option Price: " + putOptionPrice);

        // Example 3: Pricing with volatility
        Options optionWithVolatility = new Options("Call", 100.0, 100.0, 1.0, 0.05);
        double volatilityPrice = pricingService.priceWithVolatility(optionWithVolatility, 0.2);
        System.out.println("Call Option Price with Volatility: " + volatilityPrice);
    }
}