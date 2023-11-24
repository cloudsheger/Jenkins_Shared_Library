// vars/buildAndPushDockerImage.groovy

def call(Map config) {
    def imageName = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"

    echo "Building Docker image..."

    // Use Artifactory API key credentials
    withCredentials([string(credentialsId: config.ARTIFACTORY_API_KEY_CREDENTIALS_ID, variable: "artifactoryApiToken")]) {
        sh "docker build -f docker/Dockerfile -t ${imageName} ."
        sh "echo ${artifactoryApiToken} | docker login -u 'jems' --password-stdin ${config.DOCKER_REGISTRY}"
        sh "docker push ${imageName}"
    }
}
