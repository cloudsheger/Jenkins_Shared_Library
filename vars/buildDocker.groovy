def call(String customImageName) {
    sh "docker build -t ${customImageName} ."         
}
