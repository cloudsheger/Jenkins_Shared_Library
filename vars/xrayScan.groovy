// vars/xrayScan.groovy

def call(Map config) {
    // Get Artifactory credentials
    def credentialsId = credentials(config.ARTIFACTORY_CREDENTIALS_ID)

    docker.withRegistry("http://${config.DOCKER_REGISTRY}", credentialsId) {
            sh """
                curl -u ${ARTIFACTORY_USERNAME}:${ARTIFACTORY_PASSWORD} -X POST ${config.ARTIFACTORY_SERVER}/api/xray/scanArtifact -H 'Content-Type: application/json' \
                -d '{
                    "artifactoryUrl": "${config.ARTIFACTORY_SERVER}",
                    "repository": "${config.ARTIFACTORY_REPO}",
                    "artifactPath": "${config.ARTIFACTORY_PATH}",
                    "buildName": "${config.BUILD_NAME}",
                    "buildNumber": "${config.BUILD_NUMBER}"
                }'
            """
    }
}
