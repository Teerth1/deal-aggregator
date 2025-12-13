package com.dealaggregator.dealapi.service;

import org.springframework.stereotype.Service;

/**
 * Service class for Black-Scholes option pricing model calculations.
 *
 * Implements the Black-Scholes formula to calculate theoretical prices
 * for European call and put options based on current market conditions.
 */
@Service
public class BlackScholesService {

    /**
     * Calculates the theoretical price of an option using the Black-Scholes model.
     *
     * Formula for Call: C = S * N(d1) - K * e^(-rt) * N(d2)
     * Formula for Put:  P = K * e^(-rt) * N(-d2) - S * N(-d1)
     *
     * @param s Current stock price
     * @param k Strike price of the option
     * @param t Time to expiration in years (e.g., 30 days = 30/365.0)
     * @param v Volatility of the underlying stock (annual standard deviation)
     * @param r Risk-free interest rate (annual)
     * @param optionType Type of option - "call" or "put"
     * @return Theoretical option price rounded to 2 decimal places
     */
    public double blackScholes(double s, double k, double t, double v, double r, String optionType) {
        // Calculate d1: (ln(S/K) + (r + σ²/2)t) / (σ√t)
        double d1 = (Math.log(s / k) + (r + 0.5 * Math.pow(v, 2)) * t) / (v * Math.sqrt(t));
        // Calculate d2: d1 - σ√t
        double d2 = d1 - v * Math.sqrt(t);

        double price;
        if ("call".equalsIgnoreCase(optionType)) {
            // Call option formula: S * N(d1) - K * e^(-rt) * N(d2)
            price = s * cumulativeDistribution(d1) - k * Math.exp(-r * t) * cumulativeDistribution(d2);
        } else {
            // Put option formula: K * e^(-rt) * N(-d2) - S * N(-d1)
            price = k * Math.exp(-r * t) * cumulativeDistribution(-d2) - s * cumulativeDistribution(-d1);
        }

        // Round to 2 decimal places (cents)
        return Math.round(price * 100.0) / 100.0;
    }

    /**
     * Calculates the cumulative distribution function (CDF) of the standard normal distribution.
     *
     * Uses the error function to compute N(x), the probability that a standard
     * normal random variable is less than or equal to x.
     *
     * @param x Value to evaluate CDF at
     * @return Probability value between 0 and 1
     */
    private double cumulativeDistribution(double x) {
        return 0.5 * (1.0 + erf(x / Math.sqrt(2.0)));
    }

    /**
     * Approximates the error function using Horner's method.
     *
     * The error function is used in probability, statistics, and partial
     * differential equations. This implementation uses a polynomial approximation
     * for computational efficiency.
     *
     * @param z Input value
     * @return Error function value erf(z)
     */
    private double erf(double z) {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // Horner's method polynomial approximation for error function
        double ans = 1 - t * Math.exp(-z * z - 1.26551223 +
                t * (1.00002368 +
                t * (0.37409196 +
                t * (0.09678418 +
                t * (-0.18628806 +
                t * (0.27886807 +
                t * (-1.13520398 +
                t * (1.48851587 +
                t * (-0.82215223 +
                t * 0.17087277)))))))));

        // Return positive or negative value based on input sign
        if (z >= 0) return ans;
        else return -ans;
    }

}
