pipeline {
    agent any

    tools {
        maven 'Maven_Latest' // Ensure this matches your Global Tool Configuration
        jdk 'Java_17'
    }

    environment {
        // Resource optimization for your t2.micro
        MAVEN_OPTS = "-Xmx256m" 
        SONAR_URL = "http://localhost:9000"
        NEXUS_URL = "localhost:8081"
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
                echo 'Building PollApp and running JUnit tests...'
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Analyzing Code Quality...'
                // Use the token you generated in SonarQube
                withSonarQubeEnv('SonarQube-Server') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=PollApp -Dsonar.host.url=${SONAR_URL}"
                }
            }
        }

        stage('Nexus Artifact Upload') {
            steps {
                echo 'Uploading versioned WAR to Nexus...'
                // This utilizes your settings.xml configuration
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                echo 'Deploying to Tomcat 10...'
                // Using the 'Deploy to Container' plugin
                deploy deployments: [[
                    contextPath: '/PollApp', 
                    war: 'target/*.war'
                ]], 
                contextPath: null, 
                onFailure: false, 
                credentialsId: 'tomcat-admin-creds', 
                targetAddress: 'http://localhost:8080'
            }
        }
    }
}
