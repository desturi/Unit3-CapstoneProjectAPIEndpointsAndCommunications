package com.uber.pricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * LESSON NOTE: Pricing Service Application
 * - This microservice is responsible for all pricing calculations
 * - Demonstrates separation of concerns in microservices architecture
 * - Business logic for pricing is isolated from booking management
 * - Can be scaled independently based on pricing calculation demands
 */
@SpringBootApplication
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);

        System.out.println("""
            
            ╔════════════════════════════════════════════════════════════════╗
            ║                      PRICING SERVICE STARTED                   ║
            ║                                                                ║
            ║  📝 Handles price calculation                                  ║
            ║  🔗 Communicates with Booking service                          ║
            ║                                                                ║
            ║                                                                ║
            ║  Available at: http://localhost:8082/api/pricing               ║
            ╚════════════════════════════════════════════════════════════════╝
            """);
    }
}