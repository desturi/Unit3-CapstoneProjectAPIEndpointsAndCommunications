package com.uber.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LESSON NOTE: API Gateway - The Single Entry Point
 *
 * An API Gateway is a crucial component in microservices architecture that serves as the
 * single entry point for all client requests. Think of it as a "front door" to your system.
 *
 * Key Responsibilities:
 * 1. ROUTING: Directs requests to the appropriate microservice
 * 2. LOAD BALANCING: Distributes load across multiple service instances
 * 3. AUTHENTICATION & AUTHORIZATION: Centralized security
 * 4. RATE LIMITING: Prevents service overload
 * 5. REQUEST/RESPONSE TRANSFORMATION: Modify requests/responses as needed
 * 6. MONITORING & LOGGING: Centralized observability
 * 7. CIRCUIT BREAKING: Prevents cascade failures
 *
 * Benefits of API Gateway:
 * - Simplified client communication (clients only know gateway URL)
 * - Centralized cross-cutting concerns (auth, logging, etc.)
 * - Service abstraction (clients don't need to know service locations)
 * - Protocol translation (HTTP to different protocols)
 * - API versioning and backward compatibility
 *
 * Spring Cloud Gateway Features:
 * - Built on Spring WebFlux (reactive)
 * - Integrates with Spring Cloud service discovery
 * - Supports predicates and filters
 * - Built-in circuit breaker support
 * - Rate limiting capabilities
 *
 * Alternative Solutions:
 * - Netflix Zuul
 * - Kong
 * - AWS API Gateway
 * - Azure API Management
 * - Google Cloud API Gateway
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);

        System.out.println("""
            
            ╔════════════════════════════════════════════════════════════════╗
            ║                      API GATEWAY STARTED                       ║
            ║                                                                ║
            ║  🚪 Single Entry Point: http://localhost:8080                  ║
            ║  🔀 Routes requests to appropriate microservices               ║
            ║  ⚖️  Load balances across service instances                     ║
            ║  🛡️  Handles cross-cutting concerns (auth, logging)            ║
            ║                                                                ║
            ║  LEARNING TIP: All client requests go through this gateway     ║
            ╚════════════════════════════════════════════════════════════════╝
            """);
    }
}