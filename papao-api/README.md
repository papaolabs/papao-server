```plain
__________                                                    .__
\______   \_____  ___________    ____           _____  ______ |__|
 |     ___/\__  \ \____ \__  \  /  _ \   ______ \__  \ \____ \|  |
 |    |     / __ \|  |_> > __ \(  <_> ) /_____/  / __ \|  |_> >  |
 |____|    (____  /   __(____  /\____/          (____  /   __/|__|
                \/|__|       \/                      \/|__|
```
# Papao-api

# Project Convention

## Environment
1. Java version: 1.8.0+
2. Default Encoding: UTF-8
3. Default File System: Linux

## IDE
1. IntelliJ
    * `gradle` 설정 시 `Create separate module per source set` 옵션을 반드시 `off` 해준다
    * `Settings -> Editor -> Code Style -> Manage -> Import -> Checkstyle Config` 설정에서  `config/checkstyle/checkstyle.xml` file 추가(checksytle plugin 이 설치되어야 함)
    * `Settings -> Editor -> Code Style -> Manage -> Import -> IntelliJ IDEA code style XML` 설정에서 `config/idea/div-intellij-codestyle.xml` file 추가
    * `Settings -> Editor -> Inspections -> Manage -> Import` 설정에서 `config/idea/div-intellij-inspections.xml` file 추가
2. Lombok Plugin
    * `Settings -> Build, Excution, Deployment -> Compiler -> Anottation Processors -> Enable annotation processing`
3. Checkstyle Plugin
    * `Settings -> Other Settings -> Checkstyle` 설정에서 `config/checkstyle/checkstyle.xml` file 추가

# Build

- gradle wrapper 사용 `./gradlew {task}`
