pipeline {
    agent any

    environment {
        BITSID = '2024SL70023'
        SONAR_HOST = 'http://localhost:9000'
        IMAGE_NAME = 'leave-service-2024sl70023'
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package jacoco:report'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Code Inspection') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar \
                          -Dsonar.projectKey=leave-service-${BITSID} \
                          -Dsonar.projectName="leave-service-${BITSID}" \
                          -Dsonar.host.url=${SONAR_HOST} \
                          -Dsonar.login=${SONAR_TOKEN} \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Deploy to Staging') {
            steps {
                sh """
                    docker rm -f leave-staging || true
                    docker run -d --name leave-staging -e APP_ENV=staging -p 8085:8080 ${IMAGE_NAME}:${IMAGE_TAG}
                    sleep 3
                    curl -f http://localhost:8085
                """
            }
        }

        stage('Approval for Production') {
            steps {
                input message: 'Staging verified. Approve promotion to Production?', ok: 'Promote to Production'
            }
        }

        stage('Deploy to Production') {
            steps {
                sh """
                    docker rm -f leave-prod || true
                    docker run -d --name leave-prod -e APP_ENV=production -p 8086:8080 ${IMAGE_NAME}:${IMAGE_TAG}
                    sleep 3
                    curl -f http://localhost:8086
                """
            }
        }
    }
}
