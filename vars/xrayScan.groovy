// vars/xrayScan.groovy

def call(Map config) {

    // Validate required parameters
    if (!config.ARTIFACTORY_SERVER || !config.ARTIFACTORY_REPO || !config.ARTIFACTORY_PATH) {
        error("Artifactory server URL, repository, and artifact path are required.")
    }

    // Get Artifactory credentials
    def credentials-id = credentials(config.ARTIFACTORY_CREDENTIALS_ID)

    docker.withRegistry("http://${config.DOCKER_REGISTRY}", 'credentials-id') {
        withCredentials([usernamePassword(credentialsId: 'credentials-id', usernameVariable: 'ARTIFACTORY_USERNAME', passwordVariable: 'ARTIFACTORY_PASSWORD')]) {
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
}
