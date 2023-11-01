def call(String imageName, int port) {
    sh "docker run -d --name petclinic -p $port:8082 $imageName"
}
