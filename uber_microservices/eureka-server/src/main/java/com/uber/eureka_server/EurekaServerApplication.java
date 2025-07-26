package com.uber.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * LESSON NOTE: Service Discovery with Eureka Server
 *
 * This is the heart of our microservices architecture. Eureka Server acts as a service registry
 * where all microservices register themselves so they can discover and communicate with each other.
 *
 * Key Concepts:
 * 1. SERVICE REGISTRY: A centralized database of services and their locations
 * 2. SERVICE DISCOVERY: The ability for services to find and communicate with each other
 * 3. HEARTBEAT: Services send periodic signals to prove they're alive
 * 4. LOAD BALANCING: Distributes requests across multiple instances of the same service
 *
 * Why do we need Service Discovery?
 * - In microservices, services can start/stop dynamically
 * - IP addresses and ports can change
 * - Services need to scale up/down based on demand
 * - Manual configuration becomes impossible with many services
 *
 * Eureka Architecture:
 * - Eureka Server: The registry where services register
 * - Eureka Client: The services that register with the server
 * - Dashboard: Web UI to see all registered services
 *
 * Alternative Solutions:
 * - Consul (HashiCorp)
 * - etcd (Kubernetes)
 * - Zookeeper (Apache)
 * - AWS Cloud Map
 */
@SpringBootApplication
@EnableEurekaServer  // This annotation makes this application a Eureka Server
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);

		System.out.println("""
            
            ╔════════════════════════════════════════════════════════════════╗
            ║                    EUREKA SERVER STARTED                       ║
            ║                                                                ║
            ║  🔍 Service Discovery Dashboard: http://localhost:8761         ║
            ║  📊 All microservices will register here                       ║
            ║  🔄 Provides load balancing and health checking                ║
            ║                                                                ║
            ║  LEARNING TIP: Open the dashboard to see registered services   ║
            ╚════════════════════════════════════════════════════════════════╝
            """);
	}
}