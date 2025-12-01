# XAP API - GigaSpaces Testing Interface

A comprehensive testing and deployment interface for GigaSpaces XAP (eXtreme Application Platform) using containerized environments.

## Overview

This project provides a Docker-based development and testing environment for GigaSpaces XAP 17.1.2, enabling developers to:

- Deploy and test GigaSpaces clusters in containerized environments
- Manage dependencies for XAP Grid deployment
- Perform health checks and monitoring of GigaSpaces services
- Test database connectivity with Oracle JDBC drivers

## Project Structure

```
├── docker-compose.yml      # Multi-service container orchestration
├── Dockerfile             # Custom GigaSpaces image with development tools
├── pom.xml                # Maven dependencies for XAP Grid components
├── build.sh               # Build automation script
├── scripts/               # Deployment and utility scripts
│   ├── gsDeploy.sh        # GigaSpaces deployment automation
│   └── healthcheck.sh     # Container health monitoring
└── deploy/                # Processing units deployment directory
```

## Prerequisites

- Docker and Docker Compose
- Maven 3.6+
- Java 17
- GigaSpaces license (trial license available with "tryme")

## Quick Start

### 1. Build Dependencies

```bash
mvn clean package
```

This will download and prepare all required dependencies (Commons Pool, Commons DBCP, Oracle JDBC drivers) in the `target/lib` directory.

### 2. Build Docker Image

```bash
docker build -t demo/demo-xap:17.1.2 .
```

### 3. Start Services

```bash
docker-compose up -d
```

This will start:
- **demo-xap**: Main GigaSpaces cluster with management and 3 GSCs (Grid Service Containers)
- **demo-api**: API service container for deployment tasks

### 4. Verify Deployment

Check service health:
```bash
docker-compose ps
```

Access GigaSpaces Management Center:
- URL: http://localhost:8090
- Ops Manager: http://localhost:8099

## Configuration

### Environment Variables

The GigaSpaces container is configured with:

- `GS_LICENSE`: License key (default: "tryme" for trial)
- `GS_HEAP`: JVM heap size (default: "2g")
- `GS_GSA_OPTIONS`: Grid Service Agent JVM options
- `GS_GSC_OPTIONS`: Grid Service Container JVM options with custom zones

### Port Mappings

- `8090`: GigaSpaces Management Center
- `8099`: Ops Manager
- `4174`: Lookup Service
- `8200-8300`: Dynamic port range for services

## Available Commands

### Maven Profiles

```bash
# Clean Docker containers
mvn -P prune

# Build and copy dependencies
mvn clean package
```

### Docker Operations

```bash
# View logs
docker-compose logs -f demo-xap

# Stop services
docker-compose down

# Rebuild and restart
docker-compose down && docker build -t demo/demo-xap:17.1.2 . && docker-compose up -d
```

## Development

### Adding Processing Units

Place your processing unit JARs in the `deploy/` directory. The deployment script will automatically handle them during container startup.

### Custom Deployment Scripts

Modify `scripts/gsDeploy.sh` to customize the deployment process for your specific use case.

### Database Integration

The project includes Oracle JDBC drivers for database connectivity. Configure your data sources as needed in your processing units.

## Troubleshooting

### Health Checks

The containers include built-in health monitoring. Check status with:
```bash
docker-compose ps
```

### Logs

Monitor service logs:
```bash
docker-compose logs -f [service-name]
```

### Common Issues

1. **Port conflicts**: Ensure ports 8090, 8099, and 4174 are available
2. **Memory issues**: Adjust `GS_HEAP` and JVM options in docker-compose.yml
3. **License problems**: Verify GS_LICENSE environment variable

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test with `docker-compose up`
5. Submit a pull request

## License

This project is licensed under the [MIT License](LICENSE) - see the LICENSE file for details.

**Note**: This project uses GigaSpaces XAP which has its own commercial licensing terms. The GigaSpaces software is not included in this repository's license and requires appropriate licensing from GigaSpaces for production use.
