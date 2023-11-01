def call(String localJarPath, String artifactoryUrl, String apiKey) {
    sh "curl -H 'X-JFrog-Art-Api:${apiKey}' -T ${localJarPath} ${artifactoryUrl}"
}