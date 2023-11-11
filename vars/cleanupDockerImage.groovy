// vars/cleanupDockerImage.groovy

def call(Map config) {
    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"

    // Remove local Docker image
    sh "docker rmi ${dockerImage}"
}
