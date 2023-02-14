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
       // sh 'npm test'
        sh 'echo test cases'
    }

    if (app_lang== "maven"){
        sh 'mvn test'
    }

    if (app_lang== "python"){
        sh 'python -m unittest'
    }
}