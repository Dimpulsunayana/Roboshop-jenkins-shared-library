def call(){
    pipeline{
        options {
            ansiColor('xterm')
        }
        agent{
            node{
                label 'dimpul'
            }
        }

        parameters {
            choice(name: 'Infra_env', choices: ['dev', 'prod'], description: 'Pick the env')
        }
        stage('clean workspace') {
            cleanWs()
        }
        stages{
            stage('Terraform init'){
                steps{
                    sh "terraform init -backend-config=env-${Infra_env}/state.tfvars"
                }

            }
        }
    }

}