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

# View API container logs
docker-compose logs -f demo-api

# View XAP logs
docker-compose logs -f xap-grid

# Stop services
docker-compose down

# Rebuild and restart
docker-compose down && docker build -t routemeshlabs/grid-api-demo:17.1.2 . && docker-compose up -d
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
