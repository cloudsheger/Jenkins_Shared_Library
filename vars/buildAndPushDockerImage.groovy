// vars/buildAndPushDockerImage.groovy

def call(Map config) {
    def imageName = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"

    echo "Building Docker image..."

    // Use Artifactory credentials
    withCredentials([string(credentialsId: config.ARTIFACTORY_CREDENTIALS_ID, variable: "artifactoryCredentials")]) {
        sh "docker build -f docker/Dockerfile -t $imageName ."
        sh "docker login -u ${artifactoryCredentials_USR} -p ${artifactoryCredentials_PSW} ${config.DOCKER_REGISTRY}"
        sh "docker push $imageName"
    }
}
