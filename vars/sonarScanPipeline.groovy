// vars/sonarScanPipeline.groovy
// Import utility scripts from the src directory

def call(Map params) {
    //def sonarParams = utils.parameters.getSonarParameters(params)
                    // Maven build and SonarQube scan command using sonarParams
                    sh "mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=${params.SONAR_PROJECT_KEY} \
                      -Dsonar.projectName='${params.SONAR_PROJECT_NAME}' \
                      -Dsonar.host.url=${params.SONAR_HOST_URL} \
                      -Dsonar.token=${params.SONAR_TOKEN}"
}
