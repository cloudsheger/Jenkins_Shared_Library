@Library('my-shared-library') _
import com.example.CustomUtils

def imageName = "cloudsheger/simple-java-app:${env.BUILD_NUMBER}"
def dockerPort = 8082

node {
    buildDocker()

    scanDockerImage(imageName)

    deployDockerImage(imageName, dockerPort)

    stage('Send Slack Alert') {
        when {
            expression { currentBuild.resultIsBetterOrEqualTo('FAILURE') }
        }
        steps {
            slackSend(
                color: 'danger',
                channel: '#devsecops-build-status',
                message: "Build failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}\n${env.BUILD_URL}",
                tokenCredentialId: 'slack-token'
            )
        }
    }

    CustomUtils.cleanupDockerImages()
}
