// vars/sonarScanPipeline.groovy
import utils.parameters 
// Import utility scripts from the src directory

def call(Map params) {
    def sonarParams = utils.parameters.getSonarParameters(params)
                    // Maven build and SonarQube scan command using sonarParams
                    sh "mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=${sonarParams.SONAR_PROJECT_KEY} \
                      -Dsonar.projectName='${sonarParams.SONAR_PROJECT_NAME}' \
                      -Dsonar.host.url=${sonarParams.SONAR_HOST_URL} \
                      -Dsonar.token=${sonarParams.SONAR_TOKEN}"
}
