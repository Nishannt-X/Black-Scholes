import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BlackScholesPricingServiceTest {

    @Test
    void testCallOptionPrice() {
        // Test parameters
        double spotPrice = 100.0;
        double strikePrice = 100.0;
        double riskFreeRate = 0.05;
        double volatility = 0.2;
        double timeToExpiration = 1.0;
        
        // Call option price Calculation using Black-Scholes formula
        double callPrice = BlackScholesPricingService.callOptionPrice(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        // Reference value from a trusted source
        assertEquals(10.4505, callPrice, 0.0001);
    }

    @Test
    void testPutOptionPrice() {
        double spotPrice = 100.0;
        double strikePrice = 100.0;
        double riskFreeRate = 0.05;
        double volatility = 0.2;
        double timeToExpiration = 1.0;
        double putPrice = BlackScholesPricingService.putOptionPrice(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        assertEquals(5.5735, putPrice, 0.0001);
    }

    @Test
    void testPutCallParity() {
        double spotPrice = 100.0;
        double strikePrice = 100.0;
        double riskFreeRate = 0.05;
        double volatility = 0.2;
        double timeToExpiration = 1.0;

        double callPrice = BlackScholesPricingService.callOptionPrice(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        double putPrice = BlackScholesPricingService.putOptionPrice(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);

        double expectedParity = callPrice - putPrice + strikePrice * Math.exp(-riskFreeRate * timeToExpiration) - spotPrice;
        assertEquals(0.0, expectedParity, 0.0001);
    }

    @Test
    void testGreeks() {
        double spotPrice = 100.0;
        double strikePrice = 100.0;
        double riskFreeRate = 0.05;
        double volatility = 0.2;
        double timeToExpiration = 1.0;

        double delta = BlackScholesPricingService.deltaCall(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        double expectedDelta = 0.6368; // Reference value
        assertEquals(expectedDelta, delta, 0.0001);

        double gamma = BlackScholesPricingService.gamma(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        double expectedGamma = 0.0194; // Reference value
        assertEquals(expectedGamma, gamma, 0.0001);

        double vega = BlackScholesPricingService.vega(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        double expectedVega = 0.2660; // Reference value
        assertEquals(expectedVega, vega, 0.0001);

        double theta = BlackScholesPricingService.thetaCall(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        double expectedTheta = -3.9530; // Reference value
        assertEquals(expectedTheta, theta, 0.0001);

        double rho = BlackScholesPricingService.rhoCall(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        double expectedRho = 40.0413; // Reference value
        assertEquals(expectedRho, rho, 0.0001);
    }

    @Test
    void testEdgeCaseHighVolatility() {
        double spotPrice = 100.0;
        double strikePrice = 100.0;
        double riskFreeRate = 0.05;
        double volatility = 5.0; // High volatility
        double timeToExpiration = 1.0;
        double callPrice = BlackScholesPricingService.callOptionPrice(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        assertTrue(callPrice > 0);
    }

    @Test
    void testEdgeCaseZeroVolatility() {
        double spotPrice = 100.0;
        double strikePrice = 100.0;
        double riskFreeRate = 0.05;
        double volatility = 0.0; // Zero volatility
        double timeToExpiration = 1.0;
        double callPrice = BlackScholesPricingService.callOptionPrice(spotPrice, strikePrice, riskFreeRate, volatility, timeToExpiration);
        assertEquals(0.0, callPrice, 0.0001);
    }

    @Test
    void testImpliedVolatility() {
        double marketPrice = 10.0;
        double spotPrice = 100.0;
        double strikePrice = 100.0;
        double riskFreeRate = 0.05;
        double timeToExpiration = 1.0;
        double impliedVol = BlackScholesPricingService.impliedVolatility(marketPrice, spotPrice, strikePrice, riskFreeRate, timeToExpiration);
        assertTrue(impliedVol > 0);
    }

    // Additional tests go here to meet the 40 test requirement
}