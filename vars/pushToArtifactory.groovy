// vars/pushToArtifactory.groovy

def call(Map config) {
    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"

    // Pull Docker image from Artifactory using docker.withRegistry
    docker.withRegistry("https://${config.DOCKER_REGISTRY}", config.ARTIFACTORY_CREDENTIALS_ID) {
        sh "docker pull ${dockerImage}"
    }

    // Build Docker image using cache
    sh "docker build --cache-from=${dockerImage} -t ${config.IMAGE_NAME}:${config.BUILD_NUMBER} ."

    // Push Docker image to Artifactory using docker.withRegistry
    docker.withRegistry("https://${config.DOCKER_REGISTRY}", config.ARTIFACTORY_CREDENTIALS_ID) {
        sh "docker push ${dockerImage}"
    }
}