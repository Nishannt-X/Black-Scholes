package com.capitalmarkets.pricing.service;

import java.util.*;

public class BlackScholesPricingService {

    public static double callPrice(double S, double K, double T, double r, double sigma) {
        double d1 = (Math.log(S / K) + (r + sigma * sigma / 2) * T) / (sigma * Math.sqrt(T));
        double d2 = d1 - sigma * Math.sqrt(T);
        return S * cumulativeStandardNormalDistribution(d1) - K * Math.exp(-r * T) * cumulativeStandardNormalDistribution(d2);
    }

    public static double putPrice(double S, double K, double T, double r, double sigma) {
        double d1 = (Math.log(S / K) + (r + sigma * sigma / 2) * T) / (sigma * Math.sqrt(T));
        double d2 = d1 - sigma * Math.sqrt(T);
        return K * Math.exp(-r * T) * cumulativeStandardNormalDistribution(-d2) - S * cumulativeStandardNormalDistribution(-d1);
    }

    public static Map<String, Double> calculateGreeks(double S, double K, double T, double r, double sigma) {
        double d1 = (Math.log(S / K) + (r + sigma * sigma / 2) * T) / (sigma * Math.sqrt(T));
        double d2 = d1 - sigma * Math.sqrt(T);
        double deltaCall = cumulativeStandardNormalDistribution(d1);
        double deltaPut = deltaCall - 1;
        double gamma = cumulativeStandardNormalDistribution(d1) / (S * sigma * Math.sqrt(T));
        double vega = S * Math.sqrt(T) * cumulativeStandardNormalDistribution(d1);
        double thetaCall = - (S * sigma * cumulativeStandardNormalDistribution(d1)) / (2 * Math.sqrt(T)) - r * K * Math.exp(-r * T) * cumulativeStandardNormalDistribution(d2);
        double thetaPut = - (S * sigma * cumulativeStandardNormalDistribution(d1)) / (2 * Math.sqrt(T)) + r * K * Math.exp(-r * T) * cumulativeStandardNormalDistribution(-d2);
        double rhoCall = K * T * Math.exp(-r * T) * cumulativeStandardNormalDistribution(d2);
        double rhoPut = -K * T * Math.exp(-r * T) * cumulativeStandardNormalDistribution(-d2);
        Map<String, Double> greeks = new HashMap<>();
        greeks.put("deltaCall", deltaCall);
        greeks.put("deltaPut", deltaPut);
        greeks.put("gamma", gamma);
        greeks.put("vega", vega);
        greeks.put("thetaCall", thetaCall);
        greeks.put("thetaPut", thetaPut);
        greeks.put("rhoCall", rhoCall);
        greeks.put("rhoPut", rhoPut);
        return greeks;
    }

    public static double impliedVolatility(double marketPrice, double S, double K, double T, double r) {
        double sigma = 0.2; // Initial guess
        double tolerance = 0.0001;
        double price;
        do {
            price = callPrice(S, K, T, r, sigma);
            if (price < marketPrice) {
                sigma += tolerance;
            } else {
                sigma -= tolerance;
            }
        } while (Math.abs(price - marketPrice) > tolerance);
        return sigma;
    }

    private static double cumulativeStandardNormalDistribution(double x) {
        return 0.5 * (1 + Math.erf(x / Math.sqrt(2)));
    }

}