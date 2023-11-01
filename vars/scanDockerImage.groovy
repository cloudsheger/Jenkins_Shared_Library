def call(String imageName) {
    return {
        stage('Scan Docker Image') {
            steps {
                load "trivy.groovy"
                trivy(imageName)
            }
        }
    }
}
