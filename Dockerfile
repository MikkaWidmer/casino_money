# Use the official PostgreSQL image from the Docker Hub
FROM postgres:14

# Set environment variables for PostgreSQL
ENV POSTGRES_USER=myuser
ENV POSTGRES_PASSWORD=mypassword
ENV POSTGRES_DB=mydatabase

# Copy the SQL initialization script to the docker-entrypoint-initdb.d directory
# This script will be executed when the container is first created
COPY init.sql /docker-entrypoint-initdb.d/

# Expose the PostgreSQL port
EXPOSE 5433