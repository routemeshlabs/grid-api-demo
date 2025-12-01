#
# This Dockerfile is used to build an image that contains tools to test Gigaspaces
# deployment.  
#
# gigaspaces version 17.1.2 uses JDK 17.1.2
# really cool how they are aligning the versions
#
FROM gigaspaces/smart-cache-enterprise:17.1.2

# Switch to root user to install packages
USER root

# Install additional diagnostic utilities
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk-headless \
    maven \
    net-tools \
    procps \
    curl \
    wget \
    vim \
    && rm -rf /var/lib/apt/lists/*

# Switch back to the original user (if needed)
USER gs_user

# Copy the README file into the directory
COPY ./deploy/README /opt/gigaspaces/deploy

# here is the command to build the image
#docker build -t routemeshlabs/grid-api-demo:17.1.2 .

# here is the command to run the image using a docker compose file
#docker-compose -f docker-compose.yml up -d

