def call(){
    pipeline{
        agent{
            node{
                label 'dimpul'
            }
        }

        parameters {
            choice(name: 'Infra_env', choices: ['dev', 'prod'], description: 'Pick the env')
        }
        stages{
            stage('Terraform init'){
                steps{
                    sh "terraform init -backend_config=env-${Infra_env}/state.tfvars"
                }

            }
        }
    }

}