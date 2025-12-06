# XAP API - GigaSpaces Testing Interface

A comprehensive testing and deployment interface for GigaSpaces XAP (eXtreme Application Platform) using containerized environments.

**Note**: This project uses GigaSpaces XAP which has its own commercial licensing terms. The GigaSpaces software is not included in this repository's license and requires appropriate licensing from GigaSpaces for production use.

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
docker build -t routemeshlabs/grid-api-demo:17.1.2 .
```

### 3. Start Services

```bash
docker-compose up -d
```

This will start:
- **xap-grid**: Main GigaSpaces cluster with management and 3 GSCs (Grid Service Containers)
- **demo-api**: API service container for deployment tasks

### 4. Verify Deployment

Check service health:
```bash
docker-compose ps
```

Access GigaSpaces Management Center:
- URL: http://localhost:8090
- Ops Manager: http://localhost:8099

For detailed configuration, commands, development guidelines, troubleshooting, and contribution information, see [DETAILS.md](DETAILS.md).
