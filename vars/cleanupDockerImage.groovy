// vars/cleanupDockerImage.groovy

def call(Map config) {
    pipeline {
        agent any

        stages {
            stage('Cleanup Docker Image') {
                steps {
                    script {
                        cleanupDockerImage(config)
                    }
                }
            }
        }

        post {
            success {
                echo 'Docker image cleanup successful!'
            }
            failure {
                echo 'Docker image cleanup failed!'
            }
        }
    }
}

def cleanupDockerImage(Map config) {
    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"

    // Remove local Docker image
    sh "docker rmi ${dockerImage}"
}
