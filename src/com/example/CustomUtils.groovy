package com.example

class CustomUtils {
    static void cleanupDockerImages() {
        sh 'docker system prune -f'
    }
}
