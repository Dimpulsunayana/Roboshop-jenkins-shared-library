def call(){
    pipeline {
        agent any
//                {
//            label 'dimpul'
//        }

        stages{

            stage('compile/Build'){
                steps{
                    script{
                    common.compile()
                    }

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