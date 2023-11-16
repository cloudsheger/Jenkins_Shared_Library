// vars/pushToArtifactory.groovy

def call(Map params) {
    // Set default values for the variables if not provided
    def dockerRegistry = params.DOCKER_REGISTRY ?: 'cloudsheger.jfrog.io'
    def dockerRepo = params.DOCKER_REPO ?: 'docker'
    def imageName = params.IMAGE_NAME ?: 'petclinic'
    def credentialsId = params.ARTIFACTORY_CREDENTIALS_ID ?: 'default-artifactory-credentials'

    if (!dockerRegistry || !dockerRepo || !imageName || !credentialsId) {
        error "Missing required configuration parameters for Docker image."
    }

    def tagName = 'latest'
    def dockerImage = "${dockerRegistry}/${dockerRepo}/${imageName}:${tagName}"

    // Pull Docker image from Artifactory using docker.withRegistry
    docker.withRegistry("https://${dockerRegistry}", credentialsId) {
        sh "docker pull ${dockerImage}"
    }

    // Build Docker image using cache
    sh "docker build --cache-from=${dockerImage} -t ${imageName}:${tagName} ."

    // Push Docker image to Artifactory using docker.withRegistry
    docker.withRegistry("https://${dockerRegistry}", credentialsId) {
        sh "docker push ${dockerImage}"
    }
}
