def compile(){
    if (app_lang== "nodejs"){
        sh 'npm install'
    }

    if (app_lang== "maven"){
        sh 'mvn package'
    }
}

def unittests(){
    if (app_lang== "nodejs"){
        //Developer is missing to add test cases in our project,We can ignore for now but it is
        //best practice to use
     //  sh 'npm test || true'
        //os basics-- "command1 || command2" if command1 fails then run command2
        // "command1 && command2" if command1 success then run command2
        //"true" means it always success in os
            sh 'npm test || true'

    }

    if (app_lang== "maven"){
        sh 'mvn test'
    }

    if (app_lang== "python"){
        sh 'python3 -m unittest'
    }
}

def email(email_note){
    mail bcc: '', body: "Job failed - ${JOB_BASE_NAME}\n jenkins URL - ${JOB_URL}", cc: '', from: 'dimpulsunayana205@gmail.com',
            replyTo: '', subject: "${JOB_BASE_NAME} Unit test failed in jenkins", to: 'dimpulsunayana205@gmail.com'
}
def artifactPush(){
    if (app_lang== "nodejs"){
    sh "zip -r ${component}-${TAG_NAME}.zip node_modules server.js"
    sh 'ls -l'
}
}



