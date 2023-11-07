def call(Map params) {
    def projectKey = params.projectKey ?: error "Project key is required"
    def projectName = params.projectName ?: error "Project name is required"
    def sonarHostUrl = params.sonarHostUrl ?: error "SonarQube server URL is required"
    def sonarToken = params.sonarToken ?: error "SonarQube token is required"

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

