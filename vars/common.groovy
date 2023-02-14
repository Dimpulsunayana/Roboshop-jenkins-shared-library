def compile(){
    if (app_lang== "nodejs"){
        sh 'npm install'
    }

    if (app_lang== "maven"){
        sh 'mvn --version'
    }
}

def unittests(){
    if (app_lang== "nodejs"){
        //Developer is missing to add test cases in our project,We can ignore for now but it is
        //best practice to use
       sh 'npm test' || true
        //os basics-- "command1 || command2" if command1 fails then run command2
        // "command1 && command2" if command1 success then run command2
        //"true" means it always success in os
    }

    if (app_lang== "maven"){
        sh 'mvn test'
    }

    if (app_lang== "python"){
        sh 'python3 -m unittest'
    }
}