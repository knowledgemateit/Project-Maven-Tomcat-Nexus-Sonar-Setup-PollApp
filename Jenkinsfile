pipeline {
    agent any

    environment {
        // Critical for your 4GB server to prevent OOM crashes
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
                // This wrapper injects the URL and Token automatically
                withSonarQubeEnv('SonarQube-Server') {
                    sh "sudo rm -rf /var/lib/jenkins/.sonar/cache"
                    sh "mvn sonar:sonar -Dsonar.projectKey=PollApp"
                }
            }
        }

        stage('Nexus Artifact Upload') {
            steps {
                echo 'Uploading WAR to Nexus...'
                // Ensure /etc/maven/settings.xml has your admin123 credentials
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                echo 'Deploying to Tomcat 10 Container...'
                deploy deployments: [[
                    contextPath: '/PollApp', 
                    war: 'target/*.war'
                ]], 
                credentialsId: 'tomcat-admin-creds', 
                targetAddress: 'http://localhost:8080'
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
