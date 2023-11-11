// vars/pushToArtifactory.groovy

def call(Map config) {
    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"
    // Log in to Artifactory Docker registry
    withCredentials([usernamePassword(credentialsId: config.ARTIFACTORY_CREDENTIALS_ID, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh "docker login -u ${USERNAME} -p ${PASSWORD} https://${config.DOCKER_REGISTRY}"
    }

    // Push Docker image to Artifactory
    sh "docker push ${dockerImage}"
}