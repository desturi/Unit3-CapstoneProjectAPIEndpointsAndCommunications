package com.uber.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

/**
 * LESSON NOTE: Booking Service - Core Business Logic Service
 *
 * This service handles all booking-related operations in our uber system.
 * In an extremely microservices architecture, each service has a very specific responsibility.
 *
 * Booking Service Responsibilities:
 * - Create new bookings
 * - Validate booking data
 * - Calculate booking totals (via Pricing Service)
 * - Track booking status
 *
 * Key Annotations:
 * - @SpringBootApplication: Main Spring Boot application class
 * - @EnableEurekaClient: Registers this service with Eureka Server for discovery
 * - @EnableFeignClients: Enables declarative REST clients for inter-service communication
 *
 * Inter-Service Communication:
 * This service communicates with:
 * - Pricing Service (to calculate costs)
 *
 * Data Storage:
 * For this educational example, we use in-memory storage to focus on
 * microservices patterns rather than database complexity.
 */
@SpringBootApplication
public class BookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);

        System.out.println("""

            ╔════════════════════════════════════════════════════════════════╗
            ║                      BOOKING SERVICE STARTED                   ║
            ║                                                                ║
            ║  📝 Handles booking creation and management                    ║
            ║  🔗 Communicates with Pricing service                          ║
            ║  💾 Uses in-memory storage for simplicity                      ║
            ║                                                                ║
            ║  Available at: http://localhost:8081/api/bookings              ║
            ╚════════════════════════════════════════════════════════════════╝
            """);
    }

    /**
     * RestTemplate bean with @LoadBalanced annotation.
     *
     * @LoadBalanced enables this RestTemplate to resolve service names (e.g., "http://pricing-service")
     * using Eureka service discovery. This allows dynamic resolution of service instances at runtime,
     * so you do not need to hardcode hostnames or ports. Instead, you use the logical service name
     * registered with Eureka, and Spring Cloud LoadBalancer will route requests to available instances.
     */
    @Bean
    @LoadBalanced // Enables service discovery - uses service names like "pricing-service" instead of hardcoded URLs
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}