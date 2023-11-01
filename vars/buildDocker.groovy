def call() {
    return {
        stage('Build Docker Image') {
            steps {
                script {
                    def customImageName = "cloudsheger/simple-java-app:${env.BUILD_NUMBER}"
                    sh "docker build -t $customImageName ."
                }
            }
        }
    }
}
