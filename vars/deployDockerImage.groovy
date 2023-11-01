def call(String imageName, int port) {
    return {
        stage('Deploy Docker Image') {
            steps {
                sh "docker run -d --name petclinic -p $port:8082 $imageName"
            }
        }
    }
}
