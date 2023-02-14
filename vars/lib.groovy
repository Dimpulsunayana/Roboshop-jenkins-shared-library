def call(){
    pipeline {
        agent {
            label 'dimpul'
        }

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
                    script{
                        common.unittests()
                    }
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