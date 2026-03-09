pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
metadata:
  name: kaniko-pod
spec:
  containers:
    - name: kaniko
      image: gcr.io/kaniko-project/executor:debug
      command:
        - /busybox/cat
      tty: true
    - name: kubectl
      image: harbor.gipnotik.ru/library/jenkins-agent:latest
      command:
        - cat
      tty: true
"""
        }
    }
    environment {
        REGISTRY = "harbor.gipnotik.ru"
        IMAGE_NAME = "demo"
        IMAGE_TAG = "latest"
    }

    options {
        ansiColor('xterm')
    }

    stages {



        stage('Build image with kaniko') {
            steps {
                container('kaniko') {
                    script {
                        withVault([
                            vaultSecrets: [
                                [
                                    path: '/kv/harbor',
                                    engineVersion: 2,
                                    secretValues: [
                                        [envVar: 'REGISTRY_PASSWORD', vaultKey: 'admin_password'],
                                        [envVar: 'REGISTRY_USER', vaultKey: 'user']
                                    ]
                                ]                            
                            ]
                        ]) {                        
                            sh '''
                                # Создаем Docker config
                                mkdir -p /.docker
                                
                                # Создаем auth token
                                AUTH_TOKEN=$(echo -n "$REGISTRY_USER:$REGISTRY_PASSWORD" | base64 | tr -d '\n')
                                
                                # Создаем правильный JSON config
                                echo "{\\"auths\\":{\\"$REGISTRY\\":{\\"auth\\":\\"$AUTH_TOKEN\\"}}}" > /.docker/config.json                            
                                
                                mvn compile jib:build
                                
                            '''
                        }
                    }
                }
            }
        }


    }
}

