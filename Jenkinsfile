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
            }
        }
        stage('Build docker image') {
            steps {
                sh """
                docker build -t ${ECR_REPO_NAME}:${IMAGE_TAG}
                """
            }
        }

        stage('Login to AWS ECR') {
            steps {
                script {
                    withAWS(credentials: 'AWSKey', region: ${AWS_REGION}) {
                        sh """
                            aws ecr get-login-password \
                                | docker login --username AWS --password-stdin ${ECR_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                            """
                    }
                }
            }
        }

        stage('Tag & Push to ECR') {
            steps {
                script {
                    sh """
                    docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}
                    docker push ${ECR_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }
    }
}