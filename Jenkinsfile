pipeline {
    agent any

    environment {
        AWS_REGION       = "eu-central-1"
        ECR_ACCOUNT_ID   = "206578774527"
        ECR_REPO_NAME    = "auth-service"
        IMAGE_TAG        = "${env.GIT_COMMIT}" // use commit SHA
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh """
                git submodule init
                git submodule update --recursive
                """
            }
        }
        stage('Build') {
            steps {
                sh "./gradlew clean bootJar"
            }
        }
        stage('Build docker image') {
            steps {
                sh """
                docker build -t ${ECR_REPO_NAME}:${IMAGE_TAG} .
                docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_REPO_NAME}:latest
                """
            }
        }

        stage('Handle ECR') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'AWSKey'
                ]]) {
                    script {
                        def registry = "${ECR_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                        def repo     = "${registry}/${ECR_REPO_NAME}"
                        sh """
                        aws ecr get-login-password --region ${AWS_REGION} \
                            | docker login --username AWS --password-stdin ${registry}

                        docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${repo}:${IMAGE_TAG}
                        docker tag ${ECR_REPO_NAME}:latest ${repo}:latest

                        docker push ${repo}:${image_tag}
                        docker push ${repo}:latest
                        """
                    }
                }
            }
        }
    }
}