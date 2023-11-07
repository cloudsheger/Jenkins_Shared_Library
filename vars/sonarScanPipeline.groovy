def call(Map params) {
    def projectKey = params.projectKey ?: 'shared-lib'
    def projectName = params.projectName ?: 'shared-lib'
    def sonarHostUrl = params.sonarHostUrl ?: 'http://localhost:9000'
    def sonarToken = params.sonarToken ?: 'deafult sonar token required'

    pipeline {
        agent any
        stages {
            stage('Sonar Static Code Analysis') {
                steps {
                    script {
                        echo "Running SonarQube scan for project: ${projectName}"
                        sh "mvn clean verify sonar:sonar \
                            -Dsonar.projectKey=${projectKey} \
                            -Dsonar.projectName='${projectName}' \
                            -Dsonar.host.url=${sonarHostUrl} \
                            -Dsonar.login=${sonarToken}"
                    }
                }
            }
        }
    }
}

