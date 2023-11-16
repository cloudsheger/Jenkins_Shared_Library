// vars/pushToArtifactory.groovy

def buildAndPushImage(Map config, String tagName) {
    if (!config.DOCKER_REGISTRY || !config.DOCKER_REPO || !config.IMAGE_NAME || !config.ARTIFACTORY_CREDENTIALS_ID) {
        error "Missing required configuration parameters for Docker image."
    }

    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${tagName}"

    // Pull Docker image from Artifactory using docker.withRegistry
    docker.withRegistry("https://${config.DOCKER_REGISTRY}", config.ARTIFACTORY_CREDENTIALS_ID) {
        sh "docker pull ${dockerImage}"
    }

    // Build Docker image using cache
    sh "docker build --cache-from=${dockerImage} -t ${config.IMAGE_NAME}:${tagName} ."

    // Push Docker image to Artifactory using docker.withRegistry
    docker.withRegistry("https://${config.DOCKER_REGISTRY}", config.ARTIFACTORY_CREDENTIALS_ID) {
        sh "docker push ${dockerImage}"
    }
}

def call(Map config) {
    buildAndPushImage(config, 'latest')
    buildAndPushImage(config, config.BUILD_NUMBER)
}
