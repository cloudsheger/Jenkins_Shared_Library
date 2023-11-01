def call(String imageName) {
    load "trivy.groovy"
    trivy(imageName)
}