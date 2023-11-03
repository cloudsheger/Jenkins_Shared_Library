// utils/parameters.groovy
def getSonarParameters(Map params) {
    return [
        SONAR_PROJECT_KEY: params.SONAR_PROJECT_KEY ?: 'petclinic',
        SONAR_PROJECT_NAME: params.SONAR_PROJECT_NAME ?: 'petclinic',
        SONAR_HOST_URL: params.SONAR_HOST_URL ?: 'http://localhost:9000',
        SONAR_TOKEN: params.SONAR_TOKEN ?: 'YOUR_DEFAULT_SONAR_TOKEN'
    ]
}
