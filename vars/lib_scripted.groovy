def call(){
    node(){
        stage('compile/Build'){
            common.compile()
        }

        stage('Unit Tests'){
            common.unittests()
        }

        stage('Quality Control'){
            Sonar_User= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption ' +
                    '--query Parameters[0].Value | sed \'s/"//g\')'
            Sonar_Pass= '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption ' +
                    '--query Parameters[0].Value | sed \'s/"//g\')'
            wrap([$class: 'MaskPasswordsBuildWrapper',
                  varPasswordPairs: [[password: '{Sonar_Pass}',user: '{Sonar_User}', var: 'SECRET']]]) {
                sh "sonar-scanner -Dsonar.host.url=http://172.31.13.153:9000 -Dsonar.password=${Sonar_Pass} -Dsonar.login=${Sonar_User} -Dsonar.projectKey=cart"
            }
        }

        stage('upload code to centralized place'){
            echo 'upload'
        }
    }
}
