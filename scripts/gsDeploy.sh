#!/bin/sh

echo "Starting GigaSpaces Deployment..."

# Your deployment logic here
echo "Executing GigaSpaces deployment tasks..."

/opt/gigaspaces/bin/gs.sh --server=xap-grid pu deploy my_pu /opt/gigaspaces/deploy/my-pu-stateful-0.1.jar

# Mark as completed
touch /opt/gigaspaces/deploy/.deploy_complete
echo "GigaSpaces deployment script completed successfully"
