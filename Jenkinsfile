pipeline {
    agent {
    docker {
        image 'maven:3.9.6-eclipse-temurin-17'
    }
}

    parameters {
        string(name: 'REPO_URL', defaultValue: 'https://github.com/m1587/JenkinsPipelinesProject', description: 'GitHub repository URL')
        string(name: 'NAME_BRANCH', defaultValue: 'main', description: 'Branch to build')
    }

    environment {
        MAIN_BRANCH = 'main'
    }

    stages {

        stage('Checkout Code') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        echo "Starting checkout stage"

                        if (params.NAME_BRANCH == env.MAIN_BRANCH) {
                            echo "Checking out from MAIN branch using scm"
                            checkout scm
                        } else {
                            echo "Checking out from custom branch: ${params.NAME_BRANCH}"
                            git branch: "${params.NAME_BRANCH}", url: "${params.REPO_URL}"
                        }

                        echo "Checkout stage completed successfully"
                    }
                }
            }
        }

        stage('Compile') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting compilation stage"
                    sh 'mvn compile'
                    echo "Compilation stage completed successfully"
                }
            }
        }

        stage('Run Tests') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting test stage"
                    sh 'mvn test'
                    echo "Test stage completed successfully"
                }
            }
        }
    }

    post {
        success {
            echo "🎉 Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Check logs for details."
        }
    }

    triggers {
        cron('30 5 * * 1\n0 14 * * *')  // Mondays at 05:30 and every day at 14:00
    }
}
