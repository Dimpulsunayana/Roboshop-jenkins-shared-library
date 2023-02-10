def ci(){
    pipeline {
        agent {
            label 'dimpul'
        }

        stages{

            stage('compile/Build'){
                steps{
                    echo 'Hello Dimpuldimmi'
                }
            }

            stage('Unit Tests'){
                steps{
                    echo 'Unit Tests'
                }
            }

            stage('Quality Control'){
                steps{
                    echo 'Quality Control'
                }
            }

        }

        post{
            always{
                echo 'sending mail'
            }
        }
    }
}