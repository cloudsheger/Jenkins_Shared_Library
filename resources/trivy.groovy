#!/usr/bin/env groovy

def call(imageName) {
    sh "trivy image $imageName > trivy.txt"
}
