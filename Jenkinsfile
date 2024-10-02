pipeline {

    agent any

    environment {
        // REGISTRY_CREDENTIALS = credentials('dockerhub_credentials')
        DOCKER_IMAGE = 'username/microsegapi'
        APP_VERSION = "${env.BUILD_ID}"
    }

    stages {

        stage('Build') { steps { sh 'echo "building .."' } }
        stage('Test') { steps { sh 'echo "testing .."'  } }
        stage('Logger stage') {
            steps {
                script {
//                     sh 'cp target/*.jar app.jar'
//                     sh 'docker build -t ${DOCKER_IMAGE}:${APP_VERSION} .'
                    sh "echo 'hello'"
                    sh "echo "
                }
            }
        }
        // stage('Push Docker Image') { steps { sh 'docker push ${DOCKER_IMAGE}:${APP_VERSION}' } }
        // stage('Deploy') { steps { echo "Deploying Docker Image: ${DOCKER_IMAGE}:${APP_VERSION}" } }
    }
    post {
        success { echo "Build and deployment successful!" }
        failure { echo "Build failed. Please check the logs for details." }
    }
}
