# Jenkins Shared Library Documentation

## Overview

This documentation provides information on the Jenkins Shared Library designed to streamline the Continuous Integration and Continuous Delivery (CI/CD) process for both frontend and backend applications. The library includes common functions applicable to both types of applications, as well as functions specific to certain tasks.

### Common Functions (For Both Frontend and Backend)

1. **BuildDockerImage**
   - Description: Builds the Docker image to be used as a Docker agent for running application tasks like unit tests and linting.

2. **DeployDockerImage**
   - Description: Pushes the Docker image to Artifactory.

3. **PublishBuildInfo**
   - Description: Publishes build information to Artifactory.

4. **DockerX-rayScan**
   - Description: Performs a security scan on the Docker image using X-Ray.

5. **BuildDeployDockerImage**
   - Description: Pulls the existing application image layer, rebuilds it, and publishes the latest images.

6. **DockerCleanUp**
   - Description: Cleans up Docker artifacts and unused images.

7. **DockerLoginSetup**
   - Description: Sets up Docker login credentials for authentication.

8. **SetImageTags (variable)**
   - Description: Defines the image tags for Dev and Prod environments.

### Different/Uncommon Functions

1. **RunUnitTest**
   - Description: Executes unit tests for the application.

2. **RunPylamaLinter**
   - Description: Runs the Pylama linter for Python code.

3. **RunBlackLinter**
   - Description: Executes the Black linter for code formatting.

4. **RunEndToEndTest**
   - Description: Runs end-to-end tests for the application.

5. **RunIntegrationTest**
   - Description: Executes integration tests for the application.

6. **RunTestCoverage**
   - Description: Runs test coverage for the application.

## Usage

To use this shared library in your Jenkins pipeline script:

```groovy
@Library('your-shared-library') _

pipeline {
    agent any

    parameters {
        // Define Jenkins parameters for configuring the pipeline
    }

    stages {
        // Define stages using the functions from the shared library
    }

    // Add post-build actions, notifications, etc.
}


# Notes
### Ensure that the necessary credentials and environment variables are configured in Jenkins for Docker registry authentication and Artifactory API key.

### Customize the shared library according to your project's specific requirements.

### This simplified version focuses on the functions and their descriptions, acknowledging the flexibility and configurability of the variables through Jenkins parameters. Adjust as needed based on your project's specific use cases and requirements.