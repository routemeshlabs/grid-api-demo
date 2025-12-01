#!/bin/bash
# Docker health check script for GigaSpaces XAP
# Simple check to verify the management API is accessible

curl -f -s http://xap-grid:8090/v2/info > /dev/null
