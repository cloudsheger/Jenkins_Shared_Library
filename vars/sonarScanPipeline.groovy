def call(Map params) {
    def projectKey = params.projectKey ?: 'shared-lib'
    def projectName = params.projectName ?: 'shared-lib'
    def sonarHostUrl = params.sonarHostUrl ?: 'http://localhost:9000'
    def sonarToken = params.sonarToken ?: 'deafult sonar token required'

  echo "Running SonarQube scan for project: ${projectName}"

  withSonarQubeEnv('SonarScanner') {
    sh('mvn sonar:sonar \
       -Dsonar.projectKey=${projectKey} \
       -Dsonar.projectName=${projectName} \
       -Dsonar.host.url=${sonarHostUrl} \
       -Dsonar.login=${sonarToken}')
    }
}

