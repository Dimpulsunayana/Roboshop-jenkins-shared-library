def call(){
    try {
    pipeline {
            agent {
                label 'dimpul'
            }

            stages {

                stage('compile/Build') {
                    steps {
                        script {
                            common.compile()
                        }

                    }
                }

                stage('Unit Tests') {
                    steps {
                        script {
                            common.unittests()
                        }
                    }
                }

                stage('Quality Control') {
                    environment{
                        Sonar_User= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption ' +
                                '--query Parameters[0].Value | sed \'s/"//g\')'
                        Sonar_Pass= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption ' +
                                '--query Parameters[0].Value | sed \'s/"//g\')'
                            // some block
                        }
                    steps {
                        script {
                            wrap([$class: 'MaskPasswordsBuildWrapper',
                                  varPasswordPairs: [[password: '{Sonar_Pass}',user: '{Sonar_User}', var: 'SECRET']]]) {
                                sh "echo password = ${Sonar_Pass}"
                                sh "sonar-scanner -Dsonar.host.url=http://172.31.13.153:9000 -Dsonar.password=${Sonar_Pass} -Dsonar.login=${Sonar_User} -Dsonar.projectKey=cart"
                            }

                        }
                    }
                }

            }

            post {
                always {
                    echo 'sending mail'
                }
            }

        }
            }catch (Exception e){
        common.email("Failed")
    }
    }