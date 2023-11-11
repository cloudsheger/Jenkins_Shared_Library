// vars/pushToArtifactory.groovy

def call(Map config) {
    pipeline {
        agent any

        stages {
            stage('Push to Artifactory') {
                steps {
                    script {
                        pushToArtifactory(config)
                    }
                }
            }
        }

        post {
            success {
                echo 'Docker image pushed to Artifactory successfully!'
            }
            failure {
                echo 'Docker image push to Artifactory failed!'
            }
        }
    }
}

def pushToArtifactory(Map config) {
    def dockerImage = "${config.DOCKER_REGISTRY}/${config.DOCKER_REPO}/${config.IMAGE_NAME}:${config.BUILD_NUMBER}"

    // Log in to Artifactory Docker registry
    withCredentials([usernamePassword(credentialsId: config.ARTIFACTORY_CREDENTIALS_ID, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh "docker login -u ${USERNAME} -p ${PASSWORD} ${config.DOCKER_REGISTRY}"
    }

    // Push Docker image to Artifactory
    sh "docker push ${dockerImage}"
}
