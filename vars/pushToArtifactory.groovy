// vars/pushToArtifactory.groovy

def call(Map config) {
    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"

    // Push Docker image to Artifactory using docker.withRegistry
    //// Get Artifactory credentials
    //def credentialsId = credentials(config.ARTIFACTORY_CREDENTIALS_ID)
    docker.withRegistry("https://${config.DOCKER_REGISTRY}", config.ARTIFACTORY_CREDENTIALS_ID) {
        sh "docker push ${dockerImage}"
    }
}