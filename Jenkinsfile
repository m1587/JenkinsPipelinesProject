pipeline {
    agent any

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
                timeout(time: 5, unit: 'MINUTES')
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
        cron('30 5 * * 1')  // Mondays at 05:30
        cron('0 14 * * *')  // Every day at 14:00
    }
}
