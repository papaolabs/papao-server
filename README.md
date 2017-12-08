
# papao-server
> papao-api

> papao-dashboard

> papao-batch

> papao-openapi

> papao-push

> papao-image

# Project Convention

## Environment
1. Java version: 1.8.0+
2. Default Encoding: UTF-8
3. Default File System: Linux

## IDE
1. IntelliJ
    * `Settings -> Editor -> Code Style -> Manage -> Import -> IntelliJ IDEA code style XML` 설정에서 `config/idea/div-intellij-codestyle.xml` file 추가
    * `Settings -> Editor -> Inspections -> Manage -> Import` 설정에서 `config/idea/div-intellij-inspections.xml` file 추가
2. Checkstyle Plugin
    * `Settings -> Other Settings -> Checkstyle` 설정에서 `config/checkstyle/checkstyle.xml` file 추가

## Git Branch 전략
> [bitbucket branch model](https://confluence.atlassian.com/bitbucketserver0414/using-branches-in-bitbucket-server-895367626.html?utm_campaign=in-app-help&utm_medium=in-app-help&utm_source=stash) 을 따릅니다
1. develop 개발(`git flow feature`)은 `feature/지라이슈번호` e.g `feature/PAPAO-100` 형태로 브랜치 작업을 합니다
> 일반적인 이슈 개발
> `develop` branch 에서 시작되어 `develop` branch 로 PR 
2. release 개발(`git flow release`)은 `release/major.minor.x` e.g `release/1.0.x` 형태로 브랜치 작업을 합니다.
> 배포본 작업, release 브랜치는 동시에 하나만 존재 
> `develop` branch 에서 시작되어 `master` branch 로 PR
> `bitbucket` 에 `Automatic merge` 가 활성화 되어 `master` 에 merge 되면 자동으로 `develop` 에 머지됨. 
3. hotfix 개발(`git flow hotfix`)은 `hotfix/major.minor.x` e.g `hotfix/1.0.x` 형태로 브랜치 작업을 합니다, `nebula-release-plugin` 참조
> 현재 서비스에 버그 수정, hotfix 브랜치는 동시에 하나만 존재 
> `master` branch 에서 시작되어 `master` branch 로 PR
> `bitbucket` 에 `Automatic merge` 가 활성화 되어 `master` 에 merge 되면 자동으로 `develop` 에 머지됨.
4. bugfix 개발(`git flow` 에 없으나 `bitbucket` 에 있는 `release` branch 패치 이슈)은 `bugfix/지라이슈번호` e.g `bugfix/PAPAO-100` 형태로 브랜치 작업합니다.
> 배포본 버그 수정
> `release/xxx` branch 에서 시작되어 `release/xxx` branch 로 PR

## Comment Log Convention 
* 패스워드나 보안 관련 파일은 반드시 커밋하지 말고 gitignore 에 등록 후 개발시에는 전임 개발자에게 전달 받는다.
* 커밋시 아래 포맷 사용
```plain
{이슈넘버} {제목}

- {설명1}
- {설명2}
- {설명n}
```
* 예제
```plain
PAPAOAPI-100 compose method pattern 적용

- 읽기 힘들어서 적절히 메소드를 쪼개어 추상화 레벨을 같이함. 필요 없으면 제목 한줄만
```    

# Build

- gradle wrapper 사용 `./gradlew {task}`

## Testing

1. Unit Test
```bash
./gradlew test -Dfilters.tags.exclude=integration
```
2. Integration Test
```bash
./gradlew test -Dfilters.tags.include=integration
```
3. test all, checkstyle, findbug, pmd, jacoco coverage
```bash
./gradlew test check jacocoTestReport
```

## Packaging

### nebula release plugin
- 버전을 알아서 붙여 준다, semver 에 맞게
- `-Prelease.scope=` 버전 업 될 부분을 파라미터로 줄수 있음 `major`, `minor`, `patch`, 기본은 `minor`
- 특정 버전으로 명시하고 싶으면 `-Prelease.version=xx.xx.xx` 형태로 가능
- git tag 버전을 그대로 쓰고 싶으면 tag 를 딴뒤 `-Prelease.useLastTag=true`  

#### maven 형식 snapshot
```bash
./gradlew clean snapshot
```

#### 개발 버전
```bash
./gradlew clean devSnapshot -Prelease.scope=patch
```

#### RC 버전
- master, release 브랜치에서만 가능
- 자동 태깅 됨
```bash
./gradlew clean candidate
```

#### final 버전
- master, release 브랜치에서만 가능
- 자동 태깅 됨
```bash
./gradlew clean final
```

### maven releases publish 
- `-DmavenUsername=` 에 nexus 계정, `-DmavenPassword=` 에 nexus 암호 필요
```bash
./gradlew clean snapshot publish

### gradle task dependency 확인
```bash
./gradlew test taskTree
```

### 최신 dependency 확인 
```bash
./gradlew dependencyUpdates -Prevision=release
```

## 실행(배포)

### 로컬개발 테스트 편리한 설정
- profile
  - `spring.profiles.active` : `dev` or `prd` 
  - `dev` : 리모트 서버, 개발 db
  - `prd` : 리모트 서버, 상용 db
- encrypt
  - `encrypt.key=키`, spring profile 이 dev,stg,alp,prd 이면 필수, 담당자에게 키 공유받기

### Spring Config 환경(dev,prd)
- `-Dencrypt.key=해당키` or `ENCRYPT_KEY=해당키` 설정을 해준다 


#### `develop` branch
1. git pull develop branch
> commit 이 일어나면(일반적으로 PR 머지) 주기적으로 `develop` branch 를 가져온다
2. unit test
> `./gradlew test -Dfilters.tags.exclude=integration`
3. code quality
> `./gradlew checkstyleMain findbugsMain pmdMain`
4. integration test
> `./gradlew test -Dfilters.tags.include=integration`
> 동시 실행
5. build
> `./gradlew snapshot -Prelease.scope=patch`
6. call jarvis pipeline
> jarvis pipeline api 를 호출하여 개발 서버 자동 배포 
```plain
git pull develop branch  ->  unit test     ->  integration test  ->  build snapshot version  ->  call jarvis pipeline 
                         ->  code quality  -> 
```

#### `release` branch
0. gocd release git material 에 `Default Branch` 를 명시한다.
1. git pull `release` branch
> commit 이 일어나면(일반적으로 PR 머지) 주기적으로 `release/버전명` branch 를 가져온다
2. unit test
> `./gradlew test -Dfilters.tags.exclude=integration`
3. code quality
> `./gradlew checkstyleMain findbugsMain pmdMain`
4. integration test
> `./gradlew test -Dfilters.tags.include=integration`
> 동시 실행
5. build and git tagging
> `./gradlew candidate`
> 자동 git tagging 이 진행, 버전.rcX 가 생성 규칙은 `nebula-release-plugin` 참조


#### `htofix` branch
0. gocd hotfix git material 에 `Default Branch` 를 명시한다
1. git pull `hotfix` branch
> commit 이 일어나면(일반적으로 PR 머지) 주기적으로 `hotfix/버전명` branch 를 가져온다
2. unit test
> `./gradlew test -Dfilters.tags.exclude=integration`
3. code quality
> `./gradlew checkstyleMain findbugsMain pmdMain`
4. integration test
> `./gradlew test -Dfilters.tags.include=integration`
> 동시 실행
5. build and git tagging
> `./gradlew candidate -Prelease.scope=patch`
> 자동 git tagging 이 진행, 버전.rcX 가 생성 규칙은 `nebula-release-plugin` 참조
6. publish nexus
> `./gradlew candidate publish -Prelease.useLastTag=true` 
> `4.` 에서 태깅된 버전으로 spk nexus 에 publishing 
7. call jarvis pipeline
> jarvis pipeline api 를 호출하여 검증 서버 자동 배포
8. gocd release git material 에 `Poll for new changes` 을 disable 시킨다.
```plain
git pull hotfix branch  ->  unit test     ->  integration test  ->  build and git tagging  ->  publish nexus  ->  call jarvis pipeline 
                        ->  code quality  -> 
```

#### `master` branch 
1. git pull `release` branch
> commit 이 일어나면(일반적으로 PR 머지) 주기적으로 branch 를 가져온다
2. unit test
> `./gradlew test -Dfilters.tags.exclude=integration`
3. code quality
> `./gradlew checkstyleMain findbugsMain pmdMain`
4. integration test
> `./gradlew test -Dfilters.tags.include=integration`
> 동시 실행
5. build and git tagging
> `./gradlew final`
> 자동 git tagging 이 진행, 버전 가 생성 규칙은 `nebula-release-plugin` 참조
6. publish nexus
> `./gradlew candidate publish -Prelease.useLastTag=true` 
> `4.` 에서 태깅된 버전으로 spk nexus 에 publishing 
7. call jarvis pipeline
> jarvis pipeline api 를 호출하여 검증 서버 자동 배포, Canary, 상용 배포는 jarvis pipeline 에서 처리  
```plain
git pull master branch  ->  unit test     ->  integration test  ->  build and git tagging  ->  publish nexus  ->  call jarvis pipeline 
                        ->  code quality  -> 
```

## System
* SCM: [Github](http://github.com)
* Issue Tracker: [Github issue](https://github.com/papaolabs/papao-server/issues)
* CI: [Jenkins](https://jenkins-ci.org/)


## References

* Java
  * [Goolge Java Style Guide](https://google.github.io/styleguide/javaguide.html)
  * [awesome-java](https://github.com/akullpp/awesome-java)
  * [Lombok](http://projectlombok.org/)
* Gradle
  * [Netflix Nebula Plugins](https://nebula-plugins.github.io/)
* Architecture
  * [Architecture, Use Cases, and High Level Design](https://cleancoders.com/episode/clean-code-episode-7/show)
  * [Component Case Study](https://cleancoders.com/episode/clean-code-episode-18/show)
* REST API Design & Test
  * [HTTP API Design](https://github.com/interagent/http-api-design)
  * [httpie](https://github.com/jakubroztocil/httpie)
  * [htty](https://github.com/htty/htty)
* Etc
  * [git-flow](https://github.com/nvie/gitflow)
  * [bitbucket branch model](https://confluence.atlassian.com/bitbucketserver0414/using-branches-in-bitbucket-server-895367626.html?utm_campaign=in-app-help&utm_medium=in-app-help&utm_source=stash)
  * [Semantic Versioning](http://semver.org/lang/ko/)

## Author

If you have any questions regarding this project contact

## LICENSE

#### MIT LICENSE
Copyright (c) 2017- papao

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
