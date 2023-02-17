def call(){
    if(!env.SONAR_EXTRA_OPTS){
        env.SONAR_EXTRA_OPTS= ""
    }
    if(!env.TAG_NAME){
        env.PUSH_CODE == false
    }else{
        env.PUSH_CODE == true
    }

    try {
        node('dimpul') {
            stage('chekout') {
                cleanWs()
                git branch: 'main', url: "https://github.com/Dimpulsunayana/${component}.git"
            }

            stage('compile/Build') {
                common.compile()
            }

            stage('Unit Tests') {
                common.unittests()
            }

            stage('Quality Control') {
                Sonar_User = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption ' +
                        '--query Parameters[0].Value | sed \'s/"//g\')'
                Sonar_Pass = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption ' +
                        '--query Parameters[0].Value | sed \'s/"//g\')'
                wrap([$class          : 'MaskPasswordsBuildWrapper',
                      varPasswordPairs: [[password: '{Sonar_Pass}', user: '{Sonar_User}', var: 'SECRET']]]) {
                    sh "sonar-scanner -Dsonar.host.url=http://172.31.13.153:9000 -Dsonar.password=${Sonar_Pass} " +
                            "-Dsonar.login=${Sonar_User} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"
                }
            }
if (env.PUSH_CODE == true) {
    stage('upload code to centralized place') {
        common.artifactpush()
    }
}
        }
    }catch (Exception e) {
            common.email("Failed")
        }
}


