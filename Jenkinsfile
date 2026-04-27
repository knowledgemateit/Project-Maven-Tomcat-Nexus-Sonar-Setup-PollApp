pipeline {
    agent any

    environment {
        MAVEN_OPTS = "-Xmx256m -XX:MaxMetaspaceSize=128m" 
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Cloning PollApp Repository...'
                git branch: 'main', url: 'https://github.com/knowledgemateit/Project-Maven-Tomcat-Nexus-Sonar-Setup-PollApp.git'
            }
        }
        
        stage('Build & Test') {
            steps {
                echo 'Building PollApp...'
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Analyzing Code Quality...'
                withSonarQubeEnv('SonarQube-Server') {
                    // Ensure jenkins user has sudo rights or skip sudo if permissions are set
                    sh "mvn sonar:sonar -Dsonar.projectKey=PollApp"
                }
            }
        }

        stage('Nexus Artifact Upload') {
            steps {
                echo 'Uploading WAR to Nexus...'
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                echo 'Deploying to Tomcat 10 Container...'
                // Correct Declarative Syntax for Deploy to Container Plugin
                deploy(
                    adapters: [
                        tomcat9(
                            credentialsId: 'tomcat-admin-creds',
                            url: 'http://localhost:8080'
                        )
                    ],
                    contextPath: '/PollApp',
                    war: 'target/*.war'
                )
            }
        }
    }
    
    post {
        always {
            echo 'Cleaning up workspace to save disk space...'
            cleanWs()
        }
    }
}
