pipeline {

    agent any

    environment {
        // REGISTRY_CREDENTIALS = credentials('dockerhub_credentials')
        DOCKER_IMAGE = 'username/microsegapi'
        APP_VERSION = "${env.BUILD_ID}"
    }

    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Build') { steps { sh 'echo "building .."' } }
        stage('Test') { steps { sh 'echo "testing .."'  } }
        stage('Build Docker Image') {
            steps {
                script {
//                     sh 'cp target/*.jar app.jar'
//                     sh 'docker build -t ${DOCKER_IMAGE}:${APP_VERSION} .'
                    sh "echo 'hello'"
                }
            }
        }
        stage('Docker Login') {
            steps {
                script {
                    // sh 'echo $REGISTRY_CREDENTIALS_PSW | docker login -u $REGISTRY_CREDENTIALS_USR --password-stdin'
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
