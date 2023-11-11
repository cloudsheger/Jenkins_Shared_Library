// vars/pushToArtifactory.groovy

def call(Map config) {
    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"
    
    // Get Artifactory credentials
    def credentials-id = credentials(config.ARTIFACTORY_CREDENTIALS_ID)

    // Push Docker image to Artifactory using docker.withRegistry
    docker.withRegistry("https://${config.DOCKER_REGISTRY}", credentials-id) {
        sh "docker push ${dockerImage}"
    }
}